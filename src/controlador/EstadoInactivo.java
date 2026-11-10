package controlador;

public final class EstadoInactivo implements EstadoJuego {
    public static final EstadoInactivo INSTANCIA = new EstadoInactivo();

    private EstadoInactivo() {
    }

    @Override
    public void entrar(ControladorJuego controlador) {
        // Sin efectos al entrar.
    }

    @Override
    public void salir(ControladorJuego controlador) {
        // Sin efectos al salir.
    }

    @Override
    public void pausar(ControladorJuego controlador) {
        // No hay juego activo para pausar.
    }

    @Override
    public void evaluarTransiciones(ControladorJuego controlador) {
        // Sin juego activo.
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
