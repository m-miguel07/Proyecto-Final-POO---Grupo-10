package controlador;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.Timer;

import modelo.Arma;
import modelo.EnemigoTerrestre;
import modelo.EnemigoVolador;
import modelo.FabricaNivel1;
import modelo.Nivel;
import modelo.Pelota;
import modelo.Personaje;
import modelo.Plataforma;
import vista.PanelJuego;

public class ControladorJuego {

    private static final int MS_POR_FRAME = 16;

    //Atributos
    private final PanelJuego panel;
    private final Timer timer;
    private long inicio;

    //Constructor
    public ControladorJuego() {
        this.panel = new PanelJuego(FabricaNivel1.crear());
        this.timer = new Timer(MS_POR_FRAME, e -> this.paso());
        this.inicio = System.currentTimeMillis();

        this.registrarTeclado();
        this.panel.setMensaje("NIVEL 1");
    }

    //Getters
    public PanelJuego getPanel() {
        return this.panel;
    }

    public Nivel getNivel() {
        return this.panel.getNivel();
    }

    //Comportamientos
    public void iniciar() {
        this.panel.requestFocusInWindow();
        this.timer.start();
    }

    public void detener() {
        this.timer.stop();
    }

    private void registrarTeclado() {
        this.panel.setFocusTraversalKeysEnabled(false);
        this.panel.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                Personaje personaje = getNivel().getPersonaje();
                int codigo = e.getKeyCode();

                if (codigo == KeyEvent.VK_LEFT || codigo == KeyEvent.VK_A) {
                    personaje.moverHorizontal(-1, FabricaNivel1.ANCHO);
                } else if (codigo == KeyEvent.VK_RIGHT || codigo == KeyEvent.VK_D) {
                    personaje.moverHorizontal(1, FabricaNivel1.ANCHO);
                } else if (codigo == KeyEvent.VK_SPACE || codigo == KeyEvent.VK_W || codigo == KeyEvent.VK_UP) {
                    personaje.saltar();
                } else if (codigo == KeyEvent.VK_J) {
                    ControladorJuego.this.disparar();
                } else if (codigo == KeyEvent.VK_R) {
                    ControladorJuego.this.reiniciar();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                int codigo = e.getKeyCode();
                if (codigo == KeyEvent.VK_LEFT || codigo == KeyEvent.VK_A
                        || codigo == KeyEvent.VK_RIGHT || codigo == KeyEvent.VK_D) {
                    getNivel().getPersonaje().moverHorizontal(0, FabricaNivel1.ANCHO);
                }
            }
        });
    }

    private void disparar() {
        Personaje personaje = this.getNivel().getPersonaje();

        if (!personaje.estaVivo()) {
            return;
        }

        Arma arma = personaje.getArmaEquipada();

        if (arma == null) {
            return;
        }

        int centroX = (int) (personaje.getPosicionX() + personaje.getAncho() / 2);
        int centroY = (int) (personaje.getPosicionY() + personaje.getAlto() / 2);

        Pelota pelota = new Pelota(
                arma,
                centroX - 8,
                centroY - 8,
                16, 16,
                arma.getVelocidad(),
                0);

        this.getNivel().agregarPelota(pelota);
    }

    private void paso() {
        Nivel nivel = this.getNivel();
        Personaje personaje = nivel.getPersonaje();

        List<Plataforma> plataformas = nivel.getPlataformas();

        personaje.aplicarGravedad();
        personaje.actualizarVertical(plataformas, FabricaNivel1.ALTO);

        for (var enemigo : nivel.getEnemigos()) {
            if (enemigo instanceof EnemigoTerrestre) {
                ((EnemigoTerrestre) enemigo).actualizar(plataformas, FabricaNivel1.ANCHO);
            } else if (enemigo instanceof EnemigoVolador) {
                ((EnemigoVolador) enemigo).actualizar((System.currentTimeMillis() - inicio) / 1000.0);
            }
        }

        nivel.actualizar();
        nivel.eliminarRecolectados();
        nivel.eliminarEnemigosDerrotados();
        nivel.actualizarPelotas(plataformas, FabricaNivel1.ANCHO, FabricaNivel1.ALTO);

        long transcurrido = System.currentTimeMillis() - this.inicio;

        if (!personaje.estaVivo()) {
            this.panel.setMensaje("PERDISTE - R para reiniciar");
        } else if (nivel.estaCompleto()) {
            this.panel.setMensaje("NIVEL COMPLETO");
        } else {
            this.panel.setMensaje("");
        }

        this.panel.setTiempoTranscurrido(transcurrido);
        this.panel.repaint();
    }

    public void reiniciar() {
        this.detener();
        this.panel.setNivel(FabricaNivel1.crear());
        this.inicio = System.currentTimeMillis();
        this.panel.setMensaje("NIVEL 1");
        this.panel.requestFocusInWindow();
        this.timer.start();
    }
}