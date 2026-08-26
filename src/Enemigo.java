public abstract class Enemigo extends Entidad{
    private Integer danioBase;

    public Enemigo(Integer puntosVida, Double velocidad, Integer danioBase){
        super(puntosVida,velocidad);

        if (danioBase < 0){
            throw new IllegalArgumentException("El daño base no puede ser negativo.");
        }

        this.danioBase = danioBase;
    }

    //Getters
    public Integer getDanioBase(){
        return this.danioBase;
    }

    @Override
    public void atacar(Entidad objetivo){
        objetivo.setPuntosVida(objetivo.getPuntosVida() - this.getDanioBase());
    }
}
