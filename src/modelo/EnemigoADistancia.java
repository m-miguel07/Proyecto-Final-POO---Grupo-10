package modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EnemigoADistancia extends Enemigo {

    private static final int INTERVALO_DISPARO_MS = 2000;
    private static final double VELOCIDAD_PROYECTIL = 7.0;
    private static final int PROYECTIL_ANCHO = 16;
    private static final int PROYECTIL_ALTO = 16;

    //Atributos
    private int alcance;
    private int tiempoSiguienteDisparoMS;
    private Personaje objetivo;
    private final List<Proyectil> proyectiles;
    private final List<Proyectil> proyectilesVista;

    //Constructor
    public EnemigoADistancia(int alcance, int puntosVida, int ancho, int alto, double velocidad, double posicionX, double posicionY) {
        super(puntosVida, ancho, alto, velocidad, posicionX, posicionY);

        if (alcance <= 0) {
            throw new IllegalArgumentException("El alcance debe ser positivo");
        }

        this.alcance = alcance;
        this.tiempoSiguienteDisparoMS = INTERVALO_DISPARO_MS;
        this.proyectiles = new ArrayList<>();
        this.proyectilesVista = Collections.unmodifiableList(this.proyectiles);
    }

    //Getters
    public int getAlcance() {
        return this.alcance;
    }

    public List<Proyectil> getProyectiles() {
        return this.proyectilesVista;
    }

    //Setters
    public void setAlcance(int alcance) {
        if (alcance <= 0) {
            throw new IllegalArgumentException("El alcance debe ser positivo");
        }

        this.alcance = alcance;
    }

    public void setObjetivo(Personaje objetivo) {
        this.objetivo = objetivo;
    }

    //Comportamientos
    @Override
    public void actualizarEstados(int deltaMS) {
        super.actualizarEstados(deltaMS);

        this.actualizarProyectiles(deltaMS);

        if (this.estaCongelado() || this.objetivo == null || !this.estaEnAlcance(this.objetivo)) {
            return;
        }

        this.tiempoSiguienteDisparoMS -= deltaMS;

        if (this.tiempoSiguienteDisparoMS <= 0) {
            this.disparar();
            this.tiempoSiguienteDisparoMS = INTERVALO_DISPARO_MS;
        }
    }

    private boolean estaEnAlcance(ObjetoDelJuego objetivo) {
        return this.distanciaA(objetivo) <= this.alcance;
    }

    private void disparar() {
        double origenX = this.getPosicionX() + (this.getAncho() / 2.0);
        double origenY = this.getPosicionY() + (this.getAlto() / 2.0);
        double destinoX = this.objetivo.getPosicionX() + (this.objetivo.getAncho() / 2.0);
        double destinoY = this.objetivo.getPosicionY() + (this.objetivo.getAlto() / 2.0);
        double diferenciaX = destinoX - origenX;
        double diferenciaY = destinoY - origenY;
        double distancia = Math.hypot(diferenciaX, diferenciaY);

        if (distancia < 1.0) {
            return;
        }

        double velocidadX = (diferenciaX / distancia) * VELOCIDAD_PROYECTIL;
        double velocidadY = (diferenciaY / distancia) * VELOCIDAD_PROYECTIL;

        this.proyectiles.add(new Proyectil(
                origenX - (PROYECTIL_ANCHO / 2.0),
                origenY - (PROYECTIL_ALTO / 2.0),
                PROYECTIL_ANCHO,
                PROYECTIL_ALTO,
                velocidadX,
                velocidadY));
    }

    private void actualizarProyectiles(int deltaMS) {
        double factor = deltaMS / 16.0;

        for (int i = this.proyectiles.size() - 1; i >= 0; i--) {
            Proyectil proyectil = this.proyectiles.get(i);
            proyectil.mover(factor);

            if (this.objetivo != null && !this.objetivo.estaInvulnerable() && proyectil.interseca(this.objetivo)) {
                this.objetivo.perderVida();
                proyectil.desactivar();
            }

            if (!proyectil.isActivo() || proyectil.estaFueraDePantalla(Configuracion.ANCHO, Configuracion.ALTO)) {
                this.proyectiles.remove(i);
            }
        }
    }

    private double distanciaA(ObjetoDelJuego objetivo) {
        double centroX = this.getPosicionX() + this.getAncho() / 2.0;
        double centroY = this.getPosicionY() + this.getAlto() / 2.0;
        double centroXObjetivo = objetivo.getPosicionX() + objetivo.getAncho() / 2.0;
        double centroYObjetivo = objetivo.getPosicionY() + objetivo.getAlto() / 2.0;
        return Math.hypot(centroX - centroXObjetivo, centroY - centroYObjetivo);
    }
}
