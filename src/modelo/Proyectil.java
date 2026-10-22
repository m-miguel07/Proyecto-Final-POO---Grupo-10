package modelo;

public class Proyectil extends ObjetoDelJuego {

    private final double velocidadX;
    private final double velocidadY;
    private boolean activo;

    public Proyectil(double posicionX, double posicionY, int ancho, int alto,
                     double velocidadX, double velocidadY) {
        super(ancho, alto, posicionX, posicionY);

        this.velocidadX = velocidadX;
        this.velocidadY = velocidadY;
        this.activo = true;
    }

    public boolean isActivo() {
        return this.activo;
    }

    public void mover(double factor) {
        this.posicionX += this.velocidadX * factor;
        this.posicionY += this.velocidadY * factor;
    }

    public void desactivar() {
        this.activo = false;
    }

    public boolean estaFueraDePantalla(int anchoPantalla, int altoPantalla) {
        return this.posicionX + this.ancho < 0
                || this.posicionX > anchoPantalla
                || this.posicionY + this.alto < 0
                || this.posicionY > altoPantalla;
    }
}
