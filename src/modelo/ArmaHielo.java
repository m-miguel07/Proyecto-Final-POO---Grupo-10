package modelo;

public class ArmaHielo extends Arma {

    public static final int ID_TIPO = 2;

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

    //Getter
    @Override
    public int getIdTipo(){
        return ID_TIPO;
    }
    
    //Comportamientos
    @Override
    public void aplicarDanio(Enemigo objetivo) {
        objetivo.recibirDanioDeHielo(this.getDanioBase());
        objetivo.recibirCongelacion(this.duracionRalentizacion);
    }
}
