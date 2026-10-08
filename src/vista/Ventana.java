package vista;

import java.awt.CardLayout;
import java.awt.Dimension;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

//EN PROGRESO

public class Ventana extends JFrame {
    
    public static final int ANCHO_PANTALLA = 1920;
    public static final int ALTO_PANTALLA = 1080;

    public static final String CARD_SPLASH_MATERIA = "SPLASH_MATERIA";
    public static final String CARD_VIDEO = "SPLASH_VIDEO";
    public static final String CARD_MENU_PRINCIPAL = "MENU_PRINCIPAL";
    public static final String CARD_JUEGO = "JUEGO";


    private final CardLayout cardLayout;
    private final JPanel contenedor;
    private final PanelVideoIntro panelVideo;
    private final PanelMenuPrincipal panelMenu;
    private PanelJuego panelJuego;


    public Ventana(){
        this.setTitle("Basketball Fever");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setResizable(false);
        this.setUndecorated(true);

        this.panelMenu = new PanelMenuPrincipal("src/assets/menu-bg.jpeg");

        //Establecer icono de la app
        try {
            File archivoIcono = new File("src/assets/icon.png");
            if (archivoIcono.exists()) {
                Image icono = ImageIO.read(archivoIcono);
                this.setIconImage(icono);
            } else {
                System.err.println("No se encontro el icono: " + archivoIcono.getPath());
            }
        } catch(IOException | RuntimeException e) {
            System.err.println("No se pudo cargar icono.");
        }
      
        cardLayout = new CardLayout();
        contenedor = new JPanel(cardLayout);

        contenedor.setPreferredSize(new Dimension(ANCHO_PANTALLA, ALTO_PANTALLA));

        contenedor.add(new Splash("src/assets/splash.png"), CARD_SPLASH_MATERIA);

        this.panelVideo = new PanelVideoIntro(() -> mostrarTarjeta(CARD_MENU_PRINCIPAL));
        panelVideo.setPreferredSize(new Dimension(ANCHO_PANTALLA,ALTO_PANTALLA));
        contenedor.add(panelVideo, CARD_VIDEO);

        contenedor.add(panelMenu, CARD_MENU_PRINCIPAL);

        this.add(contenedor);
    }

    public void setPanelJuego(PanelJuego panelJuego) {
        if (panelJuego == null) {
            return;
        }

        if (this.panelJuego != null) {
            contenedor.remove(this.panelJuego);
        }

        this.panelJuego = panelJuego;
        contenedor.add(panelJuego, CARD_JUEGO);
        contenedor.revalidate();
    }

    public PanelJuego getPanelJuego() {
        return this.panelJuego;
    }

    public PanelVideoIntro getPanelVideo(){
        return this.panelVideo;
    }

    public PanelMenuPrincipal getPanelMenu(){
        return this.panelMenu;
    }

    public void mostrarTarjeta(String nombre){
        cardLayout.show(contenedor,nombre);
        contenedor.revalidate();
        contenedor.repaint();
    }

    
}
