package controlador;

public interface EstadoJuego {

    void entrar(ControladorJuego controlador);

    void salir(ControladorJuego controlador);

    void pausar(ControladorJuego controlador);

    boolean permiteJugar();

    boolean permiteDisparar();
}
