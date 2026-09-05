public class ArmaFuego extends Arma {

    //Atributos
    private Integer danioPorQuemadura;

    //Constructor
    public ArmaFuego(Integer danioBase, Integer alcance, double velocidad, Integer danioPorQuemadura) {
        super(danioBase, alcance, velocidad);

        if (danioPorQuemadura <= 0) {
            throw new IllegalArgumentException("El danio por quemadura debe ser positivo");
        }

        this.danioPorQuemadura = danioPorQuemadura;
    }

    //Getters
    public Integer getDanioPorQuemadura() {
        return this.danioPorQuemadura;
    }

    //Setters
    public boolean setDanioPorQuemadura(Integer danioPorQuemadura) {
        if (danioPorQuemadura <= 0) {
            return false;
        }

        this.danioPorQuemadura = danioPorQuemadura;
        return true;
    }

    //Comportamientos
    public void quemar(Entidad objetivo) {
        objetivo.setPuntosVida(objetivo.getPuntosVida() - this.danioPorQuemadura);
    }

    @Override
    public void aplicarDanio() {
    }
}