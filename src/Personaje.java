public class Personaje extends Entidad{

    private Arma armaEquipada;
    private Integer monedaRecoletadas;
    private Integer puntaje;


    public Personaje(Integer puntosVida, Double velocidad){
        super(puntosVida, velocidad);
    }

    //Getters
    public Arma getArmaEquipada(){
        return this.armaEquipada;
    }

    public Integer getMonedaRecolectadas(){
        return this.monedaRecoletadas;
    }

    public Integer getPuntaje(){
        return this.puntaje;
    }

    //Setters
    public boolean setArmaEquipada(Arma arma){
        this.armaEquipada =  arma;
        return true;
    }

    public boolean setMoneadaRecolectadas(Integer monedaRecolectada){
        // puse mayor a cero por que no se puede recolectar algo que no se tiene(osea cero) ni tampoco cantidades negativas.
        if (monedaRecolectada > 0){
            this.monedaRecoletadas = monedaRecolectada;
            return true;
        }
        return false;
    }


    //comportamientos

    @Override
    public void atacar(Entidad objetivo){
        Integer danioProbocado = this.armaEquipada.getDanioBase();
        objetivo.setPuntosVida(objetivo.getPuntosVida() - danioProbocado);
    }


}
