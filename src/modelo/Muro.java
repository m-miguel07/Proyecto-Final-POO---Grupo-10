package modelo;

public class Muro extends Plataforma {

    //Constantes
    private static final double VELOCIDAD_BAJADA = 8.0;

    //Atributos
    private boolean abierto;
    private Moneda monedaEncima;

    //Constructor
    public Muro(double posicionX, double posicionY, int ancho, int alto) {
        super(posicionX, posicionY, ancho, alto, MaterialPlataforma.NORMAL);

        this.abierto = false;
    }

    //Getters
    //verifica si el muro bajo completo, sino sigue bloqueando el paso al siguiente nivel.
    public boolean isBloqueando() {
        return !this.abierto || this.getPosicionY() < Configuracion.SUELO_Y;
    }

    //Comportamientos
    //coloca una moneda sobre el muro para que baje junto con el.
    public void colocarMonedaEncima(Moneda moneda) {
        this.monedaEncima = moneda;
        this.ubicarMonedaEncima();
    }

    //el muro empieza a bajar.
    public void abrir() {
        this.abierto = true;
    }

    //el muro va bajando hasta la plataforma blanca, sin que se pase de esta.
    public void actualizar() {
        if (!this.abierto) {
            return;
        }

        double nuevaY = this.getPosicionY() + VELOCIDAD_BAJADA;

        if (nuevaY >= Configuracion.SUELO_Y) {
            this.setPosicion(this.getPosicionX(), Configuracion.SUELO_Y);
        } else {
            this.setPosicion(this.getPosicionX(), nuevaY);
        }

        this.ubicarMonedaEncima();
    }

    private void ubicarMonedaEncima() {
        if (this.monedaEncima == null || this.monedaEncima.isRecolectado()) {
            return;
        }

        double x = this.getPosicionX() + (this.getAncho() - this.monedaEncima.getAncho()) / 2.0;
        double y = this.getPosicionY() - this.monedaEncima.getAlto();

        this.monedaEncima.setPosicion(x, y);
    }

    //prohibe al jugador atravesar el muro si esta cerrado o sigue bajando.
    public void bloquearPersonaje(Personaje personaje) {
        if (!this.isBloqueando() || !this.colisionaCon(personaje)) {
            return;
        }

        personaje.setPosicion(this.getPosicionX() - personaje.getAncho(), personaje.getPosicionY());
    }

    //prohibe a los enemigos atravesar el muro.
    public void bloquearEnemigo(EnemigoTerrestre enemigo) {
        if (!this.isBloqueando() || !this.colisionaCon(enemigo)) {
            return;
        }

        enemigo.setPosicion(this.getPosicionX() - enemigo.getAncho(), enemigo.getPosicionY());
        enemigo.invertirDireccion();
    }

    private boolean colisionaCon(ObjetoDelJuego objeto) {
        return objeto.getPosicionX() + objeto.getAncho() > this.getPosicionX()
                && objeto.getPosicionX() < this.getPosicionX() + this.getAncho()
                && objeto.getPosicionY() + objeto.getAlto() > this.getPosicionY();
    }
}
