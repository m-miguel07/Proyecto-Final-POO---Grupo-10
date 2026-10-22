package modelo;

public class Plataforma extends ObjetoDelJuego {

    //Atributos
    private MaterialPlataforma material;
    private boolean destruida;
    private boolean bloqueaDesdeAbajo;

    //Constructor
    public Plataforma(double posicionX, double posicionY, int ancho, int alto) {
        this(posicionX, posicionY, ancho, alto, MaterialPlataforma.NORMAL, false);
    }

    public Plataforma(double posicionX, double posicionY, int ancho, int alto, MaterialPlataforma material) {
        this(posicionX, posicionY, ancho, alto, material, material == MaterialPlataforma.NEGRA);
    }

    public Plataforma(double posicionX, double posicionY, int ancho, int alto,
                      MaterialPlataforma material, boolean bloqueaDesdeAbajo) {
        super(ancho, alto, posicionX, posicionY);

        if (material == null) {
            throw new IllegalArgumentException("El material de la plataforma no puede ser nulo");
        }

        this.material = material;
        this.destruida = false;
        this.bloqueaDesdeAbajo = bloqueaDesdeAbajo;
    }

    //Getters
    public boolean isDestruida() {
        return this.destruida;
    }

    //Comportamientos
    public boolean esDestructible() {
        return this.material.esDestructible();
    }

    public boolean esNegra() {
        return this.material == MaterialPlataforma.NEGRA;
    }

    public boolean permitePasarPelota() {
        return this.material.permitePasarPelota();
    }

    public boolean bloqueaDesdeAbajo() {
        return this.bloqueaDesdeAbajo;
    }

    public void destruir() {
        if (this.esDestructible()) {
            this.destruida = true;
        }
    }
}