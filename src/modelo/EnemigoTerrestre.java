package modelo;

import java.util.List;

public class EnemigoTerrestre extends Enemigo {

    //Atributos
    private int direccion;

    //Constructor
    public EnemigoTerrestre(int danioBase, int puntosVida, int ancho, int alto, double velocidad, double posicionX, double posicionY) {
        super(danioBase, puntosVida, ancho, alto, velocidad, posicionX, posicionY);

        this.direccion = 1;
    }

    //Getters
    public int getDireccion() {
        return this.direccion;
    }

    //Setters
    public void invertirDireccion() {
        this.direccion *= -1;
    }

    //Comportamientos
    public void actualizar(List<Plataforma> plataformas, int anchoPantalla) {
        double nuevaX = this.posicionX + (this.getVelocidad() * this.direccion);

        if (nuevaX < 0 || nuevaX + this.ancho > anchoPantalla) {
            this.direccion *= -1;
            return;
        }

        for (Plataforma plataforma : plataformas) {
            if (plataforma.isDestruida()) {
                continue;
            }

            boolean apoyado = this.estaApoyadoEn(plataforma);
            if (!apoyado) {
                continue;
            }

            boolean cuerpoEnteroEnPlataforma = nuevaX >= plataforma.getPosicionX()
                    && nuevaX + this.ancho <= plataforma.getPosicionX() + plataforma.getAncho();

            if (cuerpoEnteroEnPlataforma) {
                this.posicionX = nuevaX;
                return;
            }
        }

        this.direccion *= -1;
    }

    private boolean estaApoyadoEn(Plataforma plataforma) {
        double pie = this.posicionY + this.alto;
        return Math.abs(pie - plataforma.getPosicionY()) <= 6.0
                && this.posicionX + this.ancho > plataforma.getPosicionX()
                && this.posicionX < plataforma.getPosicionX() + plataforma.getAncho();
    }
}