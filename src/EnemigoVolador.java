public class EnemigoVolador extends Enemigo {

    //Atributos
    private Double altura;

    //Constructor
    public EnemigoVolador(Integer puntosVida, Double velocidad, Integer danioBase, Double altura) {
        super(puntosVida, velocidad, danioBase);

        if (altura <= 0) {
            throw new IllegalArgumentException("La altura debe ser positiva");
        }

        this.altura = altura;
    }

    //Getters
    public Double getAltura() {
        return this.altura;
    }

    //Setters
    public boolean setAltura(Double altura) {
        if (altura <= 0) {
            return false;
        }

        this.altura = altura;
        return true;
    }

    //Comportamientos
    public void volar() {
    }

}