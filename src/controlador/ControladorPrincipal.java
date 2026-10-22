package controlador;

import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.JSlider;
import javax.swing.SwingUtilities;

import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;

import modelo.GestorAudio;
import modelo.Partida;
import modelo.PartidaDAO;

import vista.PanelMenuPrincipal;
import vista.Ventana;

import persistencia.*;

public class ControladorPrincipal{
    private final Ventana ventana;
    private final GestorAudio gestorAudio;

    private final int tiempoSplashMS = 3000;
    private final AtomicBoolean videoTerminado;                                     

    private ControladorJuego controladorJuego;

    private final PartidaDAO partidaDAO;
    private  Partida partida;

    public ControladorPrincipal() throws SQLException{

        partidaDAO = new PartidaDAOSQLite();
        
        partida = partidaDAO.cargar();

        if (partida == null){
            this.partida = new Partida(0,1,0,0.10,0.10);
        }

        this.ventana =  new Ventana();
        this.gestorAudio = GestorAudio.getInstancia();
        cargarConfiguracionAudio();

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
                       gestorAudio.reproducirEfecto(GestorAudio.BOTON_INICIO);
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
                            .addActionListener(e -> this.iniciarJuegoNuevo());

        //Boton de continuar
                this.ventana.getPanelMenu()
                            .getBotonContinuar()
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
                                //Guarda la configuración
                                partida.setVolumenBGM(gestorAudio.getVolumenBGM());
                                partida.setVolumenSFX(gestorAudio.getVolumenSFX());

                                this.ventana.getPanelMenu().mostrarPanel(PanelMenuPrincipal.BOTONES);
                            });

        //Sliders (Opciones)
    
        //Musica 
            JSlider sliderBGM = this.ventana.getPanelMenu().getPanelOpciones().getSliderMusica();
            JSlider sliderSFX = this.ventana.getPanelMenu().getPanelOpciones().getSliderSFX();

            sliderBGM.addChangeListener(e -> {
                double valor = sliderBGM.getValue() / 100.0; //Ajuste al rango de 0.0 a 1.0
                gestorAudio.setVolumenBGM(valor);
                gestorAudio.reproducirEfecto(GestorAudio.BOTON_CLICK);
            });

            sliderSFX.addChangeListener(e -> {
                double valor = sliderSFX.getValue() / 100.0;
                gestorAudio.setVolumenSFX(valor);
                gestorAudio.reproducirEfecto(GestorAudio.BOTON_CLICK);
            });
                           
        //Boton de Salir (Menu principal)
        this.ventana.getPanelMenu()
                    .getBotonSalir()
                    .addActionListener(e -> cerrarJuego());

        //Inicio de secuencia (Splash -> Video -> Menu)
        this.iniciarSecuencia();
    }

    private void cargarConfiguracionAudio(){
        if (this.partida != null){
            partida.setVolumenBGM(0.10);
            partida.setVolumenSFX(0.10);
            gestorAudio.setVolumenBGM(0.10);
            gestorAudio.setVolumenSFX(0.10);

            ventana.getPanelMenu().getPanelOpciones().setVolumenes(0.10, 0.10); //Actualiza la visual de los sliders acorde al volumen guardado
        }
    }

    private void cerrarJuego(){
        partidaDAO.guardar(this.partida); 
        System.exit(0);
    }

    private void nuevoJuego(){
        partidaDAO.eliminar();
        this.part
    }

    public void iniciarSecuencia(){
        ventana.mostrarTarjeta(Ventana.CARD_SPLASH_MATERIA);
        ventana.setVisible(true);

        new Thread(() -> {
            try {
                Thread.sleep(tiempoSplashMS);
                SwingUtilities.invokeLater(this::iniciarVideoIntro);
                
            } catch(InterruptedException e){
                //Thread.currentThread().interrupt();
            }
        }).start();
    }

    private void iniciarVideoIntro(){
        ventana.mostrarTarjeta(Ventana.CARD_VIDEO);
        ventana.getPanelVideo().cargarYReproducir(
            "src/assets/intro.mp4",
            Ventana.ANCHO_PANTALLA, 
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
            gestorAudio.reproducirMusica("src/assets/Musica/musica_menu.mp3", true);
        });
    }

    private void iniciarJuegoNuevo(){
        if (this.controladorJuego != null){
            this.controladorJuego.detener();
            this.controladorJuego = null;
        }

        partidaDAO.eliminar();
        this.partida = new Partida(0, 1, 0, 0.10, 0.10);
        iniciarJuego();
    }

    public void iniciarJuego(){
        SwingUtilities.invokeLater(() -> {
            if (this.controladorJuego == null){
                this.controladorJuego = new ControladorJuego(this.partida, this);
            }

            gestorAudio.reproducirMusica("src/assets/Musica/musica_fondo_1.mp3", true);
            this.ventana.setPanelJuego(this.controladorJuego.getPanel());
            this.ventana.mostrarTarjeta(Ventana.CARD_JUEGO);
            this.ventana.getPanelJuego().requestFocusInWindow();
            this.controladorJuego.iniciar();
        });
    }

    public void volverAlMenu(){
        ventana.mostrarTarjeta(Ventana.CARD_MENU_PRINCIPAL);
        gestorAudio.reproducirMusica("src/assets/Musica/musica_menu.mp3", true);
    }
}