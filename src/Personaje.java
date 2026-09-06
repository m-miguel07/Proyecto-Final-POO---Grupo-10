public class Personaje extends Entidad{

    private Arma armaEquipada;
    private Integer monedasRecolectadas;
    private Integer puntaje;


    public Personaje(Integer puntosVida, Double velocidad){
        super(puntosVida, velocidad);
    }

    //Getters
    public Arma getArmaEquipada(){
        return this.armaEquipada;
    }

    public Integer getMonedasRecolectadas(){
        return this.monedasRecolectadas;
    }

    public Integer getPuntaje(){
        return this.puntaje;
    }

    //Setters
    public boolean setArmaEquipada(Arma arma){
        this.armaEquipada =  arma;
        return true;
    }

    public boolean setMonedasRecolectadas(Integer monedasRecolectadas){
        // puse mayor a cero por que no se puede recolectar algo que no se tiene(osea cero) ni tampoco cantidades negativas.
        if (monedasRecolectadas > 0){
            this.monedasRecolectadas = monedasRecolectadas;
            return true;
        }
        return false;
    }


    //comportamientos

    @Override
    public void atacar(Entidad objetivo){
        Integer danioProvocado = this.armaEquipada.getDanioBase();
        objetivo.setPuntosVida(objetivo.getPuntosVida() - danioProvocado);
    }
}
