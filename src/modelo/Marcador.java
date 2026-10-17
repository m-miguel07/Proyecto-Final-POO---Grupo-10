package modelo;

import java.util.List;

public class Marcador {

    //Comportamientos
    public int contarMonedasRecolectadas(List<Moneda> monedas) {
        int cantidad = 0;
        for (Moneda moneda : monedas) {
            if (moneda.isRecolectado()) {
                cantidad++;
            }
        }
        return cantidad;
    }

    public int contarMonedasTotales(List<Moneda> monedas) {
        return monedas.size();
    }
}
