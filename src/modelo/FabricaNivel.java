package modelo;

public interface FabricaNivel {

    Escenario crearEscenario(int id, Personaje personaje);

    Personaje crearPersonajeInicial();

    int getMonedasTotales();
}
