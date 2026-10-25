package controlador;

public final class EstadoJuegoCompletado implements EstadoJuego {

    public static final EstadoJuegoCompletado INSTANCIA = new EstadoJuegoCompletado();

    private EstadoJuegoCompletado() {
    }

    @Override
    public void entrar(ControladorJuego controlador) {
        controlador.detenerTimer();
        controlador.mostrarFinJuego(true);
    }

    @Override
    public void salir(ControladorJuego controlador) {
    }

    @Override
    public void pausar(ControladorJuego controlador) {
    }

    @Override
    public boolean permiteJugar() {
        return false;
    }

    @Override
    public boolean permiteDisparar() {
        return false;
    }
}
