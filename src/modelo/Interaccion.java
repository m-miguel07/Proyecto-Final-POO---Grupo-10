package modelo;

public abstract class Interaccion extends ObjetoDelJuego {

    //Constructor
    public Interaccion(int ancho, int alto, double posicionX, double posicionY) {
        super(ancho, alto, posicionX, posicionY);
    }
    public abstract void reaccionInteraccion (Pelota pelota);

}
