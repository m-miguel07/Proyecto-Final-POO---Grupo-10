package controlador;

import java.util.ArrayList;
import java.util.List;
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
import modelo.Puntaje;
import modelo.PuntajeDAO;

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
    private final PuntajeDAO puntajeDAO;

    private Partida partida;
    private List<Puntaje> puntajes;

    public ControladorPrincipal() throws SQLException{

        partidaDAO = new PartidaDAOSQLite();
        puntajeDAO = new PuntajeDAOSQLite();
        
        this.partida = partidaDAO.cargar();
        this.puntajes = new ArrayList<Puntaje>(); 

        if (partida == null){
            this.partida = new Partida(0,1,0,0.25,0.50);
        }

        this.ventana =  new Ventana();
        this.gestorAudio = GestorAudio.getInstancia();

        if (partida != null){
            cargarConfiguracionAudio();
        }
       
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

        //Boton de puntajes
                this.ventana.getPanelMenu()
                            .getBotonRanking()
                            .addActionListener(e -> {
                                this.puntajes = puntajeDAO.mejores(3);

                                this.ventana.getPanelMenu().mostrarPanel(PanelMenuPrincipal.RANKING);
                                this.ventana.getPanelMenu().getPanelRanking().mostrarPuntajes(puntajes);
                            });

        //Boton de salir (Puntajes)

                this.ventana.getPanelMenu()
                            .getPanelRanking()
                            .getBotonSalir()
                            .addActionListener(e -> { 
                                this.ventana.getPanelMenu().mostrarPanel(PanelMenuPrincipal.BOTONES);
                            });
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

    //Persistencia 

    //Extrae de los datos de guardado los valores de volumen
    private void cargarConfiguracionAudio(){
        if (this.partida != null){
            
            double volumenBGM = partida.getVolumenBGM();
            double volumenSFX = partida.getVolumenSFX();

            gestorAudio.setVolumenBGM(volumenBGM);
            gestorAudio.setVolumenSFX(volumenSFX);

            ventana.getPanelMenu().getPanelOpciones().setVolumenes(volumenSFX,volumenBGM); //Actualiza la visual de los sliders acorde al volumen guardado
        }
    }

    public void persistirPartida() {
        if (!partidaDAO.guardar(this.partida)) {
            System.err.println("No se pudo persistir la partida.");
        }
    }

    public boolean borrarPartida(){
        if (!partidaDAO.eliminar()) {
            System.err.println("No se pudo borrar la partida.");
            return false;
        }

        double SFXprevio = partida.getVolumenSFX();
        double BGMprevio = partida.getVolumenBGM();

        this.partida = new Partida(0, 1, 0, SFXprevio, BGMprevio);
        return true;
    }

    //Manejo de secuencia inicial: splash y video de intro
    public void iniciarSecuencia(){
        ventana.mostrarTarjeta(Ventana.CARD_SPLASH_MATERIA);
        ventana.setVisible(true);
        
        new Thread(() -> {
            try {
                Thread.sleep(tiempoSplashMS);
                SwingUtilities.invokeLater(this::iniciarVideoIntro);
                
            } catch(InterruptedException e){
                
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

    // Cerrar controlador ; Nuevo Juego ; Continuar ; Volver al menu ; Salir del juego

    public void cerrarControladorJuego(){
        if (this.controladorJuego != null){
            this.controladorJuego.detener();
            this.controladorJuego = null;
        }
    }

    private void iniciarJuegoNuevo(){
        
        if (!borrarPartida()){
            return;
        }

        cerrarControladorJuego();
        iniciarJuego();
    }

    public void iniciarJuego(){
        SwingUtilities.invokeLater(() -> {
            if (this.controladorJuego == null){
                this.controladorJuego = new ControladorJuego(this.partida, this.puntajeDAO, this);
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

    private void cerrarJuego(){
        persistirPartida();
        System.exit(0);
    }
}