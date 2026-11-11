package modelo;

import java.util.Collections;
import java.util.List;

public abstract class Enemigo extends Entidad {

    //Atributos
    private static final double RANGO_CUERPO_A_CUERPO = 5.0;

    private int tiempoQuemadoMS;
    private int danioPorQuemaduraTick;
    private int tiempoSiguienteTickQuemaduraMS;

    //Constructor
    public Enemigo(int puntosVida, int ancho, int alto, double velocidad, double posicionX, double posicionY) {
        super(puntosVida, ancho, alto, velocidad, posicionX, posicionY);

        this.tiempoQuemadoMS = 0;
        this.danioPorQuemaduraTick = 0;
        this.tiempoSiguienteTickQuemaduraMS = 0;
    }

    //Comportamientos
    public void actualizar(List<Plataforma> plataformas, int anchoPantalla, Muro muro) {
    }

    //Terrestre, Volador, A Distancia
    public abstract TipoEnemigo getTipo();

    //agregado para evitar usar instanceof en la vista
    public List<Proyectil> getProyectiles() {
        return Collections.emptyList();
    }

    public boolean puedeAtacar(ObjetoDelJuego objetivo) {
        double x1 = this.getPosicionX() - RANGO_CUERPO_A_CUERPO;
        double y1 = this.getPosicionY() - RANGO_CUERPO_A_CUERPO;
        int ancho1 = this.getAncho() + (int) Math.round(2 * RANGO_CUERPO_A_CUERPO);
        int alto1 = this.getAlto() + (int) Math.round(2 * RANGO_CUERPO_A_CUERPO);
        return x1 < objetivo.getPosicionX() + objetivo.getAncho()
                && x1 + ancho1 > objetivo.getPosicionX()
                && y1 < objetivo.getPosicionY() + objetivo.getAlto()
                && y1 + alto1 > objetivo.getPosicionY();
    }

    public boolean estaQuemado() {
        return this.tiempoQuemadoMS > 0;
    }

    @Override
    public void recibirQuemadura(int duracionMS, int danioPorTick) {
        if (duracionMS <= 0 || danioPorTick <= 0) {
            return;
        }
        this.tiempoQuemadoMS = duracionMS;
        this.danioPorQuemaduraTick = danioPorTick;
        this.tiempoSiguienteTickQuemaduraMS = 500;
    }

    @Override
    public void actualizarEstados(int deltaMS) {
        super.actualizarEstados(deltaMS);

        if (this.tiempoQuemadoMS > 0) {
            this.tiempoQuemadoMS = Math.max(0, this.tiempoQuemadoMS - deltaMS);
            this.tiempoSiguienteTickQuemaduraMS -= deltaMS;
            if (this.tiempoSiguienteTickQuemaduraMS <= 0) {
                this.recibirDanio(this.danioPorQuemaduraTick);
                this.tiempoSiguienteTickQuemaduraMS = 500;
            }
        }
    }
}