package modelo;

public abstract class Entidad extends ObjetoDelJuego {

    //Atributos
    private int puntosVida;
    private double velocidad;

    //Constructor
    public Entidad(int puntosVida, int ancho, int alto, double velocidad, double posicionX, double posicionY) {
        super(ancho, alto, posicionX, posicionY);

        if (puntosVida < 0) {
            throw new IllegalArgumentException("Los puntos de vida no pueden ser negativos");
        }

        if (velocidad < 0) {
            throw new IllegalArgumentException("La velocidad no puede ser negativa");
        }

        this.puntosVida = puntosVida;
        this.velocidad = velocidad;
    }

    //Getters
    public int getPuntosVida() {
        return this.puntosVida;
    }

    public double getVelocidad() {
        return this.velocidad;
    }

    //Setters
    public boolean setPuntosVida(int puntosVida) {
        if (puntosVida < 0) {
            return false;
        }

        this.puntosVida = puntosVida;
        return true;
    }

    public boolean setVelocidad(double velocidad) {
        if (velocidad < 0) {
            return false;
        }

        this.velocidad = velocidad;
        return true;
    }

    //Comportamientos
    public abstract void atacar(Entidad objetivo);

    public void recibirDanio(int danio) {
        if (danio < 0) {
            throw new IllegalArgumentException("El danio recibido no puede ser negativo");
        }

        this.puntosVida = Math.max(0, this.puntosVida - danio);
    }
}