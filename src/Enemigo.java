public abstract class Enemigo extends Entidad {
    //Atributos
    private Integer danioBase;

    public Enemigo(Integer puntosVida, Double velocidad, Integer danioBase){
        super(puntosVida,velocidad);

        if (danioBase < 0){
            throw new IllegalArgumentException("El danio base no puede ser negativo.");
        }

        this.danioBase = danioBase;
    }

    //Getters
    public Integer getDanioBase(){
        return this.danioBase;
    }

    //Setters
    public Boolean setDanioBase(Integer danioBase){
        if (danioBase <= 0) {
            return false;
        } 

        this.danioBase = danioBase;
        return true;
    }

   //Comportamientos 
    @Override
    public void atacar(Entidad objetivo){
        objetivo.setPuntosVida(objetivo.getPuntosVida() - this.getDanioBase());
    }
}
