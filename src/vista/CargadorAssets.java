package vista;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import javax.imageio.ImageIO;

public final class CargadorAssets {

    private static final AtomicInteger CONTADOR_HILOS = new AtomicInteger(1);

    private static final ExecutorService EJECUTOR = Executors.newFixedThreadPool(2, tarea -> {
        Thread hilo = new Thread(tarea, "carga-assets-" + CONTADOR_HILOS.getAndIncrement());
        hilo.setDaemon(true);
        return hilo;
    });

    private CargadorAssets() {
    }

    public static CompletableFuture<BufferedImage> cargarImagen(String ruta) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                BufferedImage imagen = ImageIO.read(new File(ruta));

                if (imagen == null) {
                    throw new IOException("Formato de imagen no soportado: " + ruta);
                }

                return imagen;
            } catch (IOException e) {
                throw new CompletionException(e);
            }
        }, EJECUTOR);
    }

    public static CompletableFuture<BufferedImage> escalar(BufferedImage original, int ancho, int alto) {
        return CompletableFuture.supplyAsync(() -> {
            BufferedImage escalada = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2 = escalada.createGraphics();

            try {
                g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                        RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                g2.drawImage(original, 0, 0, ancho, alto, null);
            } finally {
                g2.dispose();
            }

            return escalada;
        }, EJECUTOR);
    }
}
