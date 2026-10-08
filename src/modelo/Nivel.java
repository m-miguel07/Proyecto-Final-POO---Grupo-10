package modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Nivel {

    private final Personaje personaje;
    private final Aro aro;
    private final List<Enemigo> enemigos;
    private final List<Moneda> monedas;
    private final List<Plataforma> plataformas;
    private final List<Recolectable> recolectables;
    private final List<Pelota> pelotas;

    //Constructor
    public Nivel(Personaje personaje, Aro aro, List<Enemigo> enemigos, List<Moneda> monedas, List<Plataforma> plataformas) {
        this(personaje, aro, enemigos, monedas, plataformas, new ArrayList<>());
    }

    public Nivel(Personaje personaje, Aro aro, List<Enemigo> enemigos, List<Moneda> monedas,
                 List<Plataforma> plataformas, List<Recolectable> recolectables) {
        if (personaje == null || aro == null) {
            throw new IllegalArgumentException("El personaje y el aro son obligatorios");
        }

        this.personaje = personaje;
        this.aro = aro;
        this.enemigos = new ArrayList<>(enemigos);
        this.monedas = new ArrayList<>(monedas);
        this.plataformas = new ArrayList<>(plataformas);
        this.recolectables = new ArrayList<>(recolectables);
        this.pelotas = new ArrayList<>();
    }

    //Getters
    public Personaje getPersonaje() {
        return this.personaje;
    }

    public Aro getAro() {
        return this.aro;
    }

    public List<Enemigo> getEnemigos() {
        return Collections.unmodifiableList(this.enemigos);
    }

    public List<Moneda> getMonedas() {
        return Collections.unmodifiableList(this.monedas);
    }

    public List<Plataforma> getPlataformas() {
        return Collections.unmodifiableList(this.plataformas);
    }

    public List<Recolectable> getRecolectables() {
        return Collections.unmodifiableList(this.recolectables);
    }

    public List<Pelota> getPelotas() {
        return Collections.unmodifiableList(this.pelotas);
    }

    //Comportamientos
    public void actualizar() {
        this.chequearMonedas();
        this.chequearEnemigos();
    }

    public boolean estaCompleto() {
        return this.aro.isEncestado();
    }

    public int contarMonedasRecolectadas() {
        int cantidad = 0;
        for (Moneda moneda : this.monedas) {
            if (moneda.isRecolectada()) {
                cantidad++;
            }
        }
        return cantidad;
    }

    public int getMonedasTotales() {
        return this.monedas.size();
    }

    public boolean hayInterseccionConPlataforma(double posicionX, double posicionY) {
        for (Plataforma plataforma : this.plataformas) {
            if (this.colisionaConRectangulo(posicionX, posicionY, plataforma)) {
                return true;
            }
        }
        return false;
    }

    public boolean hayInterseccionConEnemigo(double posicionX, double posicionY) {
        for (Enemigo enemigo : this.enemigos) {
            if (this.colisionaConRectangulo(posicionX, posicionY, enemigo)) {
                return true;
            }
        }
        return false;
    }

    private boolean colisionaConRectangulo(double posicionX, double posicionY, ObjetoDelJuego objeto) {
        return Colisiones.hayInterseccion(
                posicionX, posicionY, this.personaje.getAncho(), this.personaje.getAlto(),
                objeto.getPosicionX(), objeto.getPosicionY(), objeto.getAncho(), objeto.getAlto());
    }

    private void chequearMonedas() {
        for (Moneda moneda : this.monedas) {
            if (!moneda.isRecolectada()
                    && this.colisionaConRectangulo(this.personaje.getPosicionX(), this.personaje.getPosicionY(), moneda)) {
                moneda.recolectar();
                this.personaje.sumarMoneda();
                this.personaje.sumarPuntaje(100);
            }
        }
    }

    private void chequearEnemigos() {
        if (this.personaje.estaInvulnerable()) {
            return;
        }

        for (Enemigo enemigo : this.enemigos) {
            if (enemigo.puedeAtacar(this.personaje)) {
                this.personaje.perderVida();
                break;
            }
        }
    }

    public void agregarPelota(Pelota pelota) {
        if (pelota != null) {
            this.pelotas.add(pelota);
        }
    }

    public boolean hayPelotaActiva() {
        for (Pelota pelota : this.pelotas) {
            if (pelota.isActiva()) {
                return true;
            }
        }
        return false;
    }

    public void actualizarPelotas(List<Plataforma> plataformasIgnoradas, int anchoPantalla, int altoPantalla) {
        for (int i = this.pelotas.size() - 1; i >= 0; i--) {
            Pelota pelota = this.pelotas.get(i);
            pelota.mover();

            if (pelota.interseca(this.aro)) {
                if (!this.aro.isEncestado()) {
                    this.aro.encestar();
                    this.personaje.sumarPuntaje(500);
                }
                this.pelotas.remove(i);
                continue;
            }

            boolean chocaConPlataforma = false;
            for (Plataforma plataforma : plataformasIgnoradas) {
                if (pelota.interseca(plataforma)) {
                    if (pelota.esDeFuego() && plataforma.esDestructible()) {
                        plataforma.destruir();
                    }
                    chocaConPlataforma = true;
                    break;
                }
            }

            for (Enemigo enemigo : this.enemigos) {
                if (!pelota.interseca(enemigo)) {
                    continue;
                }

                Arma arma = pelota.getArma();
                arma.aplicarDanio(enemigo);

                if (pelota.esDeHielo() && arma instanceof ArmaHielo) {
                    ((ArmaHielo) arma).congelar(enemigo);
                }

                if (pelota.esDeFuego() && arma instanceof ArmaFuego) {
                    ((ArmaFuego) arma).quemar(enemigo);
                }

                chocaConPlataforma = true;
                break;
            }

            if (chocaConPlataforma || pelota.estaFueraDePantalla(anchoPantalla, altoPantalla)) {
                pelota.desactivar();
                this.pelotas.remove(i);
            }
        }
    }

    public void eliminarEnemigosDerrotados() {
        this.enemigos.removeIf(enemigo -> {
            if (enemigo.getPuntosVida() <= 0) {
                this.personaje.sumarPuntaje(150);
                return true;
            }
            return false;
        });
    }

    public void eliminarRecolectados() {
        for (Recolectable recolectable : this.recolectables) {
            if (!recolectable.isRecolectado()) {
                continue;
            }

            if (this.colisionaConRectangulo(this.personaje.getPosicionX(), this.personaje.getPosicionY(), recolectable)) {
                recolectable.recolectar();
                this.personaje.setArmaEquipada(recolectable.getArma());
            }
        }
    }
}