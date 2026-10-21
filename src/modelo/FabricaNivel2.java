package modelo;

import java.util.ArrayList;
import java.util.List;

public class FabricaNivel2 {
    private static final int NUMERO_NIVEL = 2;

    //Escenarios del nivel, de abajo hacia arriba.
    private static final int ESCENARIO_1 = 1;
    private static final int ESCENARIO_2 = 2;
    private static final int ESCENARIO_3 = 3;

    //Zona de transicion superior.
    private static final int ZONA_X = 0;
    private static final int ZONA_Y = 0;
    private static final int ZONA_ANCHO = 400;
    private static final int ZONA_ALTO = 100;

    //Zona de transicion del escenario inicial, avanza al nivel 1 cuando toca el borde derecho.
    private static final int ZONA_FINAL_X = 1550;
    private static final int ZONA_FINAL_ANCHO = 50;

    //Muro que bloquea el paso en el escenario inicial.
    private static final int MURO_X = 1480;
    private static final int MURO_Y = 120;
    private static final int MURO_ANCHO = 120;
    private static final int MONEDA_MURO_ANCHO = 30;
    private static final int MONEDA_MURO_ALTO = 30;

    //Punto de aparicion del jugador al entrar a un escenario.
    private static final int SPAWN_X = 50;
    private static final int SPAWN_Y = 756;

    private static Integer monedasTotales;

    private FabricaNivel2() {
    }

    //Comportamientos
    public static Escenario crearEscenario(int id, Personaje personaje) {
        switch (id) {
            case ESCENARIO_1:
                return crearEscenario1(personaje);
            case ESCENARIO_2:
                return crearEscenario2(personaje);
            default:
                return null;
        }
    }

    public static Escenario crearEscenario1(Personaje personaje) {
        Nivel nivel = crearContenidoEscenario1(personaje);

        Escenario escenario = new Escenario(ESCENARIO_1, nivel, "src/assets/lvl-bg/Nivel2_1.jpg");

        //Al final del escenario esta el muro; se abre al encestar en el aro de basquet.
        escenario.agregarZonaTransicion(
                new ZonaTransicion(ZONA_FINAL_X, 0, ZONA_FINAL_ANCHO, Configuracion.ALTO, ESCENARIO_2, SPAWN_X, SPAWN_Y));
        return escenario;
    }

    public static Escenario crearEscenario2(Personaje personaje) {
        Nivel nivel = crearContenidoEscenario2(personaje);

        Escenario escenario = new Escenario(ESCENARIO_2, nivel, "src/assets/lvl-bg/Nivel2_2.jpg");

        //Transición del nivel, solo ocurre si la cabeza del personaje toca el fondo del nivel.
        escenario.agregarZonaTransicion(
                new ZonaTransicion(ZONA_X, ZONA_Y - ZONA_ALTO, ZONA_ANCHO, ZONA_ALTO,
                        ESCENARIO_3, SPAWN_X, SPAWN_Y));

        return escenario;
    }

    
    public static Escenario crearEscenario3(Personaje personaje) {
        Nivel nivel = crearContenidoEscenario3(personaje);

        return new Escenario(ESCENARIO_3, nivel, "src/assets/lvl-bg/Nivel3_3.jpg");
    }
        

    public static int getMonedasTotales() {
        if (monedasTotales == null) {
            Personaje personaje = crearPersonajeInicial();
            monedasTotales = crearEscenario1(personaje).getNivel().getMonedas().size()
                    + crearEscenario2(personaje).getNivel().getMonedas().size()
                    + crearEscenario3(personaje).getNivel().getMonedas().size();
        }
        return monedasTotales;
    }

    private static Nivel crearContenidoEscenario1(Personaje personaje) {
        List<Plataforma> plataformas = new ArrayList<>();
        List<Moneda> monedas = new ArrayList<>();
        List<Enemigo> enemigos = new ArrayList<>();
        List<Recolectable> recolectables = new ArrayList<>();

        // Suelo del escenario, cubre todo el ancho.
        plataformas.add(new Plataforma(0, Configuracion.SUELO_Y, Configuracion.ANCHO, 30));

        // Cinco enemigos terrestres al principio del nivel.
        enemigos.add(new EnemigoTerrestre(20, 44, 44, 2.0, 240, 776));
        enemigos.add(new EnemigoTerrestre(20, 44, 44, 2.2, 420, 776));
        enemigos.add(new EnemigoTerrestre(20, 44, 44, 1.8, 600, 776));
        enemigos.add(new EnemigoTerrestre(20, 44, 44, 2.1, 780, 776));
        enemigos.add(new EnemigoTerrestre(20, 44, 44, 1.9, 960, 776));

        // Monedas: una en el suelo y otra flotando a la altura del salto
        monedas.add(new Moneda(300, 770, 30, 30));
        monedas.add(new Moneda(700, 555, 30, 30));

        // Aro de basquet, en el cielo y en mitad de la pantalla.
        // al encestar, se empieza a abrir el muro.
        Aro aro = new Aro(755, 490, 90, 70, false);

        // Muro que bloquea el final del escenario hasta encestar en el aro.
        Muro muro = new Muro(MURO_X, MURO_Y, MURO_ANCHO, Configuracion.ALTO - MURO_Y);
        plataformas.add(muro);

        // Moneda apoyada sobre el muro: baja con el y queda en el suelo al abrirse.
        Moneda monedaMuro = new Moneda(0, 0, MONEDA_MURO_ANCHO, MONEDA_MURO_ALTO);
        muro.colocarMonedaEncima(monedaMuro);
        monedas.add(monedaMuro);

        return new Nivel(NUMERO_NIVEL, personaje, aro, enemigos, monedas, plataformas, recolectables, muro);
    }

    private static Nivel crearContenidoEscenario2(Personaje personaje) {
        List<Plataforma> plataformas = new ArrayList<>();
        List<Moneda> monedas = new ArrayList<>();
        List<Enemigo> enemigos = new ArrayList<>();
        List<Recolectable> recolectables = new ArrayList<>();

        // Suelo del escenario
        plataformas.add(new Plataforma(0, Configuracion.SUELO_Y, Configuracion.ANCHO, 30));

        // Plataformas del segundo escenario
        plataformas.add(new Plataforma(120, 700, 300, 26));
        plataformas.add(new Plataforma(480, 620, 240, 26));
        plataformas.add(new Plataforma(800, 540, 240, 26));
        plataformas.add(new Plataforma(1120, 460, 300, 26));
        plataformas.add(new Plataforma(860, 380, 240, 26));
        plataformas.add(new Plataforma(600, 300, 240, 26));
        plataformas.add(new Plataforma(340, 220, 240, 26));

        // Monedas
        monedas.add(new Moneda(300, 770, 30, 30));
        monedas.add(new Moneda(560, 570, 30, 30));
        monedas.add(new Moneda(860, 490, 30, 30));

        // Enemigos
        enemigos.add(new EnemigoTerrestre(20, 44, 44, 2.0, 1150, 416));
        enemigos.add(new EnemigoTerrestre(20, 44, 44, 2.0, 200, 656));
        enemigos.add(new EnemigoTerrestre(20, 44, 44, 2.1, 920, 336));

        // Este escenario no tiene aro: la meta esta en el ultimo escenario del nivel.
        return new Nivel(NUMERO_NIVEL, personaje, null, enemigos, monedas, plataformas, recolectables, null);
    }

    private static Nivel crearContenidoEscenario3(Personaje personaje) {
        List<Plataforma> plataformas = new ArrayList<>();
        List<Moneda> monedas = new ArrayList<>();
        List<Enemigo> enemigos = new ArrayList<>();
        List<Recolectable> recolectables = new ArrayList<>();

        // Sala final
        plataformas.add(new Plataforma(0, Configuracion.SUELO_Y, Configuracion.ANCHO, 30));

        // Pelota de hielo
        recolectables.add(new Recolectable(new ArmaHielo(10, 12.0, 5000, 0, 0, 16, 16),
                50, 780, 40, 40));

        //Tres enemigos voladores
        EnemigoVolador voladorBajo = new EnemigoVolador(20, 46, 40, 2.2, 780, 660);
        voladorBajo.setLimites(700, 900);
        enemigos.add(voladorBajo);

        EnemigoVolador voladorMedio = new EnemigoVolador(20, 46, 40, 2.0, 760, 500);
        voladorMedio.setLimites(680, 880);
        enemigos.add(voladorMedio);

        EnemigoVolador voladorAlto = new EnemigoVolador(20, 46, 40, 1.8, 720, 340);
        voladorAlto.setLimites(640, 760);
        enemigos.add(voladorAlto);

        // Monedas
        monedas.add(new Moneda(765, 460, 30, 30));
        monedas.add(new Moneda(690, 170, 30, 30));

        // Aro del ultimo escenario
        Aro aro = new Aro(755, 160, 90, 70);
        return new Nivel(NUMERO_NIVEL, personaje, aro, enemigos, monedas, plataformas, recolectables, null);
    }

    public static Personaje crearPersonajeInicial() {
        Personaje personaje = new Personaje(Personaje.VIDAS_INICIALES, 44, 64, 6.0, 120, 756);
        personaje.setArmaEquipada(new ArmaEstandar(10, 12.0, 0, 0, 16, 16));

        return personaje;
    }

}

