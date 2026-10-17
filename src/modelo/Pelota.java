package modelo;

public class Pelota extends ObjetoDelJuego {

    //Constantes físicas
    public static final double GRAVEDAD = 0.55;
    public static final double VELOCIDAD_MAX_Y = 16.0;
    public static final double RESTITUCION = 0.72;
    public static final double FRICCION_SUELO = 0.95;
    public static final double UMBRAL_VELOCIDAD_MINIMA = 0.6;
    public static final int TIEMPO_MAX_DETENIDA_MS = 1000;

    //Atributos
    private final Arma arma;
    private double velocidadX;
    private double velocidadY;
    private boolean activa;
    private int tiempoDetenidaMS;

    //Constructor
    public Pelota(Arma arma, double posicionX, double posicionY, int ancho, int alto,
                  double velocidadX, double velocidadY) {
        super(ancho, alto, posicionX, posicionY);

        if (arma == null) {
            throw new IllegalArgumentException("La pelota debe tener un arma asociada");
        }

        this.arma = arma;
        this.velocidadX = velocidadX;
        this.velocidadY = velocidadY;
        this.activa = true;
        this.tiempoDetenidaMS = 0;
    }

    //Getters
    public Arma getArma() {
        return this.arma;
    }

    public double getVelocidadY() {
        return this.velocidadY;
    }

    public boolean isActiva() {
        return this.activa;
    }

    public boolean esDeFuego() {
        return this.arma instanceof ArmaFuego;
    }

    public boolean esDeHielo() {
        return this.arma instanceof ArmaHielo;
    }

    //Comportamientos
    public void aplicarGravedad() {
        this.velocidadY += GRAVEDAD;
        if (this.velocidadY > VELOCIDAD_MAX_Y) {
            this.velocidadY = VELOCIDAD_MAX_Y;
        }
    }

    public void mover() {
        this.posicionX += this.velocidadX;
        this.posicionY += this.velocidadY;
    }

    public boolean rebotarVertical(double nuevoY, double factorRestitucion) {
        this.posicionY = nuevoY;
        boolean reboteFuerte = Math.abs(this.velocidadY) > 2.0;

        this.velocidadY = -this.velocidadY * factorRestitucion;
        this.velocidadX *= FRICCION_SUELO;

        if (Math.abs(this.velocidadY) < UMBRAL_VELOCIDAD_MINIMA) {
            this.velocidadY = 0.0;
        }
        if (Math.abs(this.velocidadX) < UMBRAL_VELOCIDAD_MINIMA) {
            this.velocidadX = 0.0;
        }

        return reboteFuerte;
    }

    public boolean rebotarHorizontal(double nuevoX, double factorRestitucion) {
        this.posicionX = nuevoX;
        boolean reboteFuerte = Math.abs(this.velocidadX) > 2.0;

        this.velocidadX = -this.velocidadX * factorRestitucion;

        if (Math.abs(this.velocidadX) < UMBRAL_VELOCIDAD_MINIMA) {
            this.velocidadX = 0.0;
        }

        return reboteFuerte;
    }

    public boolean estaDetenida() {
        return Math.abs(this.velocidadX) < UMBRAL_VELOCIDAD_MINIMA && Math.abs(this.velocidadY) <= GRAVEDAD;
    }

    public void actualizarInactividad(int deltaMS) {
        if (this.estaDetenida()) {
            this.tiempoDetenidaMS += deltaMS;
            if (this.tiempoDetenidaMS >= TIEMPO_MAX_DETENIDA_MS) {
                this.desactivar();
            }
        } else {
            this.tiempoDetenidaMS = 0;
        }
    }

    public void desactivar() {
        this.activa = false;
    }

    public boolean estaFueraDePantalla(int anchoPantalla, int altoPantalla) {
        return this.posicionX + this.ancho < 0
                || this.posicionX > anchoPantalla
                || this.posicionY > altoPantalla;
    }
}