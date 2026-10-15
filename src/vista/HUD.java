package vista;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.ImageIcon;

public class HUD {

    //Puntos de salud
    private Image imgPSLleno;
    private Image imgPSVacio;
    private Image imgFondoPS;

    //Monedas
    private Image imgFondoCoins;

    //Contador de tiempo
    private Image imgFondoTimer;

    //Puntaje
    private Image imgFondoScore;
 
    public HUD(String rutaPSLleno, String rutaPSVacio, String rutaFondoPS, String rutaFondoCoins, String rutaFondoTimer, String rutaFondoScore){

        this.imgPSLleno = new ImageIcon(rutaPSLleno).getImage();
        this.imgPSVacio = new ImageIcon(rutaPSVacio).getImage();
        this.imgFondoPS = new ImageIcon(rutaFondoPS).getImage();

        this.imgFondoCoins = new ImageIcon(rutaFondoCoins).getImage();
        this.imgFondoTimer = new ImageIcon(rutaFondoTimer).getImage();
        this.imgFondoScore = new ImageIcon(rutaFondoScore).getImage();

    }

    public void dibujar(Graphics2D g2, int vidasPersonaje, int puntaje, long tiempoTranscurrido, int monedasRecogidas, int monedasTotales ){
        g2.drawImage(imgFondoPS, 30, 15, null);
        for (int i = 0; i < 3; i++) {
            Image imgActual = (i < vidasPersonaje ? imgPSLleno : imgPSVacio);
            g2.drawImage(imgActual, 55+i*65, 35,null);
        }

        g2.drawImage(imgFondoCoins,300,15,null);
        g2.drawImage(imgFondoTimer, 650, 15,null);
        g2.drawImage(imgFondoScore, 1270,15,null);

        g2.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 40));
        g2.setColor(new Color(0, 0, 0));

        if (puntaje == 0) {
            g2.drawString(String.valueOf(puntaje), 1380, 90);
        } else {
            g2.drawString(String.valueOf(puntaje), 1355, 90);
        }
       
        g2.drawString(monedasRecogidas + " / " + monedasTotales, 370, 75);

        if (tiempoTranscurrido < 10) {
            g2.drawString(tiempoTranscurrido + "s", 725, 100);
        } else if(tiempoTranscurrido < 100){
            g2.drawString(tiempoTranscurrido + "s", 720, 100);
        } else {
             g2.drawString(tiempoTranscurrido + "s", 710, 100);
        }
        
        g2.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 25));
        g2.drawString("A/D mover  -  ESPACIO saltar  -  J disparar", 36, 135);

    }
}
