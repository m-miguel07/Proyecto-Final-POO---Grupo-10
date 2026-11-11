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
    public void iniciar(ControladorJuego controlador){
        return;
    }

    @Override
    public void pausar(ControladorJuego controlador) {
        return;
    }

    @Override 
    public void reiniciar(ControladorJuego controlador){
        return;
    }

    @Override 
    public void salirAlMenu(ControladorJuego controlador){
        controlador.cambiarEstado(EstadoInactivo.INSTANCIA);
    }

    @Override 
    public void terminarNivel(ControladorJuego controlador){
        controlador.cambiarEstado(EstadoJugando.INSTANCIA);
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
