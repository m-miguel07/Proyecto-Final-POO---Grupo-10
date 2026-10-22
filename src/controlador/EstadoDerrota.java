package controlador;

public final class EstadoDerrota implements EstadoJuego {

    public static final EstadoDerrota INSTANCIA = new EstadoDerrota();

    private EstadoDerrota() {
    }

    @Override
    public void entrar(ControladorJuego controlador) {
        controlador.detenerTimer();
        controlador.mostrarDerrota(true);
    }

    @Override
    public void salir(ControladorJuego controlador) {
        controlador.mostrarDerrota(false);
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
