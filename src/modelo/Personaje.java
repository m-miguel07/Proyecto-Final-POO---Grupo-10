package modelo;

import java.util.List;

public class Personaje extends Entidad {

    public static final double GRAVEDAD = 0.9;
    public static final double FUERZA_SALTO = -19.0;
    public static final double VELOCIDAD_MAXIMA_CAIDA = 18.0;
    public static final double TOLERANCIA_CONTACTO = 6.0;

    //Atributos
    private Arma armaEquipada;
    private int monedasRecolectadas;
    private int puntaje;

    private double velocidadY;
    private boolean enElSuelo;

    //Constructor
    public Personaje(int puntosVida, int ancho, int alto, double velocidad, double posicionX, double posicionY) {
        super(puntosVida, ancho, alto, velocidad, posicionX, posicionY);

        this.monedasRecolectadas = 0;
        this.puntaje = 0;
        this.velocidadY = 0.0;
        this.enElSuelo = false;
    }

    //Getters
    public Arma getArmaEquipada() {
        return this.armaEquipada;
    }

    public int getMonedasRecolectadas() {
        return this.monedasRecolectadas;
    }

    public int getPuntaje() {
        return this.puntaje;
    }

    public double getVelocidadY() {
        return this.velocidadY;
    }

    public boolean isEnElSuelo() {
        return this.enElSuelo;
    }

    //Setters
    public boolean setArmaEquipada(Arma arma) {
        this.armaEquipada = arma;
        return true;
    }

    public boolean setMonedasRecolectadas(int monedasRecolectadas) {
        // puse mayor a cero por que no se puede recolectar algo que no se tiene(osea cero) ni tampoco cantidades negativas.
        if (monedasRecolectadas > 0) {
            this.monedasRecolectadas = monedasRecolectadas;
            return true;
        }
        return false;
    }

    public void sumarMoneda() {
        this.monedasRecolectadas++;
    }

    public void sumarPuntaje(int puntos) {
        if (puntos > 0) {
            this.puntaje += puntos;
        }
    }

    public void setVelocidadY(double velocidadY) {
        this.velocidadY = velocidadY;
    }

    public void setEnElSuelo(boolean enElSuelo) {
        this.enElSuelo = enElSuelo;
    }

    //comportamientos
    @Override
    public void atacar(Entidad objetivo) {
        if (this.armaEquipada == null) {
            throw new IllegalStateException("El personaje no tiene un arma equipada");
        }

        this.armaEquipada.aplicarDanio(objetivo);
    }

    public void moverHorizontal(int direccion, int anchoPantalla) {
        if (direccion == 0) {
            return;
        }

        this.posicionX += this.getVelocidad() * direccion;

        if (this.posicionX < 0) {
            this.posicionX = 0;
        }

        if (this.posicionX + this.ancho > anchoPantalla) {
            this.posicionX = anchoPantalla - this.ancho;
        }
    }

    public boolean saltar() {
        if (!this.enElSuelo) {
            return false;
        }

        this.velocidadY = FUERZA_SALTO;
        this.enElSuelo = false;
        return true;
    }

    public void aplicarGravedad() {
        if (this.enElSuelo) {
            this.velocidadY = 0.0;
            return;
        }

        this.velocidadY += GRAVEDAD;

        if (this.velocidadY > VELOCIDAD_MAXIMA_CAIDA) {
            this.velocidadY = VELOCIDAD_MAXIMA_CAIDA;
        }
    }

    public void actualizarVertical(List<Plataforma> plataformas, int altoPantalla) {
        this.posicionY += this.velocidadY;

        this.enElSuelo = false;

        for (Plataforma plataforma : plataformas) {
            if (plataforma.isDestruida()) {
                continue;
            }

            if (this.estaApoyadoEn(plataforma)) {
                this.posicionY = plataforma.getPosicionY() - this.alto;
                this.velocidadY = 0.0;
                this.enElSuelo = true;
            }
        }

        if (this.posicionY + this.alto >= altoPantalla) {
            this.posicionY = altoPantalla - this.alto;
            this.velocidadY = 0.0;
            this.enElSuelo = true;
        }
    }

    public boolean haySolapamiento(Plataforma plataforma) {
        return Colisiones.hayInterseccion(
                this.posicionX, this.posicionY, this.ancho, this.alto,
                plataforma.getPosicionX(), plataforma.getPosicionY(),
                plataforma.getAncho(), plataforma.getAlto());
    }

    public boolean estaApoyadoEn(Plataforma plataforma) {
        double pie = this.posicionY + this.alto;

        return this.velocidadY >= 0
                && this.posicionX + this.ancho > plataforma.getPosicionX()
                && this.posicionX < plataforma.getPosicionX() + plataforma.getAncho()
                && Math.abs(pie - plataforma.getPosicionY()) <= TOLERANCIA_CONTACTO;
    }

    public boolean estaVivo() {
        return this.getPuntosVida() > 0;
    }
}