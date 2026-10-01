package controlador;

import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.SwingUtilities;

import java.awt.event.KeyEvent;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;

import vista.PanelMenuPrincipal;
import vista.Ventana;

public class ControladorPrincipal {
    private Ventana ventana;
    private final int tiempoSplashMS = 3000;
    private final AtomicBoolean videoTerminado;

    public ControladorPrincipal(){
        this.ventana =  new Ventana();
        this.videoTerminado = new AtomicBoolean(false);

        //Listeners del menu principal

        //Boton de Salir
        this.ventana.getPanelMenu()
                    .getBotonSalir()
                    .addActionListener(e -> System.exit(0));

        //Secuencia de "Presione ENTER" a panel botones
        this.ventana.getPanelMenu().setFocusable(true);
        this.ventana.getPanelMenu().addKeyListener(new KeyAdapter() {
            @Override 
            public void keyPressed(KeyEvent e){
                if (e.getKeyCode() == KeyEvent.VK_ENTER){
                    if (ventana.getPanelMenu().getPanelEnter().isVisible()){
                       ventana.getPanelMenu().mostrarPanel(PanelMenuPrincipal.BOTONES);;
                    }
                }
            }
        });

        this.ventana.getPanelMenu().addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                SwingUtilities.invokeLater(() -> ventana.getPanelMenu().requestFocusInWindow());
            }
        });

        //Inicio de secuencia (Splash -> Video -> Menu)
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
            Ventana.ANCHO_PANTALLA, //Corrige problema de video recortado
            Ventana.ALTO_PANTALLA, 
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