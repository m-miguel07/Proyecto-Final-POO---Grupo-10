package modelo;

public class Aro extends ObjetoDelJuego {

    //Atributos
    private final boolean esMeta;
    private boolean encestado;

    private double limiteIzquierdo;
    private double limiteDerecho;
    private double velocidadHorizontal;
    private int direccion;

    //Constructor
    public Aro(double posicionX, double posicionY, int ancho, int alto) {
        this(posicionX, posicionY, ancho, alto, true);
    }

    public Aro(double posicionX, double posicionY, int ancho, int alto, boolean esMeta) {
        super(ancho, alto, posicionX, posicionY);

        this.encestado = false;
        this.esMeta = esMeta;
        this.direccion = 1;
    }

    //Getters
    public boolean isEncestado() {
        return this.encestado;
    }

    public boolean esMeta() {
        return this.esMeta;
    }

    //Setters
    public void configurarMovimientoHorizontal(double limiteIzquierdo, double limiteDerecho, double velocidad) {
        if (limiteIzquierdo >= limiteDerecho) {
            throw new IllegalArgumentException("El limite izquierdo debe ser menor que el derecho");
        }

        if (velocidad <= 0) {
            throw new IllegalArgumentException("La velocidad debe ser positiva");
        }

        this.limiteIzquierdo = limiteIzquierdo;
        this.limiteDerecho = limiteDerecho;
        this.velocidadHorizontal = velocidad;
    }

    //Comportamientos
    public void encestar() {
        this.encestado = true;
    }

    public void actualizar() {
        if (this.velocidadHorizontal <= 0 || this.encestado) {
            return;
        }

        double nuevaX = this.getPosicionX() + this.velocidadHorizontal * this.direccion;

        if (nuevaX <= this.limiteIzquierdo) {
            nuevaX = this.limiteIzquierdo;
            this.direccion = 1;
        } else if (nuevaX >= this.limiteDerecho) {
            nuevaX = this.limiteDerecho;
            this.direccion = -1;
        }

        this.setPosicion(nuevaX, this.getPosicionY());
    }
}
