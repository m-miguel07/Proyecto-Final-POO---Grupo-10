package controlador;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
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
    private static final long COOLDOWN_DISPARO_MS = 2000;

    //Atributos
    private final PanelJuego panel;
    private final Timer timer;
    private final GestorAudio gestorAudio;
    private long tiempoTranscurridoMS;
    private long ultimoDisparoMS = -COOLDOWN_DISPARO_MS;

    private final ListenerNavegacion listener;

    private int mouseX = -1;
    private int mouseY = -1;

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
        this.tiempoTranscurridoMS = 0;

        panel.getPanelPausa().getBotonSalir().addActionListener(e -> {
            pausar();
        });

        panel.getPanelPausa().getBotonReiniciar().addActionListener(e ->{
            panel.togglePausa();
            ControladorJuego.this.reiniciar();
        });

        panel.getPanelPausa().getBotonSalida().addActionListener(e ->{
            detener();
            gestorAudio.detenerMusica();
            reiniciarEstado();
            panel.togglePausa();

            this.listener.volverAlMenu(); //notifica que se solicito la salida
        });

        this.registrarTeclado();
        this.registrarMouse();
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
        this.tiempoTranscurridoMS = 0;
        this.ultimoDisparoMS = -COOLDOWN_DISPARO_MS;
        this.panel.setTiempoTranscurrido(0);
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

    private void registrarMouse() {
        this.panel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();
            }
        });

        this.panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    if (!enPausa) {
                        mouseX = e.getX();
                        mouseY = e.getY();
                        ControladorJuego.this.disparar();
                    }
                }
            }
        });
    }

    //Mecanicas de juego
    private void disparar() {
        Personaje personaje = this.getNivel().getPersonaje();

        if (!personaje.estaVivo() || this.getNivel().estaCompleto() || this.enPausa) {
            return;
        }

        // Cooldown de 2 segundos entre disparos
        if (this.tiempoTranscurridoMS - this.ultimoDisparoMS < COOLDOWN_DISPARO_MS) {
            return;
        }

        Arma arma = personaje.getArmaEquipada();

        if (arma == null) {
            return;
        }

        int centroX = (int) (personaje.getPosicionX() + personaje.getAncho() / 2);
        int centroY = (int) (personaje.getPosicionY() + personaje.getAlto() / 2);

        double vx;
        double vy;

        if (this.mouseX >= 0 && this.mouseY >= 0) {
            // Apuntado con el mouse
            double dx = this.mouseX - centroX;
            double dy = this.mouseY - centroY;
            double distancia = Math.hypot(dx, dy);

            if (distancia < 1.0) {
                distancia = 1.0;
                dx = personaje.getDireccion();
                dy = 0;
            }

            // Orientar al personaje hacia el objetivo
            if (dx > 0) {
                personaje.setDireccion(1);
            } else if (dx < 0) {
                personaje.setDireccion(-1);
            }

            double velocidadTiro = Math.max(arma.getVelocidad(), 16.0);
            vx = (dx / distancia) * velocidadTiro;
            vy = (dy / distancia) * velocidadTiro;
        } else {
            // Tiro por defecto hacia adelante
            vx = arma.getVelocidad() * personaje.getDireccion();
            vy = -12.0;
        }

        Pelota pelota = new Pelota(
                arma,
                centroX - 8,
                centroY - 8,
                16, 16,
                vx,
                vy);

        this.getNivel().agregarPelota(pelota);
        this.ultimoDisparoMS = this.tiempoTranscurridoMS;
        gestorAudio.reproducirEfecto(GestorAudio.PELOTA_LANZAMIENTO);
    }

    private void paso() {
        Nivel nivel = this.getNivel();
        Personaje personaje = nivel.getPersonaje();

        if (!personaje.estaVivo()) {
            this.panel.setMensaje("PERDISTE - R para reiniciar");
            this.panel.repaint();
            this.timer.stop();
            return;
        }

        if (nivel.estaCompleto()) {
            this.panel.setMensaje("NIVEL COMPLETO");
            this.panel.repaint();
            this.timer.stop();
            return;
        }

        // Solo sumamos tiempo si el juego está activo
        this.tiempoTranscurridoMS += MS_POR_FRAME;
        this.panel.setTiempoTranscurrido(this.tiempoTranscurridoMS);

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
        personaje.actualizarVertical(plataformas, nivel.getEnemigos(), FabricaNivel1.ALTO);
        personaje.actualizarArmaTemporal(MS_POR_FRAME);

        for (var enemigo : nivel.getEnemigos()) {
            enemigo.actualizarEstados(MS_POR_FRAME);

            if (enemigo instanceof EnemigoTerrestre) {
                ((EnemigoTerrestre) enemigo).actualizar(plataformas, FabricaNivel1.ANCHO);
            } else if (enemigo instanceof EnemigoVolador) {
                ((EnemigoVolador) enemigo).actualizar(this.tiempoTranscurridoMS / 1000.0);
            }
        }

        nivel.actualizar();
        nivel.eliminarRecolectados();
        nivel.eliminarEnemigosDerrotados();
        nivel.actualizarPelotas(FabricaNivel1.ANCHO, FabricaNivel1.ALTO);

        this.panel.setMensaje("");
        this.panel.repaint();
    }

}