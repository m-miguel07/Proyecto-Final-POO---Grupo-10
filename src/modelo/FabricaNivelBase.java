package modelo;

public abstract class FabricaNivelBase implements FabricaNivel {

    //Coordenadas, tamaños, IDS fijos.

    // Escenarios del nivel, de abajo hacia arriba.
    protected static final int ESCENARIO_1 = 1;
    protected static final int ESCENARIO_2 = 2;
    protected static final int ESCENARIO_3 = 3;

    // Zona de transición superior.
    protected static final int ZONA_X = 0;
    protected static final int ZONA_Y = 0;
    protected static final int ZONA_ANCHO = 400;
    protected static final int ZONA_ALTO = 100;

    // Zona de transición del escenario inicial (avance al siguiente).
    protected static final int ZONA_FINAL_X = 1550;
    protected static final int ZONA_FINAL_ANCHO = 50;

    // Muro que bloquea el paso en el escenario inicial.
    protected static final int MURO_X = 1480;
    protected static final int MURO_Y = 120;
    protected static final int MURO_ANCHO = 120;
    protected static final int MONEDA_MURO_ANCHO = 30;
    protected static final int MONEDA_MURO_ALTO = 30;

    // Punto de aparición del jugador al entrar a un escenario.
    protected static final int SPAWN_X = 50;
    protected static final int SPAWN_Y = 756;
}
