package modelo;

public class Puntaje { //Para tabla de puntajes historicos

    private final String jugador;
    private final int puntos;
    private final int tiempo;
    private final String fecha;

    public Puntaje(String jugador, int puntos, int tiempo, String fecha) {
        if (jugador == null || jugador.isBlank() || tiempo < 0 || puntos < 0 || fecha == null) {
            throw new IllegalArgumentException("Jugador, puntos, tiempo y fecha son obligatorios");
        }
        this.jugador = jugador;
        this.puntos = puntos;
        this.tiempo = tiempo;
        this.fecha = fecha;
    }

    public String getJugador() {
        return this.jugador;
    }

    public int getPuntos() {
        return this.puntos;
    }

    public int getTiempo(){
        return this.tiempo;
    }
    public String getFecha() {
        return this.fecha;
    }
}
