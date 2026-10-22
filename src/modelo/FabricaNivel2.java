package modelo;

import java.util.ArrayList;
import java.util.List;

public class FabricaNivel2 implements FabricaNivel {
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

    //Zona de transicion del escenario inicial
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

    //Recorrido horizontal del aro del escenario inicial.
    private static final double ARO_LIMITE_IZQUIERDO = 420;
    private static final double ARO_LIMITE_DERECHO = 1130;
    private static final double ARO_VELOCIDAD_HORIZONTAL = 2.0;

    private static Integer monedasTotales;

    FabricaNivel2() {
    }

    //Comportamientos
    public Escenario crearEscenario(int id, Personaje personaje) {
        switch (id) {
            case ESCENARIO_1:
                return crearEscenario1(personaje);
            case ESCENARIO_2:
                return crearEscenario2(personaje);
            case ESCENARIO_3:
                return crearEscenario3(personaje);
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
        

    public int getMonedasTotales() {
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
        aro.configurarMovimientoHorizontal(ARO_LIMITE_IZQUIERDO, ARO_LIMITE_DERECHO, ARO_VELOCIDAD_HORIZONTAL);

        // Muro que bloquea el final del escenario hasta encestar en el aro.
        Muro muro = new Muro(MURO_X, MURO_Y, MURO_ANCHO, Configuracion.ALTO - MURO_Y);
        plataformas.add(muro);

        // Moneda apoyada sobre el muro: baja con el y queda en el suelo al abrirse.
        Moneda monedaMuro = new Moneda(0, 0, MONEDA_MURO_ANCHO, MONEDA_MURO_ALTO);
        muro.colocarMonedaEncima(monedaMuro);
        monedas.add(monedaMuro);

        return new Nivel(NUMERO_NIVEL, personaje, aro, enemigos, monedas, plataformas, recolectables, new ArrayList<Boton>(), muro);
    }

    private static Nivel crearContenidoEscenario2(Personaje personaje) {
        List<Plataforma> plataformas = new ArrayList<>();
        List<Moneda> monedas = new ArrayList<>();
        List<Enemigo> enemigos = new ArrayList<>();
        List<Recolectable> recolectables = new ArrayList<>();

        // Suelo del escenario
        plataformas.add(new Plataforma(0, Configuracion.SUELO_Y, Configuracion.ANCHO, 30));

        // Pelota de fuego
        recolectables.add(new Recolectable(new ArmaFuego(10, 12.0, 5, 0, 0, 16, 16),
                140, 780, 40, 40));

        //Plataformas
        plataformas.add(new Plataforma(0, 660, 300, 26, MaterialPlataforma.NORMAL, true));
        plataformas.add(new Plataforma(300, 660, 260, 26, MaterialPlataforma.MADERA, true));
        plataformas.add(new Plataforma(560, 660, 1040, 26, MaterialPlataforma.NORMAL, true));
        plataformas.add(new Plataforma(0, 500, 560, 26, MaterialPlataforma.NORMAL, true));
        plataformas.add(new Plataforma(560, 500, 260, 26, MaterialPlataforma.MADERA, true));
        plataformas.add(new Plataforma(820, 500, 780, 26, MaterialPlataforma.NORMAL, true));
        plataformas.add(new Plataforma(860, 420, 260, 26));
        plataformas.add(new Plataforma(600, 340, 260, 26));
        plataformas.add(new Plataforma(320, 260, 300, 26));
        plataformas.add(new Plataforma(0, 180, 220, 26));

        // Monedas
        monedas.add(new Moneda(300, 790, 30, 30));
        monedas.add(new Moneda(560, 630, 30, 30));
        monedas.add(new Moneda(860, 470, 30, 30));

        // Enemigos terrestres sobre las plataformas.
        enemigos.add(new EnemigoTerrestre(20, 44, 44, 2.0, 920, 456));
        enemigos.add(new EnemigoTerrestre(20, 44, 44, 2.0, 660, 296));
        enemigos.add(new EnemigoTerrestre(20, 44, 44, 2.1, 380, 216));

        // Enemigo a distancia
        EnemigoADistancia enemigoADistancia = new EnemigoADistancia(2000, 40, 44, 44, 0, 80, 136);
        enemigoADistancia.setObjetivo(personaje);
        enemigos.add(enemigoADistancia);

        // Este escenario no tiene aro
        return new Nivel(NUMERO_NIVEL, personaje, null, enemigos, monedas, plataformas, recolectables, new ArrayList<Boton>(), null);
    }

    private static Nivel crearContenidoEscenario3(Personaje personaje) {
        List<Plataforma> plataformas = new ArrayList<>();
        List<Moneda> monedas = new ArrayList<>();
        List<Enemigo> enemigos = new ArrayList<>();
        List<Recolectable> recolectables = new ArrayList<>();
        List<Boton> botones = new ArrayList<>();

        // Plataforma.
        plataformas.add(new Plataforma(0, Configuracion.SUELO_Y, Configuracion.ANCHO, 30));

        // Pelota de hielo.
        recolectables.add(new Recolectable(new ArmaHielo(10, 12.0, 5000, 0, 0, 16, 16),
                50, 780, 40, 40));

        //Enemigo volador y las plataformas
        plataformas.add(new Plataforma(0, 540, 240, 24));
        EnemigoVolador volador = new EnemigoVolador(20, 44, 44, 1.5, 300, 680);
        volador.setLimites(220, 480);
        enemigos.add(volador);

        plataformas.add(new Plataforma(0, 380, 720, 24));

        Muro muro = new Muro(380, 270, 70, 110);
        plataformas.add(muro);

        plataformas.add(new Plataforma(0, 250, 520, 20, MaterialPlataforma.NEGRA));

        // Boton verde.
        botones.add(new Boton(20, 200, 50, 50));

        plataformas.add(new Plataforma(820, 250, 150, 24));
        plataformas.add(new Plataforma(1020, 210, 150, 24));

        // Enemigo a distancia encerrado en un cuadrado.
        plataformas.add(new Plataforma(1390, 716, 100, 20, MaterialPlataforma.NORMAL, true));
        plataformas.add(new Plataforma(1390, 736, 20, 84, MaterialPlataforma.NORMAL, true));
        plataformas.add(new Plataforma(1470, 736, 20, 84, MaterialPlataforma.NORMAL, true));
        EnemigoADistancia enemigoADistancia = new EnemigoADistancia(2000, 40, 44, 44, 0, 1420, 776);
        enemigoADistancia.setObjetivo(personaje);
        enemigos.add(enemigoADistancia);

        // Moneda
        monedas.add(new Moneda(900, 220, 30, 30));

        // Aro del ultimo escenario, cerrado excepto por arriba.
        Aro aro = new Aro(1370, 300, 90, 70);
        plataformas.add(new Plataforma(1350, 300, 20, 90, MaterialPlataforma.NORMAL, true));
        plataformas.add(new Plataforma(1460, 300, 20, 90, MaterialPlataforma.NORMAL, true));
        plataformas.add(new Plataforma(1350, 370, 130, 20, MaterialPlataforma.NORMAL, true));
        return new Nivel(NUMERO_NIVEL, personaje, aro, enemigos, monedas, plataformas, recolectables, botones, muro);
    }

    public Personaje crearPersonajeInicial() {
        Personaje personaje = new Personaje(Personaje.VIDAS_INICIALES, 44, 64, 6.0, 120, 756);
        personaje.setArmaEquipada(new ArmaEstandar(10, 12.0, 0, 0, 16, 16));

        return personaje;
    }

}

