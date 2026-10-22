package controlador;

public final class EstadoVictoria implements EstadoJuego {

    public static final EstadoVictoria INSTANCIA = new EstadoVictoria();

    private EstadoVictoria() {
    }

    @Override
    public void entrar(ControladorJuego controlador) {
        controlador.detenerTimer();
        controlador.mostrarVictoria(true);
    }

    @Override
    public void salir(ControladorJuego controlador) {
        controlador.mostrarVictoria(false);
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
