package modelo;

public abstract class Enemigo extends Entidad {

    //Atributos
    private static final double RANGO_CUERPO_A_CUERPO = 5.0;

    private int danioBase;

    //Constructor
    public Enemigo(int danioBase, int puntosVida, int ancho, int alto, double velocidad, double posicionX, double posicionY) {
        super(puntosVida, ancho, alto, velocidad, posicionX, posicionY);

        if (danioBase <= 0) {
            throw new IllegalArgumentException("El danio base debe ser positivo.");
        }

        this.danioBase = danioBase;
    }

    //Getters
    public int getDanioBase() {
        return this.danioBase;
    }

    //Setters
    public boolean setDanioBase(int danioBase) {
        if (danioBase <= 0) {
            return false;
        }

        this.danioBase = danioBase;
        return true;
    }

    //Comportamientos
    public boolean puedeAtacar(ObjetoDelJuego objetivo) {
        return Colisiones.hayInterseccion(
                this.getPosicionX() - RANGO_CUERPO_A_CUERPO,
                this.getPosicionY() - RANGO_CUERPO_A_CUERPO,
                this.getAncho() + (int) Math.round(2 * RANGO_CUERPO_A_CUERPO),
                this.getAlto() + (int) Math.round(2 * RANGO_CUERPO_A_CUERPO),
                objetivo.getPosicionX(), objetivo.getPosicionY(), objetivo.getAncho(), objetivo.getAlto());
    }

    protected double distanciaA(ObjetoDelJuego objetivo) {
        double centroX = this.getPosicionX() + this.getAncho() / 2.0;
        double centroY = this.getPosicionY() + this.getAlto() / 2.0;
        double centroXObjetivo = objetivo.getPosicionX() + objetivo.getAncho() / 2.0;
        double centroYObjetivo = objetivo.getPosicionY() + objetivo.getAlto() / 2.0;
        return Math.hypot(centroX - centroXObjetivo, centroY - centroYObjetivo);
    }

    @Override
    public void atacar(Entidad objetivo) {
        if (objetivo == null) {
            throw new IllegalArgumentException("El objetivo no puede ser nulo");
        }

        objetivo.recibirDanio(this.getDanioBase());
    }
}