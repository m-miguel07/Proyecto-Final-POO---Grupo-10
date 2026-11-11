package controlador;


public final class EstadoJuegoCompletado implements EstadoJuego {

    public static final EstadoJuegoCompletado INSTANCIA = new EstadoJuegoCompletado();

    private EstadoJuegoCompletado() {
    }

    @Override
    public void entrar(ControladorJuego controlador){
        controlador.detenerTimer();
        controlador.mostrarFinJuego(true);
    }

    @Override
    public void salir(ControladorJuego controlador){
        controlador.mostrarFinJuego(false);
    }

    @Override 
    public void iniciar(ControladorJuego controlador){
        return;
    }

    @Override
    public void pausar(ControladorJuego controlador){
        return; //Idem EstadoDerrota
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
