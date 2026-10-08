package modelo;

import java.util.ArrayList;
import java.util.List;

public class FabricaNivel1 {

    public static final int ANCHO = 1600;
    public static final int ALTO = 900;

    private FabricaNivel1() {
    }

    public static Nivel crear() {
        List<Plataforma> plataformas = new ArrayList<>();
        List<Moneda> monedas = new ArrayList<>();
        List<Enemigo> enemigos = new ArrayList<>();
        List<Cofre> cofres = new ArrayList<>();
        List<Recolectable> recolectables = new ArrayList<>();

        // Fila de base: plataforma larga con el personaje y la pelota de hielo
        plataformas.add(new Plataforma(0, 820, ANCHO, 30));

        Personaje personaje = new Personaje(100, 44, 64, 6.0, 120, 756);
        personaje.setArmaEquipada(new ArmaEstandar(10, 40, 12.0, 0, 0, 16, 16));

        // Pelota de hielo (circulo celeste) cerca del personaje
        recolectables.add(new Recolectable(
                new ArmaHielo(8, 40, 11.0, 4000, 0, 0, 22, 22),
                230, 790, 30, 30));

        // Fila inferior: moneda suelta a la izquierda
        monedas.add(new Moneda(300, 770, 30, 30));

        // Fila inferior: plataforma blanca corta con dos enemigos voladores en zigzag
        plataformas.add(new Plataforma(880, 700, 520, 26));

        EnemigoVolador volador1 = new EnemigoVolador(60, 12, 30, 46, 40, 2.6, 960, 640);
        volador1.setLimites(900, 1380);
        volador1.setPosicionYBase(640);
        enemigos.add(volador1);

        EnemigoVolador volador2 = new EnemigoVolador(60, 12, 30, 46, 40, 2.2, 1240, 620);
        volador2.setLimites(900, 1380);
        volador2.setPosicionYBase(620);
        enemigos.add(volador2);

        // Fila siguiente: plataforma blanca larga con un enemigo terrestre en el medio
        plataformas.add(new Plataforma(180, 560, 1180, 26));
        enemigos.add(new EnemigoTerrestre(10, 40, 44, 44, 2.0, 700, 516));

        // Fila siguiente: dos plataformas marrones con huecos entre segmentos
        plataformas.add(new Plataforma(120, 400, 420, 26, MaterialPlataforma.MADERA));
        plataformas.add(new Plataforma(700, 400, 460, 26, MaterialPlataforma.MADERA));

        // Fila superior: cofre con moneda sobre plataforma marron (izquierda)
        plataformas.add(new Plataforma(120, 240, 420, 26, MaterialPlataforma.MADERA));
        Moneda monedaCofre = new Moneda(300, 150, 30, 30);
        cofres.add(new Cofre(280, 180, 70, 60, monedaCofre));

        // Fila superior: plataforma blanca larga a la derecha, separada del cofre
        plataformas.add(new Plataforma(1080, 240, 440, 26));

        // Pelota de fuego (figura con remera roja) al alcance del jugador
        recolectables.add(new Recolectable(
                new ArmaFuego(14, 40, 12.5, 6, 0, 0, 24, 24),
                600, 790, 30, 30));

        Aro aro = new Aro(1420, 120, 90, 70);

        return new Nivel(personaje, aro, enemigos, monedas, plataformas, cofres, recolectables);
    }
}