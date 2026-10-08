package modelo;

public class EnemigoVolador extends Enemigo {

    //Atributos
    private double altura;
    private double amplitud;
    private double posicionYBase;
    private double limiteIzquierdo;
    private double limiteDerecho;
    private int direccion;

    //Constructor
    public EnemigoVolador(double altura, int danioBase, int puntosVida, int ancho, int alto, double velocidad, double posicionX, double posicionY) {
        super(danioBase, puntosVida, ancho, alto, velocidad, posicionX, posicionY);

        if (altura <= 0) {
            throw new IllegalArgumentException("La altura debe ser positiva");
        }

        this.altura = altura;
        this.amplitud = altura / 2.0;
        this.posicionYBase = posicionY;
        this.limiteIzquierdo = posicionX;
        this.limiteDerecho = posicionX;
        this.direccion = 1;
    }

    //Getters
    public double getAltura() {
        return this.altura;
    }

    public double getPosicionYBase() {
        return this.posicionYBase;
    }

    public double getLimiteIzquierdo() {
        return this.limiteIzquierdo;
    }

    public double getLimiteDerecho() {
        return this.limiteDerecho;
    }

    //Setters
    public boolean setAltura(double altura) {
        if (altura <= 0) {
            return false;
        }

        this.altura = altura;
        return true;
    }

    public void setPosicionYBase(double posicionYBase) {
        this.posicionYBase = posicionYBase;
    }

    public void setLimites(double limiteIzquierdo, double limiteDerecho) {
        if (limiteIzquierdo > limiteDerecho) {
            throw new IllegalArgumentException("El limite izquierdo no puede ser mayor al derecho");
        }

        this.limiteIzquierdo = limiteIzquierdo;
        this.limiteDerecho = limiteDerecho;
    }

    //Comportamientos
    public void volar(double tiempoEnSegundos) {
        this.posicionY = this.posicionYBase + (this.amplitud * Math.sin(tiempoEnSegundos * 2.0));
    }

    public void moverHorizontal() {
        double nuevaX = this.posicionX + (this.getVelocidad() * this.direccion);

        if (nuevaX <= this.limiteIzquierdo || nuevaX + this.ancho >= this.limiteDerecho) {
            this.direccion *= -1;
            nuevaX = this.posicionX + (this.getVelocidad() * this.direccion);
        }

        this.posicionX = Math.max(this.limiteIzquierdo, Math.min(nuevaX, this.limiteDerecho - this.ancho));
    }

    public void actualizar(double tiempoEnSegundos) {
        this.volar(tiempoEnSegundos);
        this.moverHorizontal();
    }
}