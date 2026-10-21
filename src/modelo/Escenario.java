
package modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Escenario {

    private final int id;
    private final Nivel nivel;
    private final String rutaFondo;
    private final List<ZonaTransicion> zonasTransicion;

    public Escenario(int id, Nivel nivel, String rutaFondo) {
        this.id = id;
        this.nivel = nivel;
        this.rutaFondo = rutaFondo;
        this.zonasTransicion = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public Nivel getNivel() {
        return nivel;
    }

    public String getRutaFondo() {
        return rutaFondo;
    }

    public List<ZonaTransicion> getZonasTransicion() {
        return Collections.unmodifiableList(zonasTransicion);
    }

    public void agregarZonaTransicion(ZonaTransicion zona) {
        zonasTransicion.add(zona);
    }

}
