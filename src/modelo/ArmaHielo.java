public class ArmaHielo extends Arma {
    //Atributos
    private Integer duracionRalentizacion;

    //Constructor
    public ArmaHielo (Integer danioBase, Integer alcance, double velocidad, Integer duracionRalentizacion) {
        super (danioBase, alcance, velocidad);

        if (duracionRalentizacion <= 0) {
            throw new IllegalArgumentException ("La duracion de la ralentización debe ser positiva");
        }

        this.duracionRalentizacion = duracionRalentizacion;
    }

    //Getters
    public Integer getDuracionRalentizacion() {
        return this.duracionRalentizacion;
    }

    //Setters
    public boolean setDuracionRalentizacion(Integer duracionRalentizacion) {
        if (duracionRalentizacion <= 0) {
            return false;
        }

        this.duracionRalentizacion = duracionRalentizacion;
        return true;
    }

    //Comportamientos
    public void congelar(Entidad objetivo) {
        objetivo.setVelocidad(0.0); //duraria la cantidad que determine duración relentizacioń, falta implementar.
    }

    public void ralentizar(Entidad objetivo) {
        objetivo.setVelocidad(objetivo.getVelocidad() - (objetivo.getVelocidad() - 0.7)); //idem congelar()
    }

    @Override
    public void aplicarDanio(Entidad objetivo){
        //A implementar
    }
}