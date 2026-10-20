package vista;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JPanel;


public class PanelFinNivel extends PanelBase {
    
    public static final String BG_DEFAULT = "src/assets/ui/bg-fin-lvl.png";
    public static final String EXIT_NORMAL = "src/assets/ui/btn-exit-pause-hover.png";
    public static final String EXIT_HOVER = "src/assets/ui/btn-exit-pause-normal.png";

    private BotonMenu botonSalir;
    private BotonMenu boton; //Reiniciar nivel o Siguiente nivel

    public PanelFinNivel(String rutaTitulo, Dimension dimTitulo, String rutaBotonNormal, String rutaBotonHover){
        super(BG_DEFAULT,new Dimension(627,341), rutaTitulo, dimTitulo);

        this.botonSalir = new BotonMenu(EXIT_NORMAL, EXIT_HOVER);
        botonSalir.setFocusable(false);
        this.boton = new BotonMenu(rutaBotonNormal, rutaBotonHover);
        boton.setFocusable(false);

        JPanel panelBotones = new JPanel (new FlowLayout(FlowLayout.CENTER, 0,10));
        panelBotones.add(botonSalir);
        panelBotones.add(boton);
        
        panelBotones.setOpaque(false);  

        this.add(panelBotones,BorderLayout.CENTER);
    }

    public BotonMenu getBotonSalir(){
        return this.botonSalir;
    }

    public BotonMenu getBoton(){
        return this.boton;
    }

}
