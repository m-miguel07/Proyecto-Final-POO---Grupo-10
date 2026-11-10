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
    //Ahora EstadoJugando decide las transiciones, esto antes estaba en el ControladorJuego
    public void evaluarTransiciones(ControladorJuego controlador) {
        modelo.Nivel nivel = controlador.getNivel();
        modelo.Personaje personaje = nivel.getPersonaje();

        if (!personaje.estaVivo()) {
            controlador.cambiarEstado(EstadoDerrota.INSTANCIA);
            return;
        }

        if (nivel.estaCompleto()) {
            if (nivel.getNumeroNivel() == 1) {
                controlador.cambiarEstado(EstadoVictoria.INSTANCIA);
            } else {
                controlador.cambiarEstado(EstadoJuegoCompletado.INSTANCIA);
            }
        }
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
