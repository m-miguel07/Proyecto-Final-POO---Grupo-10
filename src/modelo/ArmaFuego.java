package modelo;

public class ArmaFuego extends Arma {

    //Atributos
    private int danioPorQuemadura;

    //Constructor
    public ArmaFuego(int danioBase, double velocidad, int danioPorQuemadura) {
        super(danioBase, velocidad);

        if (danioPorQuemadura <= 0) {
            throw new IllegalArgumentException("El danio por quemadura debe ser positivo");
        }

        this.danioPorQuemadura = danioPorQuemadura;
    }

    //Comportamientos
    @Override
    public void aplicarDanio(Entidad objetivo) {
        super.aplicarDanio(objetivo);
        objetivo.recibirQuemadura(2000, this.danioPorQuemadura);
    }

    @Override
    public TipoElemento getElemento() {
        return TipoElemento.FUEGO;
    }
}
