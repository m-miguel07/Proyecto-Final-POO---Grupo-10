package controlador;

public final class EstadoInactivo implements EstadoJuego {
    public static final EstadoInactivo INSTANCIA = new EstadoInactivo();

    private EstadoInactivo(){
    }

    public void entrar(ControladorJuego controlador){
        controlador.detenerTimer();
        controlador.getPanel().ocultarPaneles();
        controlador.detenerMusica();

    }

    public void salir(ControladorJuego controlador){
        return;
    }

    public void pausar(ControladorJuego controlador){
        return;
    }

    public boolean permiteJugar(){
        return false;
    }

    public boolean permiteDisparar(){
        return false;
    }
}
