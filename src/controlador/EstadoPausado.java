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
    public void iniciar(ControladorJuego controlador){
        return;
    }
    
    @Override
    public void pausar(ControladorJuego controlador) {
        controlador.cambiarEstado(EstadoJugando.INSTANCIA);
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
