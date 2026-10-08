package modelo;

public class Cofre extends ObjetoDelJuego {

    //Atributos
    private final Moneda moneda;
    private boolean abierto;

    //Constructor
    public Cofre(double posicionX, double posicionY, int ancho, int alto, Moneda moneda) {
        super(ancho, alto, posicionX, posicionY);

        if (moneda == null) {
            throw new IllegalArgumentException("El cofre debe contener una moneda");
        }

        this.moneda = moneda;
        this.abierto = false;
    }

    //Getters
    public Moneda getMoneda() {
        return this.moneda;
    }

    public boolean isAbierto() {
        return this.abierto;
    }

    //Comportamientos
    public void abrir() {
        this.abierto = true;
    }

    public boolean contieneMonedaSinRecolectar() {
        return !this.abierto && !this.moneda.isRecolectada();
    }
}