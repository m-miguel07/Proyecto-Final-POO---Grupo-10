package modelo;

public interface PartidaDAO {
    boolean guardar(Partida partida);
    Partida cargar();
    boolean eliminar();
}
