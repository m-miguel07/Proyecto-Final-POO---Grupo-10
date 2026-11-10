package modelo;

public class ArmaRecolectable extends Recogible {

    //Atributos
    private final Arma arma;

    //Constructor
    public ArmaRecolectable(Arma arma, double posicionX, double posicionY, int ancho, int alto) {
        super(posicionX, posicionY, ancho, alto);

        if (arma == null) {
            throw new IllegalArgumentException("El arma en el suelo debe contener un arma");
        }

        this.arma = arma;
    }

    //Getters
    public Arma getArma() {
        return this.arma;
    }
}
