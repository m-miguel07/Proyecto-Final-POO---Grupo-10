package vista;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Image;


public class SplashImagen extends JPanel {
    public SplashImagen(){
        this.setLayout(new BorderLayout()); 
    
        ImageIcon imagen = new ImageIcon("src\\assets\\splash.png");

        Image imagenEscalada = imagen.getImage().getScaledInstance(Ventana.ANCHO_PANTALLA, Ventana.ALTO_PANTALLA, Image.SCALE_SMOOTH);
         
        JLabel contenedorImagen = new JLabel(new ImageIcon(imagenEscalada));

        this.add(contenedorImagen, BorderLayout.CENTER);
       
    }
}

