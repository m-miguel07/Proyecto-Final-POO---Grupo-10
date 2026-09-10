package modelo;

// Archivo: Colisiones.java
// Sirve para detectar colisiones. La usan (Entidad) y (Nivel)

public class Colisiones {
    private Colisiones() {
    }

    public static boolean hayInterseccion (double x1, double y1, int ancho1, int alto1, double x2, double y2, int ancho2, int alto2) {
        return x1 < x2 + ancho2 && x1 + ancho1 > x2 && y1 < y2 + alto2 && y1 + alto1 > y2;
    }       
}
