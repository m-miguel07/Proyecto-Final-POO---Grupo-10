package modelo;

import java.io.File;
import java.util.HashMap;
import java.util.Map;


import javafx.application.Platform;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaException;
import javafx.scene.media.MediaPlayer;

public class GestorAudio { //Aplica patron Singleton
	
    public static final String BOTON_INICIO = "BOTON_INICIO";
    public static final String BOTON_HOVER = "BOTON_HOVER";
    public static final String BOTON_CLICK = "BOTON_CLICK";
    public static final String BOTE_PELOTA = "BOTE_PELOTA";
    public static final String PELOTA_LANZAMIENTO = "PELOTA_LANZAMIENTO";
    public static final String ENCESTADO = "ENCESTADO";
    public static final String GOLPE_PELOTA = "GOLPE_PELOTA";
    public static final String MONEDA = "MONEDA";
    public static final String AGARRE_PELOTA = "AGARRE_PELOTA";
    public static final String PERDER_VIDA = "PERDER_VIDA";
    public static final String SALTO = "SALTO";
    public static final String NIVEL_FALLIDO = "NIVEL_FALLIDO";
    public static final String NIVEL_COMPLETO = "NIVEL_COMPLETO";
    public static final String FIN_JUEGO = "FIN_JUEGO";

    private volatile double volumenBGM = 0.10;
    private double volumenSFX = 0.10;

	private MediaPlayer musicaFondo;
    private static GestorAudio instancia;

    private final Map<String, AudioClip> sonidos = new HashMap<>();
	
    private GestorAudio(){
        cargar(BOTON_INICIO, "src/assets/Sonidos/inicio_juego.mp3"); 
        cargar(BOTON_HOVER, "src/assets/Sonidos/menu_hover_boton.mp3");
        cargar(BOTON_CLICK, "src/assets/Sonidos/menu_presionar_boton_2.mp3");
        cargar(BOTE_PELOTA, "src/assets/Sonidos/bote_pelota.mp3");
        cargar(PELOTA_LANZAMIENTO, "src/assets/Sonidos/pelota_lanzamiento.mp3");
        cargar(ENCESTADO, "src/assets/Sonidos/encestado.mp3");
        cargar(GOLPE_PELOTA, "src/assets/Sonidos/golpe_pelota.mp3");
        cargar(MONEDA, "src/assets/Sonidos/moneda.mp3");
        cargar(AGARRE_PELOTA, "src/assets/Sonidos/agarre_pelota.mp3");
        cargar(SALTO, "src/assets/Sonidos/salto.mp3");
        cargar(PERDER_VIDA, "src/assets/Sonidos/perder_vida.mp3");
        cargar(NIVEL_FALLIDO, "src/assets/Sonidos/silbato_terminado.mp3");
        cargar(FIN_JUEGO,"src/assets/Sonidos/fin_juego.mp3");
    }

    public static GestorAudio getInstancia(){
        if (instancia == null){
            instancia = new GestorAudio();
        }
        return instancia;
    }

    private void cargar(String nombre, String rutaArchivo) { 
        File archivo = new File(rutaArchivo);
        if (archivo.exists()) {
            AudioClip clip = new AudioClip(archivo.toURI().toString());
            sonidos.put(nombre, clip);
        } else {
            System.err.println("AudioClip no encontrado: " + rutaArchivo);
        }
    }

    public void reproducirEfecto(String nombre) {
        AudioClip clip = sonidos.get(nombre);
        if (clip != null) {
            clip.setVolume(volumenSFX);
            clip.play();
        } else {
            System.out.println("El efecto '" + nombre + "' no está cargado.");
        }
    }

	public void reproducirMusica(String rutaArchivo,boolean enBucle) {
        Platform.runLater(() -> {
            detenerMusicaAhora();

            File archivo = new File(rutaArchivo);
            if (!archivo.exists()) {
                System.out.println("Audio no encontrado en: " + rutaArchivo);
                return;
            }

            try {
                Media media = new Media(archivo.toURI().toString());
                musicaFondo = new MediaPlayer(media);
                musicaFondo.setVolume(volumenBGM); // Rango de 0.0 (mudo) a 1.0 (máximo)

                if (enBucle) {
                    musicaFondo.setCycleCount(MediaPlayer.INDEFINITE);
                }

                musicaFondo.play();
            } catch (MediaException e) {
                detenerMusicaAhora();
                System.err.println("Error al reproducir audio: " + e.getMessage());
            }
        });
    }

    public void detenerMusica() {
        Platform.runLater(this::detenerMusicaAhora);
    }

    private void detenerMusicaAhora() {
        MediaPlayer reproductor = musicaFondo;
        musicaFondo = null;
        if (reproductor != null) {
            try {
                reproductor.stop();
            } catch (MediaException e) {
                System.err.println("Error al detener audio: " + e.getMessage());
            } finally {
                reproductor.dispose();
            }
        }
    }

    public void pausarMusica() {
        Platform.runLater(() -> {
            if (musicaFondo != null) {
                musicaFondo.pause();
            }
        });
    }

    public void reanudarMusica() {
        Platform.runLater(() -> {
            if (musicaFondo != null) {
                musicaFondo.play();
            }
        });
    }

    public void setVolumenBGM(double volumen) {
        this.volumenBGM = volumen;
        Platform.runLater(() -> {
            if (musicaFondo != null) {
                musicaFondo.setVolume(volumen);
            }
        });
    }

    public void setVolumenSFX(double volumen){
        this.volumenSFX = volumen;
    }

    public double getVolumenBGM(){
        return this.volumenBGM;
    }

    public double getVolumenSFX(){
        return this.volumenSFX;
    }

}
