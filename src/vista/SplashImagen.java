package vista;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import java.awt.Graphics;
import java.awt.Image;

//Se modificó la implementación usando awt.Graphics, reemplazando la solución anterior con imagenEscalada.
public class SplashImagen extends JPanel {
    private Image imagenOrigen;

    public SplashImagen(String rutaImagen){
        ImageIcon imagen = new ImageIcon(rutaImagen);
        this.imagenOrigen = imagen.getImage();
    }

    @Override 
    protected void paintComponent(Graphics g){
        super.paintComponent(g);

        if(imagenOrigen != null){
            g.drawImage(imagenOrigen, 0, 0,this.getWidth(), this.getHeight(), this);
        }
    }
}

