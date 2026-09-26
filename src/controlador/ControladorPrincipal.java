package controlador;

import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.SwingUtilities;

import vista.Ventana;

public class ControladorPrincipal {
    private Ventana ventana;
    private final int tiempoSplashMS = 3000;
    private final AtomicBoolean videoTerminado;

    public ControladorPrincipal(){
        this.ventana =  new Ventana();
        this.videoTerminado = new AtomicBoolean(false);
        this.iniciarSecuencia();
    }

    public void iniciarSecuencia(){
        ventana.mostrarTarjeta(Ventana.CARD_SPLASH_MATERIA);
        ventana.setVisible(true);

        new Thread(() -> {
            try {
                Thread.sleep(tiempoSplashMS);
                SwingUtilities.invokeLater(this::iniciarVideoIntro);
                
            } catch(InterruptedException e){
                Thread.currentThread().interrupt();
            }
        }).start();
    }

    private void iniciarVideoIntro(){
        ventana.mostrarTarjeta(Ventana.CARD_VIDEO);
        ventana.getPanelVideo().cargarYReproducir(
            "src/assets/intro.mp4",
            Ventana.ANCHO_PANTALLA - 300, //Corrige problema de video recortado
            Ventana.ALTO_PANTALLA - 200, 
            this::finalizarVideoYMostrarMenu, 
            this::finalizarVideoYMostrarMenu
        );
    }

    private void finalizarVideoYMostrarMenu() {
        if (videoTerminado.compareAndSet(false, true)){
            ventana.getPanelVideo().detenerYLiberar();
        }

        SwingUtilities.invokeLater(() -> {
            ventana.mostrarTarjeta(Ventana.CARD_MENU_PRINCIPAL);
        });
    }
}