package vista;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JSlider;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;

public class PanelOpciones extends PanelBase {

    private JSlider sliderMusica;
    private JSlider sliderSFX;

    public PanelOpciones(String rutaImagen){
        super("src/assets/ui/exit-normal-opc.png","src/assets/ui/exit-hover-opc.png","src/assets/ui/img-bgm.png", new Dimension(300,40) );

        //SFX
        this.sliderSFX = new JSlider();
        sliderSFX.setValue(10);
        sliderSFX.setUI(new UXSlider(sliderSFX,"src/assets/ui/barra-opc.png","src/assets/icon.png",50,20));

        sliderSFX.setOpaque(false);
        sliderSFX.setMaximumSize(new Dimension(400,250));
        sliderSFX.setAlignmentX(Component.CENTER_ALIGNMENT);

        //BGM
        this.sliderMusica = new JSlider();
        sliderMusica.setValue(10);
        sliderMusica.setUI(new UXSlider(sliderMusica,"src/assets/ui/barra-opc.png","src/assets/icon.png",50,20));

        sliderMusica.setOpaque(false);
        sliderMusica.setMaximumSize(new Dimension(400,250));
        sliderMusica.setAlignmentX(Component.CENTER_ALIGNMENT);

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
        this.add(contenedorSliders, BorderLayout.CENTER);
    }

    public JSlider getSliderMusica(){
        return this.sliderMusica;
    }

    public JSlider getSliderSFX(){
        return this.sliderSFX;
    }
}
