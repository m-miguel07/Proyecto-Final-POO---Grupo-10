package modelo;

public abstract class Recogible extends ObjetoDelJuego {

    //Atributos
    private boolean recolectado;

    //Constructor
    public Recogible(double posicionX, double posicionY, int ancho, int alto) {
        super(ancho, alto, posicionX, posicionY);

        this.recolectado = false;
    }

    //Getters
    public boolean isRecolectado() {
        return this.recolectado;
    }

    //Comportamientos
    public void recolectar() {
        this.recolectado = true;
    }
}
