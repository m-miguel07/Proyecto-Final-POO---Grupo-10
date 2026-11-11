package modelo;

import java.util.List;

public class EnemigoVolador extends Enemigo {

    //Atributos
    private double limiteIzquierdo;
    private double limiteDerecho;
    private int direccion;

    //Constructor
    public EnemigoVolador(int puntosVida, int ancho, int alto, double velocidad, double posicionX, double posicionY) {
        super(puntosVida, ancho, alto, velocidad, posicionX, posicionY);

        this.limiteIzquierdo = posicionX;
        this.limiteDerecho = posicionX;
        this.direccion = 1;
    }

    //Setters
    public void setLimites(double limiteIzquierdo, double limiteDerecho) {
        if (limiteIzquierdo > limiteDerecho) {
            throw new IllegalArgumentException("El limite izquierdo no puede ser mayor al derecho");
        }

        this.limiteIzquierdo = limiteIzquierdo;
        this.limiteDerecho = limiteDerecho;
    }

    @Override
    public TipoEnemigo getTipo() {
        return TipoEnemigo.VOLADOR;
    }

    @Override
    public boolean esInmuneA(TipoElemento tipo) {
        return tipo == TipoElemento.HIELO; // es inmune al daño de hielo
    }

    //Comportamientos
    public void moverHorizontal() {
        double nuevaX = this.posicionX + (this.getVelocidad() * this.direccion);

        if (nuevaX <= this.limiteIzquierdo || nuevaX + this.ancho >= this.limiteDerecho) {
            this.direccion *= -1;
            nuevaX = this.posicionX + (this.getVelocidad() * this.direccion);
        }

        this.posicionX = Math.max(this.limiteIzquierdo, Math.min(nuevaX, this.limiteDerecho - this.ancho));
    }
    
    @Override
    public void actualizar(List<Plataforma> plataformas, int anchoPantalla, Muro muro) {
        if (this.estaCongelado()) {
            return;
        }

        this.moverHorizontal();
    }
}
