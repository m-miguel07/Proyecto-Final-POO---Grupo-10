package modelo;

public class ArmaFuego extends Arma {

    public static final int ID_TIPO = 1;

    //Atributos
    private int danioPorQuemadura;

    //Constructor
    public ArmaFuego(int danioBase, double velocidad, int danioPorQuemadura) {
        super(danioBase, velocidad);

        if (danioPorQuemadura <= 0) {
            throw new IllegalArgumentException("El danio por quemadura debe ser positivo");
        }

        this.danioPorQuemadura = danioPorQuemadura;
    }
    
    //Getter
    @Override 
    public int getIdTipo(){
        return ID_TIPO;
    }

    //Comportamientos
    @Override
    public void aplicarDanio(Enemigo objetivo) {
        super.aplicarDanio(objetivo);
        objetivo.recibirQuemadura(2000, this.danioPorQuemadura);
    }
}
