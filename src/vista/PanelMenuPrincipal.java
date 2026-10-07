package vista;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;


public class PanelMenuPrincipal extends JPanel {

    public static final String ENTER = "ENTER";
    public static final String BOTONES = "BOTONES";

    private CardLayout layoutInferior;
    private Image imagenOrigen;
    private JButton botonNuevoJuego;
    private JButton botonContinuar;
    private JButton botonOpciones;
    private JButton botonSalir;
    private JPanel panelBotones;
    private JPanel panelEnter;
    private JPanel panelInferior;

    public PanelMenuPrincipal(String rutaImagen){
        super(new BorderLayout());
        this.setOpaque(false); //setOpaque() hace que los paneles no tengan fondo, permitiendo que paintComponent() dibuje el fondo sin que lo tape el fondo de los paneles.

        ImageIcon imagen = new ImageIcon(rutaImagen);
        this.imagenOrigen = imagen.getImage();

        //Botones
        this.botonNuevoJuego = new BotonMenu("src/assets/ui/ng-normal.png", "src/assets/ui/ng-hover.png", "src/assets/sfx/boton-hover", "src/assets/sfx/boton-click");
        this.botonContinuar = new BotonMenu("src/assets/ui/co-normal.png", "src/assets/ui/co-hover.png", "src/assets/sfx/boton-hover", "src/assets/sfx/boton-click");
        this.botonOpciones = new BotonMenu("src/assets/ui/opc-normal.png", "src/assets/ui/opc-hover.png", "src/assets/sfx/boton-hover", "src/assets/sfx/boton-click");
        this.botonSalir = new BotonMenu ("src/assets/ui/exit-normal.png", "src/assets/ui/exit-hover.png", "src/assets/sfx/boton-hover", "src/assets/sfx/boton-click");

        //Titulo
        Splash imagenTitulo = new Splash("src/assets/title.png");
        imagenTitulo.setPreferredSize(new Dimension(610,409));
        imagenTitulo.setOpaque(false);

        //Contenedores y paneles
        JPanel contenedorTitulo = new JPanel (new FlowLayout(FlowLayout.CENTER));;
        contenedorTitulo.add(imagenTitulo);
        contenedorTitulo.setOpaque(false);

        this.panelBotones = new JPanel(new GridLayout(0,1,10,20));
        panelBotones.add(botonNuevoJuego);
        panelBotones.add(botonContinuar);
        panelBotones.add(botonOpciones);
        panelBotones.add(botonSalir);
        panelBotones.setOpaque(false);

        JPanel contenedorBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
        contenedorBotones.setBorder(BorderFactory.createEmptyBorder(0,0,110,0));
        contenedorBotones.add(panelBotones);
        contenedorBotones.setOpaque(false);

        Splash imagenEnter = new Splash("src/assets/ui/img-enter.png");
        imagenEnter.setPreferredSize(new Dimension(631,125));
        imagenEnter.setOpaque(false);

        this.panelEnter = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelEnter.setBorder(BorderFactory.createEmptyBorder(225,0,0,0));
        panelEnter.add(imagenEnter);
        panelEnter.setOpaque(false);

        //CardLayout que gestiona la transición de "Presione enter" a los botones del menu.
        this.layoutInferior = new CardLayout();
        this.panelInferior = new JPanel(layoutInferior);
        panelInferior.setOpaque(false);
        panelInferior.add(panelEnter, ENTER);
        panelInferior.add(contenedorBotones, BOTONES);

        this.add(contenedorTitulo,BorderLayout.CENTER);
        this.add(panelInferior, BorderLayout.SOUTH);
    }

    //Getters
    public JButton getBotonNuevoJuego(){
        return this.botonNuevoJuego;
    }

    public JButton getBotonContinuar(){
        return this.botonContinuar;
    }

    public JButton getBotonOpciones(){
        return this.botonOpciones;
    }

    public JButton getBotonSalir(){
        return this.botonSalir;
    }

    public JPanel getPanelEnter(){
        return this.panelEnter;
    }


    public void mostrarPanel(String panel){
        layoutInferior.show(panelInferior,panel);
        panelInferior.revalidate();
        panelInferior.repaint();
    }

    @Override 
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        if(imagenOrigen != null){
            g.drawImage(imagenOrigen, 0, 0,this.getWidth(), this.getHeight(), this);
        }
    }
}
