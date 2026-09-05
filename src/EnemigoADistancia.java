public class EnemigoADistancia extends Enemigo {

    //Atributos
    private Integer alcance;

    //Constructor
    public EnemigoADistancia(Integer puntosVida, Double velocidad, Integer danioBase, Integer alcance) {
        super(puntosVida, velocidad, danioBase);

        if (alcance <= 0) {
            throw new IllegalArgumentException("El alcance debe ser positivo");
        }

        this.alcance = alcance;
    }

    //Getters
    public Integer getAlcance() {
        return this.alcance;
    }

    //Setters
    public boolean setAlcance(Integer alcance) {
        if (alcance <= 0) {
            return false;
        }

        this.alcance = alcance;
        return true;
    }

    //Comportamientos
    @Override
    public void atacar(Entidad objetivo) {
        objetivo.setPuntosVida(objetivo.getPuntosVida() - this.getDanioBase());
    }
}