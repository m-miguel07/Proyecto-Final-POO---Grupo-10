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
import javax.swing.JLabel;
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
        this.setOpaque(false);

        ImageIcon imagen = new ImageIcon(rutaImagen);
        this.imagenOrigen = imagen.getImage();

        //Botones
        this.botonNuevoJuego = new JButton("Nuevo juego");
        this.botonContinuar = new JButton("Continuar");
        this.botonOpciones = new JButton ("Opciones");
        this.botonSalir = new JButton ("Salir");

        //Titulo
        JLabel etiquetaTituloSup = new JLabel("BASKETBALL");
        etiquetaTituloSup.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 100));
        JLabel etiquetaTituloInf = new JLabel("FEVER");
        etiquetaTituloInf.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 100));

        JPanel panelTitulo = new JPanel(new GridLayout(0,1,5,5));
        panelTitulo.add(etiquetaTituloSup);
        panelTitulo.add(etiquetaTituloInf);
        panelTitulo.setOpaque(false);

        JPanel contenedorTitulo = new JPanel (new FlowLayout(FlowLayout.LEFT));
        contenedorTitulo.setBorder(BorderFactory.createEmptyBorder(125,100,0,0));
        contenedorTitulo.add(panelTitulo);
        contenedorTitulo.setOpaque(false);

        this.panelBotones = new JPanel(new GridLayout(0,1,10,20));
        panelBotones.add(botonNuevoJuego);
        panelBotones.add(botonContinuar);
        panelBotones.add(botonOpciones);
        panelBotones.add(botonSalir);
        panelBotones.setOpaque(false);
        escalarBotones();

        JPanel contenedorBotones = new JPanel(new FlowLayout(FlowLayout.LEFT));
        contenedorBotones.setBorder(BorderFactory.createEmptyBorder(0,100,100,0));
        contenedorBotones.add(panelBotones);
        contenedorBotones.setOpaque(false);

        this.add(contenedorTitulo,BorderLayout.NORTH);
        this.add(contenedorBotones,BorderLayout.SOUTH);
    }

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
