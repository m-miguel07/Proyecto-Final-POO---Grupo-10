package modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GestorPelotas {

    //Constantes
    private static final int PUNTAJE_ENCESTADO = 500;

    //Atributos
    private final List<Pelota> pelotas;
    private final List<Pelota> pelotasVista;

    //Constructor
    public GestorPelotas() {
        this.pelotas = new ArrayList<>();
        this.pelotasVista = Collections.unmodifiableList(this.pelotas);
    }

    //Getters
    public List<Pelota> getPelotas() {
        return this.pelotasVista;
    }

    //Comportamientos
    public void agregar(Pelota pelota) {
        if (pelota == null) {
            throw new IllegalArgumentException("La pelota no puede ser nula");
        }

        this.pelotas.add(pelota);
    }

    public void actualizar(Aro aro, Personaje personaje, List<Enemigo> enemigos,
                           List<Plataforma> plataformas, List<Boton> botones,
                           int anchoPantalla, int altoPantalla, int deltaMS) {
        GestorAudio gestorAudio = GestorAudio.getInstancia();

        for (int i = this.pelotas.size() - 1; i >= 0; i--) {
            Pelota pelota = this.pelotas.get(i);

            double prevX = pelota.getPosicionX();
            double prevY = pelota.getPosicionY();

            pelota.aplicarGravedad();
            pelota.mover();

            // Detecta si la pelota impacto con el aro.
            if (aro != null && pelota.interseca(aro)) {
                if (!aro.isEncestado()) {
                    aro.encestar();
                    personaje.sumarPuntaje(PUNTAJE_ENCESTADO);
                    gestorAudio.reproducirEfecto(GestorAudio.ENCESTADO);
                }
                pelota.desactivar();
                this.pelotas.remove(i);
                continue;
            }

            // Detecta si la pelota activo un boton.
            boolean activoBoton = false;
            for (Boton boton : botones) {
                if (boton.isActivado() || !pelota.interseca(boton)) {
                    continue;
                }

                boton.activar();
                activoBoton = true;
                break;
            }

            if (activoBoton) {
                pelota.desactivar();
                this.pelotas.remove(i);
                continue;
            }

            // Detecta si la pelota impacto con un enemigo y desaparece.
            boolean impactoEnemigo = false;
            for (Enemigo enemigo : enemigos) {
                if (!pelota.interseca(enemigo)) {
                    continue;
                }

                Arma arma = pelota.getArma();
                arma.aplicarDanio(enemigo);

                gestorAudio.reproducirEfecto(GestorAudio.GOLPE_PELOTA);
                impactoEnemigo = true;
                break;
            }

            if (impactoEnemigo) {
                pelota.desactivar();
                this.pelotas.remove(i);
                continue;
            }

            // Detecta si la pelota impacto con alguna plataforma.
            for (Plataforma plataforma : plataformas) {
                if (plataforma.isDestruida()) {
                    continue;
                }

                //La plataforma negra permite que la pelota atraviese,
                //vuelve a tener colisión una vez que la atraviese.
                if (plataforma.permitePasarPelota() && pelota.getVelocidadY() <= 0) {
                    continue;
                }

                if (pelota.interseca(plataforma)) {
                    if (pelota.esDeFuego() && plataforma.esDestructible()) {
                        plataforma.destruir();
                        continue;
                    }

                    boolean rebotarArriba = (prevY + pelota.getAlto() <= plataforma.getPosicionY());
                    boolean rebotarAbajo = (prevY >= plataforma.getPosicionY() + plataforma.getAlto());
                    boolean rebotarIzquierda = (prevX + pelota.getAncho() <= plataforma.getPosicionX());
                    boolean rebotarDerecha = (prevX >= plataforma.getPosicionX() + plataforma.getAncho());

                    boolean sonidoRebote = false;

                    if (rebotarArriba) {
                        sonidoRebote = pelota.rebotarVertical(plataforma.getPosicionY() - pelota.getAlto(), Pelota.RESTITUCION);
                    } else if (rebotarAbajo) {
                        sonidoRebote = pelota.rebotarVertical(plataforma.getPosicionY() + plataforma.getAlto(), Pelota.RESTITUCION);
                    } else if (rebotarIzquierda) {
                        sonidoRebote = pelota.rebotarHorizontal(plataforma.getPosicionX() - pelota.getAncho(), Pelota.RESTITUCION);
                    } else if (rebotarDerecha) {
                        sonidoRebote = pelota.rebotarHorizontal(plataforma.getPosicionX() + plataforma.getAncho(), Pelota.RESTITUCION);
                    } else {
                        if (pelota.getVelocidadY() >= 0) {
                            sonidoRebote = pelota.rebotarVertical(plataforma.getPosicionY() - pelota.getAlto(), Pelota.RESTITUCION);
                        } else {
                            sonidoRebote = pelota.rebotarVertical(plataforma.getPosicionY() + plataforma.getAlto(), Pelota.RESTITUCION);
                        }
                    }

                    if (sonidoRebote) {
                        gestorAudio.reproducirEfecto(GestorAudio.BOTE_PELOTA);
                    }
                }
            }

            // La pelota rebota en los limites lataerales de la pantalla.
            if (pelota.getPosicionX() < 0) {
                if (pelota.rebotarHorizontal(0, Pelota.RESTITUCION)) {
                    gestorAudio.reproducirEfecto(GestorAudio.BOTE_PELOTA);
                }
            } else if (pelota.getPosicionX() + pelota.getAncho() > anchoPantalla) {
                if (pelota.rebotarHorizontal(anchoPantalla - pelota.getAncho(), Pelota.RESTITUCION)) {
                    gestorAudio.reproducirEfecto(GestorAudio.BOTE_PELOTA);
                }
            }

            // La pelota desaparece si no se mueve en el suelo.
            pelota.actualizarInactividad(deltaMS);

            // La pelota desaparece si esta fuera de la pantalla.
            if (!pelota.isActiva() || pelota.estaFueraDePantalla(anchoPantalla, altoPantalla)) {
                pelota.desactivar();
                this.pelotas.remove(i);
            }
        }
    }
}
