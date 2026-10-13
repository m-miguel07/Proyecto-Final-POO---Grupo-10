package vista;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;


public class PanelPausa extends PanelBase {
    
    private BotonMenu botonBGM;
    private BotonMenu botonSFX;
    private BotonMenu botonSalida;
    
    public PanelPausa(){
        super("src/assets/ui/exit-normal-pause.png", 
              "src/assets/ui/exit-hover-pause.png", 
              "src/assets/ui/img-pause.png",
              new Dimension(247,45));

        Dimension dimBotones = new Dimension(100,100);

        this.botonBGM = new BotonMenu("src/assets/ui/btn-pause.png", "src/assets/ui/btn-pause.png");
        botonBGM.setPreferredSize(dimBotones);
        botonBGM.setFocusable(false);

        this.botonSFX = new BotonMenu("src/assets/ui/btn-pause.png", "src/assets/ui/btn-pause.png");
        botonSFX.setPreferredSize(dimBotones);
        botonSFX.setFocusable(false);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER,30,0));
        panelBotones.add(botonBGM);
        panelBotones.add(botonSFX);
        panelBotones.setOpaque(false);

        JPanel contenedorBotones = new JPanel(new GridLayout(2,1));
        contenedorBotones.setBorder(BorderFactory.createEmptyBorder(75,0,0,0));
        contenedorBotones.add(panelBotones);
        contenedorBotones.setOpaque(false);

        this.botonSalida = new BotonMenu("src/assets/ui/btn-exit-pause-normal.png", "src/assets/ui/btn-exit-pause-hover.png");
        botonSalida.setFocusable(false);

        JPanel panelSalida = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelSalida.setBorder(BorderFactory.createEmptyBorder(0,0,35,0));
        panelSalida.add(botonSalida);
        panelSalida.setOpaque(false);

        this.add(contenedorBotones, BorderLayout.CENTER);
        this.add(panelSalida, BorderLayout.SOUTH);

    }

    public BotonMenu getBotonSalida(){
        return this.botonSalida;
    }
}
