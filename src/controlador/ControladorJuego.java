package controlador;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import javax.swing.Timer;
import modelo.Arma;
import modelo.EnemigoTerrestre;
import modelo.EnemigoVolador;
import modelo.FabricaNivel1;
import modelo.GestorAudio;
import modelo.Nivel;
import modelo.Pelota;
import modelo.Personaje;
import modelo.Plataforma;
import vista.PanelJuego;

public class ControladorJuego {
    //Constantes
    private static final int MS_POR_FRAME = 16;

    //Atributos
    private final PanelJuego panel;
    private final Timer timer;
    private final GestorAudio gestorAudio;
    private long inicio;

    private final ListenerNavegacion listener;

    private boolean teclaIzquierda;
    private boolean teclaDerecha;
    private boolean teclaSaltar;
    private boolean saltoPendiente;
    private boolean teclaDisparo;
    private boolean enPausa;

    //Constructor
    public ControladorJuego(ListenerNavegacion listener) {
        this.panel = new PanelJuego(FabricaNivel1.crear(), "src/assets/lvl-bg/Nivel1_1.png");
        this.timer = new Timer(MS_POR_FRAME, e -> this.paso());
        this.listener = listener;
        this.gestorAudio = GestorAudio.getInstancia();
        this.inicio = System.currentTimeMillis();

        panel.getPanelPausa().getBotonSalir().addActionListener(e -> {
            pausar();
        });

        panel.getPanelPausa().getBotonSalida().addActionListener(e ->{
            detener();
            gestorAudio.detenerMusica();
            reiniciarEstado();
            panel.togglePausa();

            this.listener.volverAlMenu(); //notifica que se solicito la salida
        });

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

    private void iniciarMusica(){
        gestorAudio.reproducirMusica("src/assets/Musica/musica_fondo_1.mp3", true);
    }

    //Gameloop
    public void iniciar() {
        this.teclaIzquierda = false;
        this.teclaDerecha = false;
        this.teclaSaltar = false;
        this.saltoPendiente = false;
        this.teclaDisparo = false;
        this.enPausa = false;

        this.panel.requestFocusInWindow();
        this.timer.start();
    }

    public void detener() {
        this.timer.stop();
        gestorAudio.pausarMusica();
    }

    public void pausar(){
        if (!enPausa){
            enPausa = true;
            detener();
        } else {
            enPausa = false;
            gestorAudio.reanudarMusica();
            iniciar();
        }

         panel.togglePausa();
                
    }

    public void reiniciar() {

        this.detener();
        gestorAudio.detenerMusica();

        reiniciarEstado();

        iniciarMusica();
        
        this.panel.requestFocusInWindow();
        this.timer.start();
    }

    private void reiniciarEstado() {
        this.teclaIzquierda = false;
        this.teclaDerecha = false;
        this.teclaSaltar = false;
        this.saltoPendiente = false;
        this.teclaDisparo = false;
        this.enPausa = false;
        this.panel.setNivel(FabricaNivel1.crear());
        this.inicio = System.currentTimeMillis();
        this.panel.setMensaje("NIVEL 1");
    }

    //Desplazamiento
    private void registrarTeclado() {
        this.panel.setFocusTraversalKeysEnabled(false);
        this.panel.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                int codigo = e.getKeyCode();

                if (codigo == KeyEvent.VK_R) {
                    ControladorJuego.this.reiniciar();
                    return;
                }

                if (!getNivel().getPersonaje().estaVivo() || getNivel().estaCompleto()) {
                    return;
                }

                if (codigo == KeyEvent.VK_LEFT || codigo == KeyEvent.VK_A) {
                    teclaIzquierda = true;
                } else if (codigo == KeyEvent.VK_RIGHT || codigo == KeyEvent.VK_D) {
                    teclaDerecha = true;
                } else if (codigo == KeyEvent.VK_SPACE || codigo == KeyEvent.VK_W || codigo == KeyEvent.VK_UP) {
                    if (!teclaSaltar) {
                        saltoPendiente = true;
                    }
                    teclaSaltar = true;
                } else if (codigo == KeyEvent.VK_J) {
                    if (!teclaDisparo) {
                        teclaDisparo = true;
                        ControladorJuego.this.disparar();
                    }
                }

                if (codigo == KeyEvent.VK_ESCAPE){
                    pausar();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                int codigo = e.getKeyCode();

                if (codigo == KeyEvent.VK_LEFT || codigo == KeyEvent.VK_A) {
                    teclaIzquierda = false;
                } else if (codigo == KeyEvent.VK_RIGHT || codigo == KeyEvent.VK_D) {
                    teclaDerecha = false;
                } else if (codigo == KeyEvent.VK_SPACE || codigo == KeyEvent.VK_W || codigo == KeyEvent.VK_UP) {
                    teclaSaltar = false;
                } else if (codigo == KeyEvent.VK_J) {
                    teclaDisparo = false;
                }
            }
        });
    }

    //Mecanicas de juego
    private void disparar() {
        Personaje personaje = this.getNivel().getPersonaje();

        if (!personaje.estaVivo()) {
            return;
        }

        if (this.getNivel().hayPelotaActiva()) {
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
                arma.getVelocidad() * personaje.getDireccion(),
                0);

        this.getNivel().agregarPelota(pelota);
    }

    private void paso() {
        Nivel nivel = this.getNivel();
        Personaje personaje = nivel.getPersonaje();

        long transcurrido = System.currentTimeMillis() - this.inicio;

        if (!personaje.estaVivo()) {
            this.panel.setMensaje("PERDISTE - R para reiniciar");
            this.panel.setTiempoTranscurrido(transcurrido);
            this.panel.repaint();
            return;
        }

        if (nivel.estaCompleto()) {
            this.panel.setMensaje("NIVEL COMPLETO");
            this.panel.setTiempoTranscurrido(transcurrido);
            this.panel.repaint();
            return;
        }

        List<Plataforma> plataformas = nivel.getPlataformas();

        int direccion = 0;
        if (this.teclaIzquierda && !this.teclaDerecha) {
            direccion = -1;
        } else if (this.teclaDerecha && !this.teclaIzquierda) {
            direccion = 1;
        }

        personaje.actualizarHorizontal(direccion, FabricaNivel1.ANCHO);

        if (this.saltoPendiente) {
            personaje.saltar();
            this.saltoPendiente = false;
        }

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

        this.panel.setMensaje("");
        this.panel.setTiempoTranscurrido(transcurrido);
        this.panel.repaint();
    }

}