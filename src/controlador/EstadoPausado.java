package controlador;

public final class EstadoPausado implements EstadoJuego {

    public static final EstadoPausado INSTANCIA = new EstadoPausado();

    private EstadoPausado() {
    }

    @Override
    public void entrar(ControladorJuego controlador) {
        controlador.detenerTimer();
        controlador.pausarMusica();
        controlador.mostrarPausa(true);
    }

    @Override
    public void salir(ControladorJuego controlador) {
        controlador.mostrarPausa(false);
    }

    @Override
    public void pausar(ControladorJuego controlador) {
        controlador.cambiarEstado(EstadoJugando.INSTANCIA);
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
