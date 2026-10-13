package modelo;

import java.io.File;
import java.util.HashMap;
import java.util.Map;


import javafx.application.Platform;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class GestorAudio { //Aplica patron Singleton
	
    public static final String BOTON_INICIO = "BOTON_INICIO";
    public static final String BOTON_HOVER = "BOTON_HOVER";
    public static final String BOTON_CLICK = "BOTON_CLICK";

    private double volumenMusica = 0.5;
	private MediaPlayer musicaFondo;
    private static GestorAudio instancia;

    private final Map<String, AudioClip> sonidos = new HashMap<>();
	
    private GestorAudio(){
        //FALTA PONER LOS SONIDOS
        cargar(BOTON_INICIO, "src/assets/Sonidos/inicio_juego.mp3"); 
        cargar(BOTON_HOVER, "src/assets/Sonidos/menu_hover_boton.mp3");
        cargar(BOTON_CLICK, "src/assets/Sonidos/menu_presionar_boton_2.mp3");
    }

    public static GestorAudio getInstancia(){
        if (instancia == null){
            instancia = new GestorAudio();
        }
        return instancia;
    }

    private void cargar(String nombre, String rutaArchivo) { 
        AudioClip clip = new AudioClip(new File(rutaArchivo).toURI().toString());
        sonidos.put(nombre, clip);
    }

    public void reproducirEfecto(String nombre) {
        AudioClip clip = sonidos.get(nombre);
        if (clip != null) {
            clip.play();
        } else {
            System.out.println("El efecto '" + nombre + "' no está cargado.");
        }
    }

	public void reproducirMusica(String rutaArchivo,boolean enBucle) {
        Platform.runLater(() -> {
            try {
                detenerMusica();

                File archivo = new File(rutaArchivo);
                if (!archivo.exists()) {
                    System.out.println("Audio no encontrado en: " + rutaArchivo);
                    return;
                }

                Media media = new Media(archivo.toURI().toString());
                musicaFondo = new MediaPlayer(media);
                musicaFondo.setVolume(this.volumenMusica); // Rango de 0.0 (mudo) a 1.0 (máximo)

                if (enBucle) {
                    musicaFondo.setCycleCount(MediaPlayer.INDEFINITE);
                }

                musicaFondo.play();
            } catch (Exception e) {
                System.err.println("Error al reproducir audio: " + e.getMessage());
            }
        });
    }

    public void detenerMusica() {
        if (musicaFondo != null) {
            Platform.runLater(() -> {
                try {
                    musicaFondo.stop();
                    musicaFondo.dispose();
                    musicaFondo = null;
                } catch (Exception ignored) {}
            });
        }
    }

    public void pausarMusica() {
        if (musicaFondo != null) {
            Platform.runLater(() -> musicaFondo.pause());
        }
    }

    public void reanudarMusica() {
        if (musicaFondo != null) {
            Platform.runLater(() -> musicaFondo.play());
        }
    }

    public void setVolumen(double volumen) {
        this.volumenMusica = volumen;
        if (musicaFondo != null) {
            Platform.runLater(() -> musicaFondo.setVolume(volumen));
        }
    }

    public double getVolumen(){
        return this.volumenMusica;
    }

}
