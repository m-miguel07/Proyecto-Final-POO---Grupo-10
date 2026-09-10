package modelo;

public class Aro extends ObjetoDelJuego {

    //Atributos
    private boolean encestado;

    //Constructor
    public Aro(double posicionX, double posicionY, int ancho, int alto) {
        super(ancho, alto, posicionX, posicionY);

        this.encestado = false;
    }

    //Getters
    public boolean isEncestado() {
        return this.encestado;
    }

    //Comportamientos
    public void encestar() {
        this.encestado = true;
    }
}