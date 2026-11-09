package modelo;

import java.awt.geom.Rectangle2D;

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

    public Rectangle2D getLimites() {
        return new Rectangle2D.Double(posicionX,posicionY,ancho,alto);
    }

    //Setters
    public void setPosicion(double x, double y){
        this.posicionX = x;
        this.posicionY = y;
    }

    //Comportamientos
    public boolean interseca(ObjetoDelJuego otro) {
        return this.getLimites().intersects(otro.getLimites());
    }
}