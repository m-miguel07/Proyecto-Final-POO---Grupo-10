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
    public MaterialPlataforma getMaterial() {
        return this.material;
    }

    public boolean isDestruida() {
        return this.destruida;
    }

    //Setters
    public boolean setMaterial(MaterialPlataforma material) {
        if (material == null) {
            return false;
        }

        this.material = material;
        return true;
    }

    //Comportamientos
    public boolean esDestructible() {
        return this.material.esDestructible();
    }

    public boolean puedeSerDestruidaPor(MaterialPlataforma materialAtaque) {
        return !this.destruida && this.esDestructible() && materialAtaque == MaterialPlataforma.MADERA;
    }

    public void destruir() {
        if (this.esDestructible()) {
            this.destruida = true;
        }
    }
}