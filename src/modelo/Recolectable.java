package modelo;

public class Recolectable extends ObjetoDelJuego {

    //Atributos
    private final Arma arma;
    private boolean recolectado;

    //Constructor
    public Recolectable(Arma arma, double posicionX, double posicionY, int ancho, int alto) {
        super(ancho, alto, posicionX, posicionY);

        if (arma == null) {
            throw new IllegalArgumentException("El recolectable debe contener un arma");
        }

        this.arma = arma;
        this.recolectado = false;
    }

    //Getters
    public Arma getArma() {
        return this.arma;
    }

    public boolean isRecolectado() {
        return this.recolectado;
    }

    //Comportamientos
    public void recolectar() {
        this.recolectado = true;
    }

    public boolean esPelotaDeFuego() {
        return this.arma instanceof ArmaFuego;
    }

    public boolean esPelotaDeHielo() {
        return this.arma instanceof ArmaHielo;
    }
}