package modelo;

public class ArmaHielo extends Arma {

    //Atributos
    private int duracionRalentizacion;

    //Constructor
    public ArmaHielo(int danioBase, double velocidad, int duracionRalentizacion) {
        super(danioBase, velocidad);

        if (duracionRalentizacion <= 0) {
            throw new IllegalArgumentException("La duracion de la ralentizacion debe ser positiva");
        }

        this.duracionRalentizacion = duracionRalentizacion;
    }

    //Comportamientos
    @Override
    public void aplicarDanio(Entidad objetivo) {
        super.aplicarDanio(objetivo);
        objetivo.recibirCongelacion(this.duracionRalentizacion);
    }

    @Override
    protected TipoDanio getTipoDanio() {
        return TipoDanio.HIELO;
    }

    @Override
    public TipoRenderArma getTipoRender() {
        return TipoRenderArma.HIELO;
    }
}
