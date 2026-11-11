package modelo;

public class ArmaEstandar extends Arma {

    //Pelota de basquet base, sin habilidades unicas

    public ArmaEstandar(int danioBase, double velocidad) {
        super(danioBase, velocidad);
    }

    @Override
    public TipoElemento getElemento() {
        return TipoElemento.NORMAL;
    }
}
