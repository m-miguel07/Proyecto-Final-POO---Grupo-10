package modelo;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import javafx.application.Platform;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class GestorAudio { //Aplica patron Singleton
	
	private MediaPlayer musicaFondo;
    private static GestorAudio instancia;

    private final Map<String, Clip> sonidos = new HashMap<>();
    private final Map<String, Long> duraciones = new HashMap<>();
	
    private GestorAudio(){
        //FALTA PONER LOS SONIDOS
        this.cargar("boton-enter"); 
        this.cargar("boton-hover");
        this.cargar("boton-click");
    }

    public static GestorAudio getInstancia(){
        if (instancia == null){
            instancia = new GestorAudio();
        }
        return instancia;
    }

    private void cargar(String nombre) { //El SFX debe ser .wav, .mp3 no es compatible
        File archivo = new File("src/assets/sfx/", nombre + ".wav");
        try (AudioInputStream flujo = AudioSystem.getAudioInputStream(archivo)) {
            long milisegundos = (long) (1000 * flujo.getFrameLength() / flujo.getFormat().getFrameRate());
            Clip clip = AudioSystem.getClip();
            clip.open(flujo);
            this.sonidos.put(nombre, clip);
            this.duraciones.put(nombre, milisegundos);
        } catch (Exception e) {
            System.err.println("Fallo al cargar: " + archivo.getAbsolutePath());
            e.printStackTrace();
        }
    }

    public void reproducirEfecto(String nombre) {
        Clip clip = sonidos.get(nombre);
        if (clip != null) {
            clip.setFramePosition(0); // Lo rebobina al inicio
            clip.start();             // Le da play
        } else {
            System.out.println("El efecto '" + nombre + "' no está cargado.");
        }
    }

	public void reproducirMusica(String rutaArchivo, double volumen, boolean enBucle) {
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
                musicaFondo.setVolume(volumen); // Rango de 0.0 (mudo) a 1.0 (máximo)

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
        if (musicaFondo != null) {
            Platform.runLater(() -> musicaFondo.setVolume(volumen));
        }
    }

}
