public abstract class Arma {   

    //Atributos
    private Integer danioBase;
    private Integer alcance;
    private double velocidad;

    //Constructor
    public Arma (Integer danioBase, Integer alcance, double velocidad){
        if (danioBase <= 0){
            throw new IllegalArgumentException("El daño base debe ser positivo");
        }

        if (alcance <= 0){
            throw new IllegalArgumentException("El alcance debe ser positivo");
        }

        if (velocidad <= 0){
            throw new IllegalArgumentException("La velocidad debe ser positiva");
        }

        this.danioBase = danioBase;
        this.alcance = alcance;
        this.velocidad = velocidad;
    }

    //Getters
    public Integer getDanioBase(){
        return this.danioBase;
    }

    public Integer getAlcance(){
        return this.alcance;
    }

    public double getVelocidad(){
        return this.velocidad;
    }

    //Comportamientos
    public abstract void aplicarDanio();
}
