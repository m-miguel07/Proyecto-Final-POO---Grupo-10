package controlador;

public final class EstadoInactivo implements EstadoJuego {
    public static final EstadoInactivo INSTANCIA = new EstadoInactivo();

    private EstadoInactivo(){
    }

    @Override
    public void entrar(ControladorJuego controlador){
        controlador.detenerTimer();
        controlador.getPanel().ocultarPaneles();
        controlador.detenerMusica();
    }

    @Override
    public void salir(ControladorJuego controlador){
        return;
    }

    @Override 
    public void iniciar(ControladorJuego controlador){
        controlador.inicializar();
        controlador.iniciarMusica();
        controlador.cambiarEstado(EstadoJugando.INSTANCIA);

    }

    @Override
    public void pausar(ControladorJuego controlador){
        return;
    }

    @Override 
    public void reiniciar(ControladorJuego controlador){
        return;
    }
    
    @Override
    public void salirAlMenu(ControladorJuego controlador){
        return;
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
    public boolean permiteJugar(){
        return false;
    }

    @Override
    public boolean permiteDisparar(){
        return false;
    }
}
