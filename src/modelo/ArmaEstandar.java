package modelo;

public class ArmaEstandar extends Arma {

    //Pelota de basquet base, sin habilidades unicas

    public ArmaEstandar(int danioBase, int alcance, double velocidad) {
        super(danioBase, alcance, velocidad);
    }

    @Override
    public void aplicarDanio(Entidad objetivo) {
        objetivo.recibirDanio(this.getDanioBase());
    }
}