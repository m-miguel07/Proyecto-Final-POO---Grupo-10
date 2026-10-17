package modelo;

public abstract class Enemigo extends Entidad {

    //Atributos
    private static final double RANGO_CUERPO_A_CUERPO = 5.0;

    private int danioBase;
    private int tiempoCongeladoMS;
    private int tiempoQuemadoMS;
    private int danioPorQuemaduraTick;
    private int tiempoSiguienteTickQuemaduraMS;

    //Constructor
    public Enemigo(int danioBase, int puntosVida, int ancho, int alto, double velocidad, double posicionX, double posicionY) {
        super(puntosVida, ancho, alto, velocidad, posicionX, posicionY);

        if (danioBase <= 0) {
            throw new IllegalArgumentException("El danio base debe ser positivo.");
        }

        this.danioBase = danioBase;
        this.tiempoCongeladoMS = 0;
        this.tiempoQuemadoMS = 0;
        this.danioPorQuemaduraTick = 0;
        this.tiempoSiguienteTickQuemaduraMS = 0;
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

    public void congelar(int duracionMS) {
        this.tiempoCongeladoMS = Math.min(5000, Math.max(this.tiempoCongeladoMS, duracionMS));
    }

    public boolean estaCongelado() {
        return this.tiempoCongeladoMS > 0;
    }

    public void quemar(int duracionMS, int danioPorTick) {
        this.tiempoQuemadoMS = duracionMS;
        this.danioPorQuemaduraTick = danioPorTick;
        this.tiempoSiguienteTickQuemaduraMS = 500;
    }

    public boolean estaQuemado() {
        return this.tiempoQuemadoMS > 0;
    }

    public void actualizarEstados(int deltaMS) {
        if (this.tiempoCongeladoMS > 0) {
            this.tiempoCongeladoMS = Math.max(0, this.tiempoCongeladoMS - deltaMS);
        }

        if (this.tiempoQuemadoMS > 0) {
            this.tiempoQuemadoMS = Math.max(0, this.tiempoQuemadoMS - deltaMS);
            this.tiempoSiguienteTickQuemaduraMS -= deltaMS;
            if (this.tiempoSiguienteTickQuemaduraMS <= 0) {
                this.recibirDanio(this.danioPorQuemaduraTick);
                this.tiempoSiguienteTickQuemaduraMS = 500;
            }
        }
    }
}