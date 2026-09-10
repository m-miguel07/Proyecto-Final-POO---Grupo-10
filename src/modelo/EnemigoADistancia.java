package modelo;

public class EnemigoADistancia extends Enemigo {

    //Atributos
    private int alcance;

    //Constructor
    public EnemigoADistancia(int alcance, int danioBase, int puntosVida, int ancho, int alto, double velocidad, double posicionX, double posicionY) {
        super(danioBase, puntosVida, ancho, alto, velocidad, posicionX, posicionY);

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
        return this.distanciaA(objetivo) <= this.getAlcance();
    }
}