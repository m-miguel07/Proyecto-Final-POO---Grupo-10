package modelo;

public class Partida {

    private int puntajeAcumulado;
    private int nivelActual;
    private long tiempoAcumulado;
    private double volumenSFX;
    private double volumenBGM;

    

    public Partida(int puntaje, int nivel, long tiempo, double volumenSFX, double volumenBGM){
        if (puntaje < 0 || nivel < 1 || tiempo < 0 || volumenSFX < 0 || volumenBGM < 0){
            throw new IllegalArgumentException("El puntaje, nivel, tiempo y valores de volumen son obligatorios.");
        }
        this.puntajeAcumulado = puntaje;
        this.nivelActual = nivel;
        this.tiempoAcumulado = tiempo;
        this.volumenSFX = volumenSFX;
        this.volumenBGM = volumenBGM;
    }

    //Getters
    public int getPuntajeAcumulado() {
        return puntajeAcumulado;
    }

    public int getNivelActual() {
        return nivelActual;
    }

    public long getTiempoAcumulado() {
        return tiempoAcumulado;
    }

    public double getVolumenSFX() {
        return volumenSFX;
    }

    public double getVolumenBGM() {
        return volumenBGM;
    }

    //Setters
    public void setVolumenSFX(double volumenSFX) {
        this.volumenSFX = volumenSFX;
    }

    public void setVolumenBGM(double volumenBGM) {
        this.volumenBGM = volumenBGM;
    }
    
}
