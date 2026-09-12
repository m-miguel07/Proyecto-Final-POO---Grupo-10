package vista;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

//EN PROGRESO

public class Ventana extends JFrame {
    
    public static final int ANCHO_PANTALLA = 1366;
    public static final int ALTO_PANTALLA = 768;

    public static final String CARD_SPLASH_MATERIA = "SPLASH_MATERIA";

    private final CardLayout cardLayout;
    private final JPanel contenedor;

    public Ventana(){
        this.setTitle("Basketball Fever");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setResizable(false);

        cardLayout = new CardLayout();
        contenedor = new JPanel(cardLayout);

        contenedor.add(new SplashImagen(), CARD_SPLASH_MATERIA);

        this.add(contenedor);
        this.pack();
        this.setLocationRelativeTo(null);
    }

    public void mostrarSplash(String nombre){
        cardLayout.show(contenedor,nombre);
        contenedor.revalidate();
        contenedor.repaint();   
    }

}
