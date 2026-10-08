package modelo;

import java.util.ArrayList;
import java.util.List;

public class FabricaNivel1 {

    //Constantes
    public static final int ANCHO = 1600;
    public static final int ALTO = 900;

    private FabricaNivel1() {
    }

    public static Nivel crear() {
        List<Plataforma> plataformas = new ArrayList<>();
        List<Moneda> monedas = new ArrayList<>();
        List<Enemigo> enemigos = new ArrayList<>();
        List<Recolectable> recolectables = new ArrayList<>();

        // Fila de base: plataforma larga con el personaje
        plataformas.add(new Plataforma(0, 820, ANCHO, 30));

        Personaje personaje = new Personaje(Personaje.VIDAS_INICIALES, 44, 64, 6.0, 120, 756);
        personaje.setArmaEquipada(new ArmaEstandar(10, 40, 12.0, 0, 0, 16, 16));

        // Fila inferior: moneda suelta
        monedas.add(new Moneda(300, 770, 30, 30));

        // Fila inferior: plataforma blanca corta
        plataformas.add(new Plataforma(880, 700, 520, 26));

        /* Tres enemigos voladores, se mueven de izquierda a derecha 
        (la idea es congelarlos para poder subir a la plataforma de arriba)*/
        
        EnemigoVolador voladorIzquierdo = new EnemigoVolador(40, 12, 30, 46, 40, 2.6, 320, 640);
        voladorIzquierdo.setLimites(250, 470);
        enemigos.add(voladorIzquierdo);

        EnemigoVolador voladorCentral = new EnemigoVolador(40, 12, 30, 46, 40, 2.4, 780, 640);
        voladorCentral.setLimites(700, 920);
        enemigos.add(voladorCentral);

        EnemigoVolador voladorDerecho = new EnemigoVolador(40, 12, 30, 46, 40, 2.2, 1220, 640);
        voladorDerecho.setLimites(1150, 1370);
        enemigos.add(voladorDerecho);

        // Fila siguiente: plataforma blanca larga con un enemigo terrestre en el medio
        plataformas.add(new Plataforma(180, 560, 1180, 26));
        enemigos.add(new EnemigoTerrestre(10, 40, 44, 44, 2.0, 700, 516));

        // Fila siguiente: dos plataformas blancas, cada una con plataformas marrones (que son destructibles)
        plataformas.add(new Plataforma(120, 400, 300, 26));
        plataformas.add(new Plataforma(420, 400, 120, 26, MaterialPlataforma.MADERA));
        plataformas.add(new Plataforma(700, 400, 340, 26));
        plataformas.add(new Plataforma(1040, 400, 120, 26, MaterialPlataforma.MADERA));

        // Fila superior: plataforma blanca con segmento marron a la derecha, donde esta el aro
        plataformas.add(new Plataforma(120, 240, 300, 26));
        plataformas.add(new Plataforma(420, 240, 120, 26, MaterialPlataforma.MADERA));

        // Fila superior: plataforma blanca larga a la derecha, separada del aro
        plataformas.add(new Plataforma(1080, 240, 440, 26));

        Aro aro = new Aro(255, 170, 90, 70);

        return new Nivel(personaje, aro, enemigos, monedas, plataformas, recolectables);
    }
}