
package modelo;

public class ZonaTransicion {

    //Define el rectangulo que delimita la zona de transición entre un escenario y otro(s)
    private final int x;
    private final int y;
    private final int ancho;
    private final int alto;

    private final int idEscenarioDestino;
    private final int spawnX;
    private final int spawnY;

    public ZonaTransicion(
            int x,
            int y,
            int ancho,
            int alto,
            int idEscenarioDestino,
            int spawnX,
            int spawnY) {

        this.x = x;
        this.y = y;
        this.ancho = ancho;
        this.alto = alto;
        this.idEscenarioDestino = idEscenarioDestino;
        this.spawnX = spawnX;
        this.spawnY = spawnY;
    }

    public boolean contiene(double px, double py) {
        return px >= x && px <= x + ancho
            && py >= y && py <= y + alto;
    }

    public int getIdEscenarioDestino() {
        return idEscenarioDestino;
    }

    public int getSpawnX() {
        return spawnX;
    }

    public int getSpawnY() {
        return spawnY;
    }
}
