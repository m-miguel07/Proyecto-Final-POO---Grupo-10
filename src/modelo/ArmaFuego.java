package modelo;

public class ArmaFuego extends Arma {

    //Atributos
    private int danioPorQuemadura;

    //Constructor
    public ArmaFuego(int danioBase, int alcance, double velocidad, int danioPorQuemadura) {
        super(danioBase, alcance, velocidad);

        if (danioPorQuemadura <= 0) {
            throw new IllegalArgumentException("El danio por quemadura debe ser positivo");
        }

        this.danioPorQuemadura = danioPorQuemadura;
    }

    //Getters
    public int getDanioPorQuemadura() {
        return this.danioPorQuemadura;
    }

    //Setters
    public boolean setDanioPorQuemadura(int danioPorQuemadura) {
        if (danioPorQuemadura <= 0) {
            return false;
        }

        this.danioPorQuemadura = danioPorQuemadura;
        return true;
    }

    //Comportamientos
    public void quemar(Entidad objetivo) {
        objetivo.recibirDanio(this.danioPorQuemadura);
    }

    @Override
    public void aplicarDanio(Entidad objetivo) {
        objetivo.recibirDanio(this.getDanioBase());
    }
}