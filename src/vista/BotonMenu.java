package vista;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class BotonMenu extends JButton {
    
    private boolean hover;

    private Image imgNormal; 
    private Image imgHover;

    
    public BotonMenu(String rutaImagen, String rutaHover){
        if (rutaImagen != null){
            this.imgNormal = new ImageIcon(rutaImagen).getImage();
        }

        if (rutaHover != null){
            this.imgHover = new ImageIcon(rutaHover).getImage();
        }

        this.hover = false;

        this.setRolloverEnabled(true);
        this.setBorderPainted(false);
        this.setContentAreaFilled(false);
        this.setFocusPainted(false);
        this.setOpaque(false);
        this.setMargin(new Insets(0,0,0,0));
        this.setPreferredSize(new Dimension(400, 60));
        this.setMaximumSize(new Dimension(400, 60));

        this.addMouseListener((MouseListener) new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                hover = true;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                hover = false;
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {

        Image imgActual; 

        if (!hover){
            imgActual = imgNormal;
        } else {
            imgActual = imgHover;
        }
       
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);

        g2.drawImage(imgActual,0,0, getWidth(), getHeight(), this);
        g2.dispose();

        super.paintComponent(g);
    }

}
