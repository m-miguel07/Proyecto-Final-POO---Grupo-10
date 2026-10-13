package vista;

import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;

public class PanelBase extends JPanel{ //Panel base con encabezado y fondo, lo heredan PanelOpciones y PanelPausa
    private Image imagenOrigen;
    private BotonMenu botonSalir;

    public PanelBase(String rutaSalirNormal, String rutaSalirHover, String rutaTitulo, Dimension dimTitulo){

        super(new BorderLayout());

        //Fondo de panel
        ImageIcon imagen = new ImageIcon("src/assets/ui/bg-opc.png");
        this.imagenOrigen = imagen.getImage();
        this.setPreferredSize(new Dimension(480,480)); //Tamaño de la imagen del panel

        //Boton salir (X)
        this.botonSalir = new BotonMenu(rutaSalirNormal, rutaSalirHover);
        botonSalir.setPreferredSize(new Dimension(59,59));
        botonSalir.setFocusable(false);
        
        //Contenedores

        //Boton salir
        JPanel contenedorBoton = new JPanel(new FlowLayout(FlowLayout.LEFT));
        contenedorBoton.add(botonSalir);
        contenedorBoton.setOpaque(false);
        contenedorBoton.setBorder(BorderFactory.createEmptyBorder(15,15,0,0));

        //Titulo panel
        Splash imagenTitulo = new Splash(rutaTitulo);

        imagenTitulo.setPreferredSize(dimTitulo);
        imagenTitulo.setMaximumSize(dimTitulo);
        imagenTitulo.setMinimumSize(dimTitulo);

        imagenTitulo.setOpaque(false);

        JPanel contenedorTitulo = new JPanel(new FlowLayout(FlowLayout.CENTER,0,0));
        contenedorTitulo.add(imagenTitulo, BorderLayout.CENTER);
        contenedorTitulo.setOpaque(false);
        contenedorTitulo.setBorder(BorderFactory.createEmptyBorder(100,0,0,75));

        //Encabezado (Boton + titulo)
        JPanel contenedorEncabezado = new JPanel(new BorderLayout());
        contenedorEncabezado.setOpaque(false);
        contenedorEncabezado.add(contenedorBoton, BorderLayout.WEST);
        contenedorEncabezado.add(contenedorTitulo, BorderLayout.CENTER);
        
        this.add(contenedorEncabezado, BorderLayout.NORTH);
    }

    public BotonMenu getBotonSalir() {
        return botonSalir;
    }

    @Override 
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        if(imagenOrigen != null){
            g.drawImage(imagenOrigen, 0, 0,this.getWidth(), this.getHeight(), this);
        }
    }
}
