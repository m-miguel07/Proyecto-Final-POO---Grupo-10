package modelo;

public class Recolectable extends Recogible {

    //Atributos
    private final Arma arma;

    //Constructor
    public Recolectable(Arma arma, double posicionX, double posicionY, int ancho, int alto) {
        super(posicionX, posicionY, ancho, alto);

        if (arma == null) {
            throw new IllegalArgumentException("El recolectable debe contener un arma");
        }

        this.arma = arma;
    }

    //Getters
    public Arma getArma() {
        return this.arma;
    }
}
