package controlador;

import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.SwingUtilities;

import java.awt.event.KeyEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;

import modelo.GestorAudio;

import vista.PanelMenuPrincipal;
import vista.Ventana;

public class ControladorPrincipal{
    private final Ventana ventana;
    private final GestorAudio gestorAudio;

    private final int tiempoSplashMS = 3000;
    private final AtomicBoolean videoTerminado;

    private ControladorJuego controladorJuego;

    @SuppressWarnings("this-escape")
    public ControladorPrincipal(){
        this.ventana =  new Ventana();
        this.gestorAudio = GestorAudio.getInstancia();

        this.videoTerminado = new AtomicBoolean(false);

        //Listeners del menu principal

        //Secuencia de "Presione ENTER" a panel botones
        this.ventana.getPanelMenu().setFocusable(true); //El panel requiere focus para leer el input del teclado
        this.ventana.getPanelMenu().addKeyListener(new KeyAdapter() {
            @Override 
            public void keyPressed(KeyEvent e){
                if (e.getKeyCode() == KeyEvent.VK_ENTER){
                    if (ventana.getPanelMenu().getPanelEnter().isVisible()){
                       ventana.getPanelMenu().mostrarPanel(PanelMenuPrincipal.BOTONES);;
                       gestorAudio.reproducirEfecto("boton-enter");
                    }
                }
            }
        });

        this.ventana.getPanelMenu().addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                SwingUtilities.invokeLater(() -> ventana.getPanelMenu().requestFocusInWindow()); //Solicita dicho focus
            }
        });

        //Boton de nuevo juego
                this.ventana.getPanelMenu()
                            .getBotonNuevoJuego()
                            .addActionListener(e -> this.iniciarJuego());

        //Boton de opciones
                this.ventana.getPanelMenu()
                            .getBotonOpciones()
                            .addActionListener(e -> {
                               this.ventana.getPanelMenu().mostrarPanel(PanelMenuPrincipal.OPCIONES);
                            });
        //Boton de salir (Opciones)
                this.ventana.getPanelMenu()
                            .getPanelOpciones()
                            .getBotonSalir()
                            .addActionListener(e -> {
                                this.ventana.getPanelMenu().mostrarPanel(PanelMenuPrincipal.BOTONES);
                            });

        //Boton de Salir (Menu principal)
        this.ventana.getPanelMenu()
                    .getBotonSalir()
                    .addActionListener(e -> System.exit(0));

       
        //Inicio de secuencia (Splash -> Video -> Menu)
        this.iniciarSecuencia();
    }

    public void iniciarSecuencia(){
        ventana.mostrarTarjeta(Ventana.CARD_SPLASH_MATERIA);
        ventana.setVisible(true);

        new Thread(() -> {
            try {
                Thread.sleep(tiempoSplashMS);
                SwingUtilities.invokeLater(this::iniciarVideoIntro);
                
            } catch(InterruptedException e){
                Thread.currentThread().interrupt();
            }
        }).start();
    }

    private void iniciarVideoIntro(){
        ventana.mostrarTarjeta(Ventana.CARD_VIDEO);
        ventana.getPanelVideo().cargarYReproducir(
            "src/assets/intro.mp4",
            Ventana.ANCHO_PANTALLA, //Corrige problema de video recortado
            Ventana.ALTO_PANTALLA,
            this::finalizarVideoYMostrarMenu, 
            this::finalizarVideoYMostrarMenu
        );
    }

    private void finalizarVideoYMostrarMenu() {
        if (videoTerminado.compareAndSet(false, true)){
            ventana.getPanelVideo().detenerYLiberar();
        }

        SwingUtilities.invokeLater(() -> {
            ventana.mostrarTarjeta(Ventana.CARD_MENU_PRINCIPAL);
            gestorAudio.reproducirMusica("src/assets/bgm/menu.mp3", 1, true);
        });
    }

    public void iniciarJuego(){
        SwingUtilities.invokeLater(() -> {
            if (this.controladorJuego == null){
                this.controladorJuego = new ControladorJuego();
            }

            this.ventana.setPanelJuego(this.controladorJuego.getPanel());
            this.ventana.mostrarTarjeta(Ventana.CARD_JUEGO);
            this.ventana.getPanelJuego().requestFocusInWindow();
            this.controladorJuego.iniciar();
        });
    }
}