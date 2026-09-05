public abstract class Entidad {
    
    //Atributos
    private Integer puntosVida;
    private Double  velocidad;
    
    //Constructor
    public Entidad (Integer puntosVida, Double velocidad) {
        this.puntosVida = puntosVida;
        this.velocidad = velocidad; 
    }

    //Getters
    public Integer getPuntosVida(){
        return this.puntosVida;
    }

    public Double getVelocidad(){
        return this.velocidad;
    }
        
    //Setters
    public boolean setPuntosVida(Integer puntosVida){
        if (puntosVida < 0){
            return false;
        }

        this.puntosVida = puntosVida;
        return true;

    }

    public boolean setVelocidad(Double velocidad){
        if (velocidad < 0){
            return false;
        }

        this.velocidad = velocidad;
        return true;
    }

    //Comportamientos
    public abstract void atacar(Entidad objetivo);

}
