package modelo;

public class ArmaFuego extends Arma {

    //Atributos
    private int danioPorQuemadura;

    //Constructor
    public ArmaFuego(int danioBase, double velocidad, int danioPorQuemadura, double posicionX, double posicionY, int ancho, int alto) {
        super(danioBase, velocidad, posicionX, posicionY, ancho, alto);

        if (danioPorQuemadura <= 0) {
            throw new IllegalArgumentException("El danio por quemadura debe ser positivo");
        }

        this.danioPorQuemadura = danioPorQuemadura;
    }

    //Comportamientos
    public void quemar(Entidad objetivo) {
        if (objetivo instanceof Enemigo) {
            ((Enemigo) objetivo).quemar(2000, this.danioPorQuemadura);
        } else {
            objetivo.recibirDanio(this.danioPorQuemadura);
        }
    }

    @Override
    public void aplicarDanio(Entidad objetivo) {
        objetivo.recibirDanio(this.getDanioBase());
        this.quemar(objetivo);
    }
}
