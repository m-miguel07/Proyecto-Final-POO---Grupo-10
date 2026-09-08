public class ArmaEstandar extends Arma {
    
    //Pelota de basquet base, sin habilidades unicas
    
    public ArmaEstandar (Integer danioBase, Integer alcance, double velocidad){
        super(danioBase,alcance,velocidad);
    }

    @Override 
    public void aplicarDanio(Entidad objetivo){
        //A implementar
    }
}
