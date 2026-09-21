package controlador;

import javax.swing.SwingUtilities;

import vista.Ventana;

public class ControladorSplash {
    private Ventana ventana;
    private final int tiempoMateriaMS = 4000;

    public ControladorSplash(){
        this.ventana =  new Ventana();
        this.iniciarSecuencia();
    }

    public void iniciarSecuencia(){
        ventana.mostrarSplash(Ventana.CARD_SPLASH_MATERIA);
        ventana.setVisible(true);

        new Thread(() -> {
            try {
                Thread.sleep(tiempoMateriaMS);
                SwingUtilities.invokeLater(() -> {
                    ventana.mostrarSplash(Ventana.SPLASH_PRUEBA); //Mostraría el video, aun no implementado
                });
            } catch(InterruptedException e){
                Thread.currentThread().interrupt();
            }
        }).start();
    }
}