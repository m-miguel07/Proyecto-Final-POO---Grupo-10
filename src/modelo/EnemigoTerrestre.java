package modelo;

import java.util.List;

public class EnemigoTerrestre extends Enemigo {

    //Atributos
    private int direccion;

    //Constructor
    public EnemigoTerrestre(int puntosVida, int ancho, int alto, double velocidad, double posicionX, double posicionY) {
        super(puntosVida, ancho, alto, velocidad, posicionX, posicionY);

        this.direccion = 1;
    }

    //Comportamientos
    //Invierte el sentido de la patrulla (por ejemplo, al chocar con el muro).
    public void invertirDireccion() {
        this.direccion *= -1;
    }

    public void actualizar(List<Plataforma> plataformas, int anchoPantalla, Muro muro) {
        if (!this.estaCongelado()) {
            this.mover(plataformas, anchoPantalla);
        }

        if (muro != null) {
            muro.bloquearEnemigo(this);
        }
    }

    private void mover(List<Plataforma> plataformas, int anchoPantalla) {
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