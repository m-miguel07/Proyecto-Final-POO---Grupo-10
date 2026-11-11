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
    public void iniciar(ControladorJuego controlador){
        return;
    }

    @Override
    public void pausar(ControladorJuego controlador) {
        controlador.cambiarEstado(EstadoPausado.INSTANCIA);
    }

    @Override 
    public void reiniciar(ControladorJuego controlador) {
        controlador.reiniciar();
    }

    @Override 
    public void salirAlMenu(ControladorJuego controlador){
        return;
    }
    
    @Override 
    public void terminarNivel(ControladorJuego controlador){
        switch (controlador.getNivel().getNumeroNivel()){
            case 1:
                controlador.cambiarEstado(EstadoVictoria.INSTANCIA);
                break;
            case 2:
                controlador.cambiarEstado(EstadoJuegoCompletado.INSTANCIA);
                break;
        }
    }

    @Override 
    public void perder(ControladorJuego controlador){
        controlador.cambiarEstado(EstadoDerrota.INSTANCIA);
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
