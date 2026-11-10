package modelo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public final class GestorGuardado {

    //Un solo hilo daemon para el guardado en segundo plano
    private static final AtomicInteger CONTADOR = new AtomicInteger(1);
    
    //Lambda para implementar la creación del hilo (en este caso es uno solo)
    private static final ExecutorService EJECUTOR = Executors.newSingleThreadExecutor(tarea -> {
        Thread hilo = new Thread(tarea, "guardado-bg-1" + CONTADOR.getAndIncrement());
        hilo.setDaemon(true);
        return hilo;
    });

    private GestorGuardado() {
    }

    public static void guardarPartida(Partida partida, PartidaDAO partidaDAO) {
    
        if (partida == null || partidaDAO == null) {
            return;
        }
        //implementación lambda, llama a partidaDAOSQLite 
        EJECUTOR.execute(() -> {
            try {
                partidaDAO.guardar(partida);
            } catch (Exception e) {
                System.err.println("Error al guardar partida en segundo plano: " + e.getMessage());
            }
        });
    }

    //Idem que el anterior
    public static void guardarPuntaje(Puntaje puntaje, PuntajeDAO puntajeDAO) {
        if (puntaje == null || puntajeDAO == null) {
            return;
        }
        EJECUTOR.execute(() -> {
            try {
                puntajeDAO.guardar(puntaje);
            } catch (Exception e) {
                System.err.println("Error al guardar puntaje en segundo plano: " + e.getMessage());
            }
        });
    }
}
