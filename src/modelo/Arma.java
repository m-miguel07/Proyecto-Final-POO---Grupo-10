package modelo;

public abstract class Arma {

    //Atributos
    private int danioBase;
    private int alcance;
    private double velocidad;

    //Constructor
    public Arma(int danioBase, int alcance, double velocidad) {
        if (danioBase <= 0) {
            throw new IllegalArgumentException("El danio base debe ser positivo");
        }

        if (alcance <= 0) {
            throw new IllegalArgumentException("El alcance debe ser positivo");
        }

        if (velocidad <= 0) {
            throw new IllegalArgumentException("La velocidad debe ser positiva");
        }

        this.danioBase = danioBase;
        this.alcance = alcance;
        this.velocidad = velocidad;
    }

    //Getters
    public int getDanioBase() {
        return this.danioBase;
    }

    public int getAlcance() {
        return this.alcance;
    }

    public double getVelocidad() {
        return this.velocidad;
    }

    //Comportamientos
    public abstract void aplicarDanio(Entidad objetivo);
}