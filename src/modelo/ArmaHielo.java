package modelo;

public class ArmaHielo extends Arma {

    //Atributos
    private int duracionRalentizacion;

    //Constructor
    public ArmaHielo(int danioBase, int alcance, double velocidad, int duracionRalentizacion) {
        super(danioBase, alcance, velocidad);

        if (duracionRalentizacion <= 0) {
            throw new IllegalArgumentException("La duracion de la ralentizacion debe ser positiva");
        }

        this.duracionRalentizacion = duracionRalentizacion;
    }

    //Getters
    public int getDuracionRalentizacion() {
        return this.duracionRalentizacion;
    }

    //Setters
    public boolean setDuracionRalentizacion(int duracionRalentizacion) {
        if (duracionRalentizacion <= 0) {
            return false;
        }

        this.duracionRalentizacion = duracionRalentizacion;
        return true;
    }

    //Comportamientos
    public void congelar(Entidad objetivo) {
        objetivo.setVelocidad(0.0); //duraria la cantidad que determine duracionRalentizacion, falta implementar.
    }

    public void ralentizar(Entidad objetivo) {
        objetivo.setVelocidad(objetivo.getVelocidad() * 0.7);
    }

    @Override
    public void aplicarDanio(Entidad objetivo) {
        objetivo.recibirDanio(this.getDanioBase());
    }
}