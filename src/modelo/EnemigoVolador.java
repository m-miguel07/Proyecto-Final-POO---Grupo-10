package modelo;

public class EnemigoVolador extends Enemigo {

    //Atributos
    private double altura;

    //Constructor
    public EnemigoVolador(double altura, int danioBase, int puntosVida, int ancho, int alto, double velocidad, double posicionX, double posicionY) {
        super(danioBase, puntosVida, ancho, alto, velocidad, posicionX, posicionY);

        if (altura <= 0) {
            throw new IllegalArgumentException("La altura debe ser positiva");
        }

        this.altura = altura;
    }

    //Getters
    public double getAltura() {
        return this.altura;
    }

    //Setters
    public boolean setAltura(double altura) {
        if (altura <= 0) {
            return false;
        }

        this.altura = altura;
        return true;
    }

    //Comportamientos
    public void volar() {
    }

}