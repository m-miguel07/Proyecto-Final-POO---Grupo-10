package modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GestorPelotas {

    //Constantes
    private static final int PUNTAJE_ENCESTADO = 500;
    private static final int MS_POR_FRAME = 16;

    //Atributos
    private final List<Pelota> pelotas;

    //Constructor
    public GestorPelotas() {
        this.pelotas = new ArrayList<>();
    }

    //Getters
    public List<Pelota> getPelotas() {
        return Collections.unmodifiableList(this.pelotas);
    }

    //Comportamientos
    public void agregar(Pelota pelota) {
        if (pelota != null) {
            this.pelotas.add(pelota);
        }
    }

    public void actualizar(Aro aro, Personaje personaje, List<Enemigo> enemigos,
                           List<Plataforma> plataformas, int anchoPantalla, int altoPantalla) {
        GestorAudio gestorAudio = GestorAudio.getInstancia();

        for (int i = this.pelotas.size() - 1; i >= 0; i--) {
            Pelota pelota = this.pelotas.get(i);

            double prevX = pelota.getPosicionX();
            double prevY = pelota.getPosicionY();

            pelota.aplicarGravedad();
            pelota.mover();

            // 1. Detección con el Aro (Objetivo final)
            if (pelota.interseca(aro)) {
                if (!aro.isEncestado()) {
                    aro.encestar();
                    personaje.sumarPuntaje(PUNTAJE_ENCESTADO);
                    gestorAudio.reproducirEfecto(GestorAudio.ENCESTADO);
                }
                pelota.desactivar();
                this.pelotas.remove(i);
                continue;
            }

            // 2. Detección con Enemigos (La pelota impacta, hace daño y desaparece)
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

            // 3. Detección y rebotes con Plataformas
            for (Plataforma plataforma : plataformas) {
                if (plataforma.isDestruida()) {
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

            // 4. Rebote en límites laterales de pantalla
            if (pelota.getPosicionX() < 0) {
                if (pelota.rebotarHorizontal(0, Pelota.RESTITUCION)) {
                    gestorAudio.reproducirEfecto(GestorAudio.BOTE_PELOTA);
                }
            } else if (pelota.getPosicionX() + pelota.getAncho() > anchoPantalla) {
                if (pelota.rebotarHorizontal(anchoPantalla - pelota.getAncho(), Pelota.RESTITUCION)) {
                    gestorAudio.reproducirEfecto(GestorAudio.BOTE_PELOTA);
                }
            }

            // 5. Inactividad cuando se detiene en el suelo
            pelota.actualizarInactividad(MS_POR_FRAME);

            // 6. Eliminar si sale de la pantalla o se desactivó
            if (!pelota.isActiva() || pelota.estaFueraDePantalla(anchoPantalla, altoPantalla)) {
                pelota.desactivar();
                this.pelotas.remove(i);
            }
        }
    }
}
