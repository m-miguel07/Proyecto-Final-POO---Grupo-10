package vista;

import java.awt.CardLayout;
import java.awt.Dimension;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Image;
import java.io.IOException;

//EN PROGRESO

public class Ventana extends JFrame {
    
    public static final int ANCHO_PANTALLA = 1920;
    public static final int ALTO_PANTALLA = 1080;

    public static final String CARD_SPLASH_MATERIA = "SPLASH_MATERIA";
    public static final String CARD_VIDEO = "SPLASH_VIDEO";
    public static final String CARD_MENU_PRINCIPAL = "MENU_PRINCIPAL";
    

    private final CardLayout cardLayout;
    private final JPanel contenedor;
    private final PanelVideoIntro panelVideo;
    private final PanelMenuPrincipal panelMenu;


    public Ventana(){
        this.setTitle("Basketball Fever");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setResizable(false);
        this.setUndecorated(true);

        this.panelMenu = new PanelMenuPrincipal("src/assets/menu-bg.jpeg");

        //Establecer icono de la app
        try {
            Image icono = ImageIO.read(getClass().getResource("/assets/icon.png"));
            this.setIconImage(icono);
        } catch(IOException | NullPointerException e) {
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
