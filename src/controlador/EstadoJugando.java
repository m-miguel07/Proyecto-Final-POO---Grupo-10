package controlador;

public final class EstadoJugando implements EstadoJuego {

    public static final EstadoJugando INSTANCIA = new EstadoJugando();

    private EstadoJugando() {
    }

    @Override
    public void entrar(ControladorJuego controlador) {
        controlador.ocultarPaneles();
        controlador.reanudarMusica();
        controlador.iniciarTimer();
        controlador.getPanel().requestFocusInWindow();
    }

    @Override
    public void salir(ControladorJuego controlador) {
        controlador.detenerTimer();
    }

    @Override
    public void pausar(ControladorJuego controlador) {
        controlador.cambiarEstado(EstadoPausado.INSTANCIA);
    }

    @Override
    public boolean permiteJugar() {
        return true;
    }

    @Override
    public boolean permiteDisparar() {
        return true;
    }
}
