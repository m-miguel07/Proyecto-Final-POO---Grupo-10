package modelo;

public class ArmaEstandar extends Arma {

    public static final int ID_TIPO = 0;
    //Pelota de basquet base, sin habilidades unicas

    public ArmaEstandar(int danioBase, double velocidad) {
        super(danioBase, velocidad);
    }

    @Override
    public int getIdTipo(){
        return ID_TIPO;
    }
}
