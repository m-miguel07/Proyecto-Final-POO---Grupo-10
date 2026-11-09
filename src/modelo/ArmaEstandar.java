package modelo;

public class ArmaEstandar extends Arma {

    public static final int ID_TIPO = 0;
    //Pelota de basquet base, sin habilidades unicas

    public ArmaEstandar(int danioBase, double velocidad, double posicionX, double posicionY, int ancho, int alto) {
        super(danioBase, velocidad, posicionX, posicionY, ancho, alto);
    }

    @Override
    public int getIdTipo(){
        return ID_TIPO;
    }
}
