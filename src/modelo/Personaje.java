package modelo;

import java.util.List;

public class Personaje extends Entidad {

    //Constantes
    public static final double GRAVEDAD = 0.9;
    public static final double FUERZA_SALTO = -19.0;
    public static final double VELOCIDAD_MAXIMA_CAIDA = 18.0;
    public static final double TOLERANCIA_CONTACTO = 6.0;

    public static final double VELOCIDAD_MAXIMA = 7.0;
    public static final double ACELERACION_SUELO = 1.8;
    public static final double ACELERACION_AIRE = 1.2;
    public static final double ROZAMIENTO_SUELO = 2.4;
    public static final double ROZAMIENTO_AIRE = 0.3;

    public static final int VIDAS_INICIALES = 3;
    public static final long INVULNERABILIDAD_MS = 2000;

    //Atributos
    private Arma armaEquipada;
    private int monedasRecolectadas;
    private int puntaje;

    private double velocidadX;
    private double velocidadY;
    private boolean enElSuelo;
    private long invulnerableHasta;
    private int direccion;

    //Constructor
    public Personaje(int puntosVida, int ancho, int alto, double velocidad, double posicionX, double posicionY) {
        super(puntosVida, ancho, alto, velocidad, posicionX, posicionY);

        this.monedasRecolectadas = 0;
        this.puntaje = 0;
        this.velocidadX = 0.0;
        this.velocidadY = 0.0;
        this.enElSuelo = false;
        this.invulnerableHasta = 0L;
        this.direccion = 1;
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

    public double getVelocidadX() {
        return this.velocidadX;
    }

    public double getVelocidadY() {
        return this.velocidadY;
    }

    public boolean isEnElSuelo() {
        return this.enElSuelo;
    }

    public int getDireccion() {
        return this.direccion;
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
    public boolean estaInvulnerable() {
        return System.currentTimeMillis() < this.invulnerableHasta;
    }

    public void perderVida() {
        this.setPuntosVida(Math.max(0, this.getPuntosVida() - 1));
        this.invulnerableHasta = System.currentTimeMillis() + INVULNERABILIDAD_MS;
    }

    @Override
    public void recibirDanio(int danio) {
        if (danio < 0) {
            throw new IllegalArgumentException("El danio recibido no puede ser negativo");
        }

        this.perderVida();
    }

    @Override
    public void atacar(Entidad objetivo) {
        if (this.armaEquipada == null) {
            throw new IllegalStateException("El personaje no tiene un arma equipada");
        }

        this.armaEquipada.aplicarDanio(objetivo);
    }

    public void actualizarHorizontal(int direccion, int anchoPantalla) {
        if (direccion != 0) {
            this.direccion = direccion;
        }

        double aceleracion = this.enElSuelo ? ACELERACION_SUELO : ACELERACION_AIRE;
        double rozamiento = this.enElSuelo ? ROZAMIENTO_SUELO : ROZAMIENTO_AIRE;

        if (direccion != 0) {
            boolean invierteSentido = this.velocidadX != 0
                    && Math.signum(this.velocidadX) != direccion;

            if (invierteSentido && this.enElSuelo) {
                aceleracion += ACELERACION_SUELO;
            }

            this.velocidadX += aceleracion * direccion;

            if (Math.abs(this.velocidadX) > VELOCIDAD_MAXIMA) {
                this.velocidadX = Math.signum(this.velocidadX) * VELOCIDAD_MAXIMA;
            }
        } else {
            if (Math.abs(this.velocidadX) <= rozamiento) {
                this.velocidadX = 0.0;
            } else {
                this.velocidadX -= Math.signum(this.velocidadX) * rozamiento;
            }
        }

        this.posicionX += this.velocidadX;

        if (this.posicionX < 0) {
            this.posicionX = 0;
            this.velocidadX = 0;
        }

        if (this.posicionX + this.ancho > anchoPantalla) {
            this.posicionX = anchoPantalla - this.ancho;
            this.velocidadX = 0;
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
        double pieAnterior = this.posicionY + this.alto;
        this.posicionY += this.velocidadY;

        this.enElSuelo = false;

        for (Plataforma plataforma : plataformas) {
            if (plataforma.isDestruida()) {
                continue;
            }

            if (this.aterrizaSobre(plataforma, pieAnterior)) {
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

    public boolean aterrizaSobre(Plataforma plataforma, double pieAnterior) {
        double pie = this.posicionY + this.alto;
        double superficie = plataforma.getPosicionY();
        boolean cayendo = this.velocidadY >= 0;

        boolean haySolapeHorizontal = this.posicionX + this.ancho > plataforma.getPosicionX()
                && this.posicionX < plataforma.getPosicionX() + plataforma.getAncho();

        return cayendo
                && haySolapeHorizontal
                && pieAnterior <= superficie
                && pie >= superficie - TOLERANCIA_CONTACTO;
    }

    public boolean estaVivo() {
        return this.getPuntosVida() > 0;
    }
}