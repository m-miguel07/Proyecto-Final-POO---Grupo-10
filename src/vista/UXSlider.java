package vista;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JSlider;
import javax.swing.plaf.basic.BasicSliderUI;

public class UXSlider extends BasicSliderUI {

    //UI de sliders personalizado
    
    private final BufferedImage imgBarra;
    private final BufferedImage imgPerilla;
    private final int altoPerilla;
    private final int altoBarra;

    public UXSlider(JSlider slider, String rutaBarra, String rutaPerilla, int altoPerilla, int altoBarra){
        super(slider);
        this.imgBarra = cargarImagen(rutaBarra);
        this.imgPerilla = cargarImagen(rutaPerilla);
        
        this.altoPerilla = altoPerilla;
        this.altoBarra = altoBarra;

    }

    private BufferedImage cargarImagen(String ruta){
        try {
            return ImageIO.read(new File(ruta));
        } catch (IOException e) {
            throw new RuntimeException("No se pudo cargar: " + ruta, e);
        }
    }

    @Override
    protected Dimension getThumbSize() {
        int ancho = imgPerilla.getWidth() * altoPerilla / imgPerilla.getHeight();
        return new Dimension(ancho, altoPerilla);
    }

    @Override
    public void paintTrack(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                            RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);

        // centrada verticalmente respecto a la perilla
        int y = thumbRect.y + thumbRect.height / 2 - altoBarra / 2;

        g2.drawImage(imgBarra, trackRect.x, y, trackRect.width, altoBarra, null);
    }

    @Override
    public void paintThumb(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                            RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);

        g2.drawImage(imgPerilla, thumbRect.x, thumbRect.y,
                     thumbRect.width, thumbRect.height, null);
    }

    @Override
    public void paintFocus(Graphics g) {
        //Sin recuadro de foco
    }
}
