package modelo;

public class Boton extends Interaccion {

    private boolean activado;

    public Boton(double posicionX, double posicionY, int ancho, int alto) {
        super(ancho, alto, posicionX, posicionY);

        this.activado = false;
    }

    public boolean isActivado() {
        return this.activado;
    }

    public void activar() {
        this.activado = true;
    }

    @Override
    public void reaccionInteraccion(Pelota pelota) {
        this.activar();
    }
}
