package modelo;

public class Aro extends ObjetoDelJuego {

    //Atributos
    private final boolean esMeta;
    private boolean encestado;

    //Constructor
    public Aro(double posicionX, double posicionY, int ancho, int alto) {
        this(posicionX, posicionY, ancho, alto, true);
    }

    public Aro(double posicionX, double posicionY, int ancho, int alto, boolean esMeta) {
        super(ancho, alto, posicionX, posicionY);

        this.encestado = false;
        this.esMeta = esMeta;
    }

    //Getters
    public boolean isEncestado() {
        return this.encestado;
    }

    public boolean esMeta() {
        return this.esMeta;
    }

    //Comportamientos
    public void encestar() {
        this.encestado = true;
    }
}
