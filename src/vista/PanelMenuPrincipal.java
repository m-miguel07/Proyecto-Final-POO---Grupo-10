package vista;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class PanelMenuPrincipal extends JPanel {
    private Image imagenOrigen;
    private JButton botonNuevoJuego;
    private JButton botonContinuar;
    private JButton botonOpciones;
    private JButton botonSalir;
    private JPanel  panelBotones;

    public PanelMenuPrincipal(String rutaImagen){
        super(new BorderLayout());
        this.setOpaque(false); //setOpaque() hace que los paneles no tengan fondo, permitiendo que paintComponent() dibuje el fondo sin que lo tape el fondo de los paneles.

        ImageIcon imagen = new ImageIcon(rutaImagen);
        this.imagenOrigen = imagen.getImage();

        //Botones
        this.botonNuevoJuego = new JButton("Nuevo juego");
        this.botonContinuar = new JButton("Continuar");
        this.botonOpciones = new JButton ("Opciones");
        this.botonSalir = new JButton ("Salir");

        //Titulo
        Splash imagenTitulo = new Splash("src/assets/title.png");
        imagenTitulo.setPreferredSize(new Dimension(670,450));
        imagenTitulo.setOpaque(false);

        //Contenedores y paneles
        JPanel contenedorTitulo = new JPanel (new FlowLayout(FlowLayout.CENTER));
        contenedorTitulo.setBorder(BorderFactory.createEmptyBorder(25,0,0,0));
        contenedorTitulo.add(imagenTitulo);
        contenedorTitulo.setOpaque(false);

        this.panelBotones = new JPanel(new GridLayout(0,1,10,20));
        panelBotones.add(botonNuevoJuego);
        panelBotones.add(botonContinuar);
        panelBotones.add(botonOpciones);
        panelBotones.add(botonSalir);
        panelBotones.setOpaque(false);
        escalarBotones();

        JPanel contenedorBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
        contenedorBotones.setBorder(BorderFactory.createEmptyBorder(0,0,100,0));
        contenedorBotones.add(panelBotones);
        contenedorBotones.setOpaque(false);

        //Listeners
        botonSalir.addActionListener(e -> System.exit(0));


        this.add(contenedorTitulo,BorderLayout.CENTER);
        this.add(contenedorBotones,BorderLayout.SOUTH);
    }

    //Getters
    public JButton getBotonNuevoJuego(){
        return this.botonNuevoJuego;
    }

    public JButton getBotonContinuar(){
        return this.getBotonNuevoJuego();
    }

    public JButton getBotonOpciones(){
        return this.getBotonOpciones();
    }

    public JButton getBotonSalir(){
        return this.getBotonSalir();
    }

    //Metodos
    private void escalarBotones(){
        for (Component c : panelBotones.getComponents()){
            if (c instanceof JButton){
                c.setPreferredSize(new Dimension(200,50));
                c.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 25));
            }
        }
    }

    @Override 
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        if(imagenOrigen != null){
            g.drawImage(imagenOrigen, 0, 0,this.getWidth(), this.getHeight(), this);
        }
    }
}
