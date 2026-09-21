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
    public static final String CARD_SPLASH_PRUEBA = "SPLASH_PRUEBA";
    public static final String CARD_MENU_PRINCIPAL = "MENU_PRINCIPAL";
    private final CardLayout cardLayout;
    private final JPanel contenedor;

    public Ventana(){
        this.setTitle("Basketball Fever");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setResizable(false);

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

        contenedor.add(new SplashImagen("src/assets/splash.png"), CARD_SPLASH_MATERIA);
        contenedor.add(new SplashImagen(""), CARD_SPLASH_PRUEBA);
        contenedor.add(new PanelMenuPrincipal(""), CARD_MENU_PRINCIPAL);

        this.add(contenedor);
    }

    public void mostrarTarjeta(String nombre){
        cardLayout.show(contenedor,nombre);
    }

}
