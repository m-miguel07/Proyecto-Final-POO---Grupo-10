package controlador;

public interface EstadoJuego {

    void entrar(ControladorJuego controlador);

    void salir(ControladorJuego controlador);

    void iniciar(ControladorJuego controlador);

    void pausar(ControladorJuego controlador);

    void reiniciar(ControladorJuego controlador);

    void salirAlMenu(ControladorJuego controlador);

    void terminarNivel(ControladorJuego controlador);

    void perder(ControladorJuego controlador);

    boolean permiteJugar();

    boolean permiteDisparar();
}
