package modelo;

public class EnemigoTerrestre extends Enemigo {

    //Constructor
    public EnemigoTerrestre(int danioBase, int puntosVida, int ancho, int alto, double velocidad, double posicionX, double posicionY) {
        super(danioBase, puntosVida, ancho, alto, velocidad, posicionX, posicionY);
    }
}