package controlador;

import javax.swing.SwingUtilities;

import vista.Ventana;

public class ControladorPrincipal {
    private Ventana ventana;
    private final int tiempoSplashMS = 3500;

    public ControladorPrincipal(){
        this.ventana =  new Ventana();
        this.iniciarSecuencia();
    }

    public void iniciarSecuencia(){
        ventana.mostrarTarjeta(Ventana.CARD_SPLASH_MATERIA);
        ventana.setVisible(true);

        new Thread(() -> {
            try {
                Thread.sleep(tiempoSplashMS);
                SwingUtilities.invokeLater(() -> {
                    ventana.mostrarTarjeta(Ventana.CARD_SPLASH_PRUEBA); //Mostraría el video, aun no implementado 
                });
                
                Thread.sleep(tiempoSplashMS);
                ventana.mostrarTarjeta(Ventana.CARD_MENU_PRINCIPAL);
            } catch(InterruptedException e){
                Thread.currentThread().interrupt();
            }
        }).start();
    }
}