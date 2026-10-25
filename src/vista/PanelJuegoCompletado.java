package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

public class PanelJuegoCompletado extends PanelBase{

    public static final int ERROR = -1;
    public static final int EXITO = 1;
    public static final int INVALIDO = 0;
    
    private static final String BG_PANEL = "src/assets/ui/bg-end-game.png";
    private static final Font FONT_FIELD = new Font(Font.SANS_SERIF, Font.BOLD, 15);
    private static final Font FONT_LABEL = new Font(Font.SANS_SERIF, Font.BOLD, 45);

    private JLabel etiquetaPuntaje;
    private JLabel etiquetaTiempo;
    private JLabel etiquetaInfo;

    private JTextField campoNombre;
    
    public PanelJuegoCompletado(){
        super(
            BG_PANEL, 
            new Dimension(792,792),
            "src/assets/ui/img-fin.png",
            new Dimension(792,145),
            false
        );

        this.etiquetaPuntaje = new JLabel("PUNTAJE: .... ");
        etiquetaPuntaje.setFont(FONT_LABEL);
        etiquetaPuntaje.setForeground(Color.BLACK);

        this.etiquetaTiempo = new JLabel("TIEMPO TOTAL: .... ");
        etiquetaTiempo.setFont(FONT_LABEL);
        etiquetaTiempo.setForeground(Color.BLACK);

        this.etiquetaInfo = new JLabel();
        etiquetaInfo.setFont(FONT_FIELD);
        etiquetaInfo.setVisible(false);


        JPanel panelStats = new JPanel(new GridLayout(0,1,0,25));
        panelStats.setBorder(BorderFactory.createEmptyBorder(25,100,0,0));
        panelStats.add(etiquetaPuntaje);
        panelStats.add(etiquetaTiempo);
        panelStats.setOpaque(false);

        JPanel contenedorStats = new JPanel(new FlowLayout(FlowLayout.LEFT));
        contenedorStats.add(panelStats);
        contenedorStats.setOpaque(false);

        Splash ingresoNombre = new Splash("src/assets/ui/img-fin-ingreso.png"); 
        ingresoNombre.setPreferredSize(new Dimension(626,36));
        ingresoNombre.setOpaque(false);

        this.campoNombre = new JTextField();
        campoNombre.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 25));
        campoNombre.setForeground(Color.BLACK);

        JPanel panelInfo = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelInfo.add(etiquetaInfo);
        panelInfo.setOpaque(false);

        JPanel panelNombre = new JPanel();
        panelNombre.setLayout(new BoxLayout(panelNombre, BoxLayout.Y_AXIS));
        
        panelNombre.add(ingresoNombre);
        panelNombre.add(Box.createVerticalStrut(10));
        panelNombre.add(campoNombre);
        panelNombre.add(Box.createVerticalStrut(10));
        panelNombre.add(panelInfo);
        panelNombre.setOpaque(false);

        JPanel contenedorNombre = new JPanel(new FlowLayout(FlowLayout.CENTER));
        contenedorNombre.setBorder(BorderFactory.createEmptyBorder(0, 0, 100, 0));
        contenedorNombre.add(panelNombre);
        contenedorNombre.setOpaque(false);

        this.add(contenedorStats, BorderLayout.CENTER);
        this.add(contenedorNombre, BorderLayout.SOUTH);


    }

    public JTextField getCampoNombre(){
        return this.campoNombre;
    }

    public void mostrarStats(int puntaje, long tiempo){
        etiquetaPuntaje.setText("PUNTAJE: " + puntaje);
        etiquetaTiempo.setText("TIEMPO: " + tiempo / 1000);
    }

    public void informar(int estado){
        switch (estado){
            case 1:
                etiquetaInfo.setForeground(new Color(24, 0, 173));
                etiquetaInfo.setText("PUNTAJE GUARDADO CON EXITO");
                break;
            case 0:
                etiquetaInfo.setForeground(Color.RED);
                etiquetaInfo.setText("INGRESE UN NOMBRE DE 1 A 10 CARACTERES");
                break;
            default:
                break;
        }

    etiquetaInfo.setVisible(true);
    Timer timer = new Timer(3000, e-> { //Muestra el mensaje por 3s
        etiquetaInfo.setVisible(false);
    });

    timer.setRepeats(false);
    timer.start();

    }
}
