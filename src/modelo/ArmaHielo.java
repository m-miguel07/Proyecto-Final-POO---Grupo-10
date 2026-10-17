package modelo;

public class ArmaHielo extends Arma {

    //Atributos
    private int duracionRalentizacion;

    //Constructor
    public ArmaHielo(int danioBase, double velocidad, int duracionRalentizacion, double posicionX, double posicionY, int ancho, int alto) {
        super(danioBase, velocidad, posicionX, posicionY, ancho, alto);

        if (duracionRalentizacion <= 0) {
            throw new IllegalArgumentException("La duracion de la ralentizacion debe ser positiva");
        }

        this.duracionRalentizacion = duracionRalentizacion;
    }

    //Comportamientos
    public void congelar(Entidad objetivo) {
        if (objetivo instanceof Enemigo) {
            ((Enemigo) objetivo).congelar(this.duracionRalentizacion);
        }
    }

    @Override
    public void aplicarDanio(Entidad objetivo) {
        objetivo.recibirDanio(this.getDanioBase());
        this.congelar(objetivo);
    }
}
