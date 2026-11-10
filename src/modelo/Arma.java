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
        objetivo.recibirDanio(this.getDanioBase(), this.getTipoDanio());
    }

    protected TipoDanio getTipoDanio() {
        return TipoDanio.NORMAL; // Las armas sin elemento no cambian el daño
    }

    //Estandar, Hielo, Fuego (renders para evitar instanceof en la vista)
    public abstract TipoRenderArma getTipoRender();
}
