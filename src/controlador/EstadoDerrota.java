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
    public void iniciar(ControladorJuego controlador){
        return;
    }

    @Override
    public void pausar(ControladorJuego controlador) {
        return; //No existe pausa en el estado de derrota
    }

    @Override 
    public void reiniciar(ControladorJuego controlador){
        controlador.reiniciar();
        controlador.cambiarEstado(EstadoJugando.INSTANCIA);
    }

    @Override 
    public void salirAlMenu(ControladorJuego controlador){
        controlador.cambiarEstado(EstadoInactivo.INSTANCIA);
    }

    @Override
    public void terminarNivel(ControladorJuego controlador){
        return;
    }

    @Override 
    public void perder(ControladorJuego controlador){
        return;
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
