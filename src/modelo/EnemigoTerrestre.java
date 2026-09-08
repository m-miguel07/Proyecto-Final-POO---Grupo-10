public class EnemigoTerrestre extends Enemigo {
    
    //Constructor
    public EnemigoTerrestre(Integer puntosVida, Double velocidad, Integer danioBase) {
        super(puntosVida, velocidad, danioBase);
    }

    //Comportamientos
    @Override
    public void atacar(Entidad objetivo) {
        objetivo.setPuntosVida(objetivo.getPuntosVida() - this.getDanioBase());
    }
}