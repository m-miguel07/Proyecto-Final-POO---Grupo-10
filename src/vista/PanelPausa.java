package vista;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;


import javax.swing.BorderFactory;
import javax.swing.JPanel;


public class PanelPausa extends PanelBase {
    
    private BotonMenu botonReiniciar;
    private BotonMenu botonSalida;
    
    public PanelPausa(){
        super("src/assets/ui/exit-normal-pause.png", 
              "src/assets/ui/exit-hover-pause.png", 
              "src/assets/ui/img-pause.png",
              new Dimension(247,45));

        this.botonSalida = new BotonMenu("src/assets/ui/btn-exit-pause-normal.png", "src/assets/ui/btn-exit-pause-hover.png");
        botonSalida.setFocusable(false);

        this.botonReiniciar = new BotonMenu("src/assets/ui/btn-reset-pause-normal.png", "src/assets/ui/btn-reset-pause-hover.png");
        botonReiniciar.setFocusable(false);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 0,15));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(75,0,0,0));
        panelBotones.add(botonReiniciar);
        panelBotones.add(botonSalida);
        panelBotones.setOpaque(false);

        this.add(panelBotones, BorderLayout.CENTER);
    }

    public BotonMenu getBotonReiniciar(){
        return this.botonReiniciar;
    }

    public BotonMenu getBotonSalida(){
        return this.botonSalida;
    }
}
