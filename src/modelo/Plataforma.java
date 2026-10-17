package modelo;

public class Plataforma extends ObjetoDelJuego {

    //Atributos
    private MaterialPlataforma material;
    private boolean destruida;

    //Constructor
    public Plataforma(double posicionX, double posicionY, int ancho, int alto) {
        this(posicionX, posicionY, ancho, alto, MaterialPlataforma.NORMAL);
    }

    public Plataforma(double posicionX, double posicionY, int ancho, int alto, MaterialPlataforma material) {
        super(ancho, alto, posicionX, posicionY);

        if (material == null) {
            throw new IllegalArgumentException("El material de la plataforma no puede ser nulo");
        }

        this.material = material;
        this.destruida = false;
    }

    //Getters
    public boolean isDestruida() {
        return this.destruida;
    }

    //Comportamientos
    public boolean esDestructible() {
        return this.material.esDestructible();
    }

    public void destruir() {
        if (this.esDestructible()) {
            this.destruida = true;
        }
    }
}