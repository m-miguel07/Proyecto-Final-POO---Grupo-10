package modelo;

public abstract class ObjetoDelJuego {

    //Atributos
    protected final int ancho;
    protected final int alto;
    protected double posicionX;
    protected double posicionY;

    //Constructor
    public ObjetoDelJuego(int ancho, int alto, double posicionX, double posicionY) {
        if (ancho <= 0 || alto <= 0) {
            throw new IllegalArgumentException("El ancho y el alto deben ser positivos");
        }

        this.ancho = ancho;
        this.alto = alto;
        this.posicionX = posicionX;
        this.posicionY = posicionY;
    }

    //Getters
    public int getAncho() {
        return this.ancho;
    }

    public int getAlto() {
        return this.alto;
    }

    public double getPosicionX() {
        return this.posicionX;
    }

    public double getPosicionY() {
        return this.posicionY;
    }

    //Setters
    public void setPosicionX(double posicionX) {
        this.posicionX = posicionX;
    }

    public void setPosicionY(double posicionY) {
        this.posicionY = posicionY;
    }

    //Comportamientos
    public boolean interseca(ObjetoDelJuego otro) {
        return Colisiones.hayInterseccion(
                this.posicionX, this.posicionY, this.ancho, this.alto,
                otro.posicionX, otro.posicionY, otro.ancho, otro.alto);
    }
}