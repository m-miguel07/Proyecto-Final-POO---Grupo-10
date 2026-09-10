package modelo;

public class Personaje extends Entidad {

    private Arma armaEquipada;
    private int monedasRecolectadas;
    private int puntaje;


    public Personaje(int puntosVida, int ancho, int alto, double velocidad, double posicionX, double posicionY) {
        super(puntosVida, ancho, alto, velocidad, posicionX, posicionY);

        this.monedasRecolectadas = 0;
        this.puntaje = 0;
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

    //comportamientos
    @Override
    public void atacar(Entidad objetivo) {
        if (this.armaEquipada == null) {
            throw new IllegalStateException("El personaje no tiene un arma equipada");
        }

        this.armaEquipada.aplicarDanio(objetivo);
    }
}