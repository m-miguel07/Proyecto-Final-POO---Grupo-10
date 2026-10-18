package modelo;

public class EnemigoADistancia extends Enemigo {

    //Atributos
    private int alcance;

    //Constructor
    public EnemigoADistancia(int alcance, int puntosVida, int ancho, int alto, double velocidad, double posicionX, double posicionY) {
        super(puntosVida, ancho, alto, velocidad, posicionX, posicionY);

        if (alcance <= 0) {
            throw new IllegalArgumentException("El alcance debe ser positivo");
        }

        this.alcance = alcance;
    }

    //Getters
    public int getAlcance() {
        return this.alcance;
    }

    //Setters
    public boolean setAlcance(int alcance) {
        if (alcance <= 0) {
            return false;
        }

        this.alcance = alcance;
        return true;
    }

    //Comportamientos
    @Override
    public boolean puedeAtacar(ObjetoDelJuego objetivo) {
        return this.distanciaA(objetivo) <= this.alcance;
    }

    private double distanciaA(ObjetoDelJuego objetivo) {
        double centroX = this.getPosicionX() + this.getAncho() / 2.0;
        double centroY = this.getPosicionY() + this.getAlto() / 2.0;
        double centroXObjetivo = objetivo.getPosicionX() + objetivo.getAncho() / 2.0;
        double centroYObjetivo = objetivo.getPosicionY() + objetivo.getAlto() / 2.0;
        return Math.hypot(centroX - centroXObjetivo, centroY - centroYObjetivo);
    }
}
