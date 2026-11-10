package controlador;

public interface EstadoJuego {

    void entrar(ControladorJuego controlador);

    void salir(ControladorJuego controlador);

    void pausar(ControladorJuego controlador);

    void evaluarTransiciones(ControladorJuego controlador); //Nuevo, para decidir las transiciones

    boolean permiteJugar();

    boolean permiteDisparar();
}
