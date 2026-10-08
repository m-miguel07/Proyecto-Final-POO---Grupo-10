package modelo;

public class Pelota extends ObjetoDelJuego {

    //Atributos
    private final Arma arma;
    private double velocidadX;
    private double velocidadY;
    private boolean activa;

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
    }

    //Getters
    public Arma getArma() {
        return this.arma;
    }

    public double getVelocidadX() {
        return this.velocidadX;
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

    //Setters
    public void setVelocidadX(double velocidadX) {
        this.velocidadX = velocidadX;
    }

    public void setVelocidadY(double velocidadY) {
        this.velocidadY = velocidadY;
    }

    //Comportamientos
    public void mover() {
        this.posicionX += this.velocidadX;
        this.posicionY += this.velocidadY;
    }

    public void desactivar() {
        this.activa = false;
    }

    public boolean estaFueraDePantalla(int anchoPantalla, int altoPantalla) {
        return this.posicionX + this.ancho < 0
                || this.posicionX > anchoPantalla
                || this.posicionY + this.alto < 0
                || this.posicionY > altoPantalla;
    }
}