package modelo;

import java.util.List;

public class EnemigoPerseguidor extends Enemigo {

    // Constantes 
    private static final double GRAVEDAD = 0.9;
    private static final double FUERZA_SALTO = -16.0;
    private static final double VELOCIDAD_MAXIMA_CAIDA = 18.0;
    private static final double TOLERANCIA_CONTACTO = 6.0;

    private Personaje objetivo;
    private double velocidadX;
    private double velocidadY;
    private boolean enElSuelo;
    private int direccion;
    private int tiempoAturdidoMS;
    
    private double velocidadMovimiento;
    


    public EnemigoPerseguidor(int puntosVida, int ancho, int alto, double velocidad, double posicionX, double posicionY) {
        super(puntosVida, ancho, alto, velocidad, posicionX, posicionY);

        this.velocidadMovimiento = velocidad;
        this.velocidadX = 0;
        this.velocidadY = 0;
        this.enElSuelo = false;
        this.direccion = 1;
        this.tiempoAturdidoMS = 0;
    }

    public void setObjetivo(Personaje objetivo) {
        this.objetivo = objetivo;
    }

    public int getDireccion() {
        return this.direccion;
    }

    public boolean isEnElSuelo() {
        return this.enElSuelo;
    }

    public boolean estaAturdido() {
        return this.tiempoAturdidoMS > 0;
    }

    @Override
    public void invertirDireccion() {
        this.direccion *= -1;
    }

   @Override
    public void recibirDanio(int danio) {
        super.recibirDanio(danio); // Resta la vida normalmente
        
            if (this.getPuntosVida() > 0) {
            this.tiempoAturdidoMS = 2000; 
            this.velocidadX = 0; // Frena en seco
        }
    }

    @Override
    public void actualizarEstados(int deltaMS) {
        super.actualizarEstados(deltaMS);
        if (this.tiempoAturdidoMS > 0) {
            this.tiempoAturdidoMS = Math.max(0, this.tiempoAturdidoMS - deltaMS);
        }
    }

   @Override
    public void actualizar(List<Plataforma> plataformas, int anchoPantalla, Muro muro) {
        if (this.estaCongelado() || this.objetivo == null) {
            return;
        }

        // 1. Lógica de persecución (si no está aturdido)
        if (!this.estaAturdido()) {
            if (this.objetivo.getPosicionX() < this.getPosicionX()) {
                this.direccion = -1;
            } else {
                this.direccion = 1;
            }
            this.velocidadX = this.velocidadMovimiento * this.direccion;
        }

        this.posicionX += this.velocidadX;

        // Límites de pantalla
        if (this.posicionX < 0) {
            this.posicionX = 0;
        } else if (this.posicionX + this.ancho > anchoPantalla) {
            this.posicionX = anchoPantalla - this.ancho;
        }

        if (this.enElSuelo && this.objetivo.getPosicionY() < this.getPosicionY() - 30) {
            this.velocidadY = FUERZA_SALTO;
            this.enElSuelo = false;
        }

        // 4. Gravedad
        this.velocidadY += GRAVEDAD;
        if (this.velocidadY > VELOCIDAD_MAXIMA_CAIDA) {
            this.velocidadY = VELOCIDAD_MAXIMA_CAIDA;
        }

        // 5. Movimiento Vertical y Colisiones con Plataformas
        double pieAnterior = this.posicionY + this.alto;
        this.posicionY += this.velocidadY;
        this.enElSuelo = false;

        for (Plataforma plataforma : plataformas) {
            if (plataforma.isDestruida()) continue;

            // Colisión al caer (aterrizar)
            if (this.velocidadY >= 0 && this.posicionX + this.ancho > plataforma.getPosicionX()
                    && this.posicionX < plataforma.getPosicionX() + plataforma.getAncho()
                    && pieAnterior <= plataforma.getPosicionY()
                    && this.posicionY + this.alto >= plataforma.getPosicionY() - TOLERANCIA_CONTACTO) {
                
                this.posicionY = plataforma.getPosicionY() - this.alto;
                this.velocidadY = 0;
                this.enElSuelo = true;
            }
            
            // Colisión al saltar (golpear techo)
            if (this.velocidadY < 0 && this.posicionX + this.ancho > plataforma.getPosicionX()
                    && this.posicionX < plataforma.getPosicionX() + plataforma.getAncho()
                    && this.posicionY <= plataforma.getPosicionY() + plataforma.getAlto()
                    && this.posicionY >= plataforma.getPosicionY()) {
                
                this.posicionY = plataforma.getPosicionY() + plataforma.getAlto();
                this.velocidadY = 0;
            }
        }

        // Colisión con el muro
        if (muro != null) {
            muro.bloquearEnemigo(this);
        }
    }
}