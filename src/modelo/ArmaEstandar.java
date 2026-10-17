package modelo;

public class ArmaEstandar extends Arma {

    //Pelota de basquet base, sin habilidades unicas

    public ArmaEstandar(int danioBase, double velocidad, double posicionX, double posicionY, int ancho, int alto) {
        super(danioBase, velocidad, posicionX, posicionY, ancho, alto);
    }

    @Override
    public void aplicarDanio(Entidad objetivo) {
        objetivo.recibirDanio(this.getDanioBase());
    }
}
