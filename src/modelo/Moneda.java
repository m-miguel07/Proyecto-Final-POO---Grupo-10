package modelo;

public class Moneda extends ObjetoDelJuego {

    //Atributos
    private boolean recolectada;

    //Constructor
    public Moneda(double posicionX, double posicionY, int ancho, int alto) {
        super(ancho, alto, posicionX, posicionY);

        this.recolectada = false;
    }

    //Getters
    public boolean isRecolectada() {
        return this.recolectada;
    }

    //Comportamientos
    public void recolectar() {
        this.recolectada = true;
    }
}