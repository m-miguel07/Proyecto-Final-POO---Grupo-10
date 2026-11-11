package modelo;

public abstract class Arma {

    //Atributos
    private int danioBase;
    private double velocidad;

    //Constructor
    public Arma(int danioBase, double velocidad) {
        if (danioBase <= 0) {
            throw new IllegalArgumentException("El danio base debe ser positivo");
        }

        if (velocidad <= 0) {
            throw new IllegalArgumentException("La velocidad debe ser positiva");
        }

        this.danioBase = danioBase;
        this.velocidad = velocidad;
    }

    //Getters
    public int getDanioBase() {
        return this.danioBase;
    }

    public double getVelocidad() {
        return this.velocidad;
    }

    //Comportamientos
    public void aplicarDanio(Entidad objetivo) {
        objetivo.recibirDanio(this.getDanioBase(), this.getElemento());
    }

    //Estandar, Hielo, Fuego (tipo de dominio: define el danio y el sprite)
    public abstract TipoElemento getElemento();
}
