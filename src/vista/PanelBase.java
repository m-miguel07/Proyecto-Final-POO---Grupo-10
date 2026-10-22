package vista;

import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;

public abstract class PanelBase extends JPanel{ //Panel base con encabezado y fondo, lo heredan PanelOpciones y PanelPausa

    private final static String BG_DEFAULT = "src/assets/ui/bg-opc.png";
    private final static Dimension DIM_DEFAULT = new Dimension(480,480);
    
    private final static String EXIT_NORMAL_DEFAULT = "src/assets/ui/exit-normal-opc.png";
    private final static String EXIT_HOVER_DEFAULT =  "src/assets/ui/exit-hover-opc.png";


    private Image imagenOrigen;
    private BotonMenu botonSalir;

    //Constructor con parametros de formato default
    public PanelBase(String rutaSalirNormal, String rutaSalirHover, String rutaTitulo, Dimension dimTitulo){

        super(new BorderLayout());

        //Fondo de panel
        ImageIcon imagen = new ImageIcon(BG_DEFAULT);
        this.imagenOrigen = imagen.getImage();
        this.setPreferredSize(DIM_DEFAULT); //Tamaño de la imagen del panel

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

    //Constructor con formato de imagen de fondo y dimension personalizada ; boton de salir opcional (Para PanelFinNivel)
    public PanelBase(String rutaBg, Dimension dimPanel, String rutaTitulo, Dimension dimTitulo, boolean tieneBotonSalir){

        super(new BorderLayout());

        //Fondo de panel
        ImageIcon imagen = new ImageIcon(rutaBg);
        this.imagenOrigen = imagen.getImage();
        this.setPreferredSize(dimPanel);

        //Boton salir (X)
        if (!tieneBotonSalir){
            this.botonSalir = null;
        } else {
            this.botonSalir = new BotonMenu(EXIT_NORMAL_DEFAULT, EXIT_HOVER_DEFAULT);
            botonSalir.setPreferredSize(new Dimension(59,59));
            botonSalir.setFocusable(false);;
        }
        
        //Contenedores

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

        //Encabezado (titulo)
        JPanel contenedorEncabezado = new JPanel(new BorderLayout());
        contenedorEncabezado.setOpaque(false);

        contenedorEncabezado.add(contenedorTitulo, BorderLayout.CENTER);
        
        if (tieneBotonSalir){
            JPanel contenedorBoton = new JPanel(new FlowLayout(FlowLayout.LEFT));
            contenedorBoton.add(botonSalir);
            contenedorBoton.setOpaque(false);
            contenedorBoton.setBorder(BorderFactory.createEmptyBorder(15,15,0,0));
            contenedorEncabezado.add(contenedorBoton, BorderLayout.WEST);
        }
        
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
