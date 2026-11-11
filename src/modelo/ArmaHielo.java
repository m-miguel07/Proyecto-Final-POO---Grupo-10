package modelo;

public class ArmaHielo extends Arma {

    //Atributos
    private int duracionCongelacion;

    //Constructor
    public ArmaHielo(int danioBase, double velocidad, int duracionCongelacion) {
        super(danioBase, velocidad);

        if (duracionCongelacion <= 0) {
            throw new IllegalArgumentException("La duracion de la congelación debe ser positiva");
        }

        this.duracionCongelacion = duracionCongelacion;
    }

    //Comportamientos
    @Override
    public void aplicarDanio(Entidad objetivo) {
        super.aplicarDanio(objetivo);
        objetivo.recibirCongelacion(this.duracionCongelacion);
    }

    @Override
    public TipoElemento getElemento() {
        return TipoElemento.HIELO;
    }
}
