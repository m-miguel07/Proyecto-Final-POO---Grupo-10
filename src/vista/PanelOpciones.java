package vista;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSlider;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;

import java.awt.Image;

public class PanelOpciones extends JPanel {
    private Image imagenOrigen;
    private BotonMenu botonSalir;
    private JSlider sliderMusica;
    private JSlider sliderSFX;

    public PanelOpciones(String rutaImagen){
        super(new BorderLayout());

        //Fondo de panel
        ImageIcon imagen = new ImageIcon(rutaImagen);
        this.imagenOrigen = imagen.getImage();
        this.setPreferredSize(new Dimension(480,480 )); //Tamaño de la imagen del panel

        //Boton salir (X)
        this.botonSalir = new BotonMenu("src/assets/ui/exit-normal-opc.png","src/assets/ui/exit-hover-opc.png","","");
        botonSalir.setPreferredSize(new Dimension(59,59));

        //Contenedores

        //Boton salir
        JPanel contenedorBoton = new JPanel(new FlowLayout(FlowLayout.LEFT));
        contenedorBoton.add(botonSalir);
        contenedorBoton.setOpaque(false);
        contenedorBoton.setBorder(BorderFactory.createEmptyBorder(15,15,0,0));

        //Titulo panel
        Splash imagenTitulo = new Splash("src/assets/ui/img-opc.png");

        Dimension dimTitulo = new Dimension(300,40);
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
    
        //SFX
        this.sliderSFX = new JSlider();
        sliderSFX.setUI(new UXSlider(sliderSFX,"src/assets/ui/barra-opc.png","src/assets/icon.png",50,20));

        sliderSFX.setOpaque(false);
        sliderSFX.setMaximumSize(new Dimension(400,250));
        sliderSFX.setAlignmentX(Component.CENTER_ALIGNMENT);

        //BGM
        this.sliderMusica = new JSlider();
        sliderMusica.setUI(new UXSlider(sliderMusica,"src/assets/ui/barra-opc.png","src/assets/icon.png",50,20));

        sliderMusica.setOpaque(false);
        sliderMusica.setMaximumSize(new Dimension(400,250));
        sliderSFX.setAlignmentX(Component.CENTER_ALIGNMENT);

        //Sliders (SFX y BGM)
        JPanel contenedorSliders = new JPanel ();
        contenedorSliders.setLayout(new BoxLayout(contenedorSliders, BoxLayout.Y_AXIS));

        Dimension dimBGM = new Dimension(182,30);
        Dimension dimSFX = new Dimension(90,30);

        Splash tituloBGM = new Splash("src/assets/ui/img-bgm.png");
        tituloBGM.setPreferredSize(dimBGM);
        tituloBGM.setMaximumSize(dimBGM);
        tituloBGM.setMinimumSize(dimBGM);

        tituloBGM.setAlignmentX(Component.CENTER_ALIGNMENT);
        tituloBGM.setOpaque(false);

        Splash tituloSFX = new Splash("src/assets/ui/img-sfx.png");
        tituloSFX.setPreferredSize(dimSFX);
        tituloSFX.setMaximumSize(dimSFX);
        tituloSFX.setMinimumSize(dimSFX);

        tituloSFX.setAlignmentX(Component.CENTER_ALIGNMENT);
        tituloSFX.setOpaque(false);

        contenedorSliders.add(Box.createVerticalGlue());
        contenedorSliders.add(tituloBGM);

        contenedorSliders.add(Box.createVerticalStrut(15));
        contenedorSliders.add(sliderMusica);

        contenedorSliders.add(Box.createRigidArea(new Dimension(0,50))); //Espacio vertical entre sliders

        contenedorSliders.add(tituloSFX);
        contenedorSliders.add(Box.createVerticalStrut(15));
        contenedorSliders.add(sliderSFX);
        contenedorSliders.add(Box.createVerticalGlue());

        contenedorSliders.setOpaque(false);
        
        this.add(contenedorEncabezado, BorderLayout.NORTH);
        this.add(contenedorSliders, BorderLayout.CENTER);

    }

    public JButton getBotonSalir() {
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
