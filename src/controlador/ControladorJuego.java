package controlador;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.List;
import javax.swing.Timer;

import modelo.Arma;
import modelo.Configuracion;
import modelo.Enemigo;
import modelo.Muro;
import modelo.FabricaNivel;
import modelo.FabricaNiveles;
import modelo.GestorAudio;
import modelo.Nivel;
import modelo.Partida;
import modelo.Pelota;
import modelo.Personaje;
import modelo.Plataforma;
import modelo.ZonaTransicion;
import modelo.Escenario;

import vista.PanelJuego;

public class ControladorJuego {

    private ControladorPrincipal controladorPrincipal;

    //Constantes
    private static final int INTERVALO_TIMER_MS = 16;
    private static final long DELTA_MAXIMO_MS = 50;
    private static final long COOLDOWN_DISPARO_MS = 2000;

    //Visual
    private final PanelJuego panel;
    private Escenario escenarioActual;
    private FabricaNivel fabricaNivel;
    private EstadoJuego estado;

    //Guardado
    private Partida partida;
    //Audio
    private final GestorAudio gestorAudio;

    //Gameloop
    private final Timer timer;
    private long tiempoTranscurridoMS;
    private long ultimoDisparoMS = -COOLDOWN_DISPARO_MS;
    private long ultimoPasoNanos;

    //Desplazamiento y apuntado
    private int mouseX = -1;
    private int mouseY = -1;

    private boolean teclaIzquierda;
    private boolean teclaDerecha;
    private boolean teclaSaltar;
    private boolean saltoPendiente;
    private boolean teclaDisparo;

    //Constructor
    public ControladorJuego(Partida partida, ControladorPrincipal controladorPrincipal) {

        this.controladorPrincipal = controladorPrincipal;

        //Persistencia
        this.partida = partida;

        //logica de generación de nivel
        this.fabricaNivel = FabricaNiveles.para(this.partida.getNivelActual());
        this.escenarioActual = this.fabricaNivel.crearEscenario(1, this.fabricaNivel.crearPersonajeInicial());

        if (this.partida.getNivelActual() == 2) {
            this.escenarioActual.getNivel().getPersonaje().setPuntaje(partida.getPuntajeAcumulado());
        }

        this.panel = new PanelJuego(escenarioActual);
        if (this.partida.getNivelActual() == 2) {
            this.panel.setTiempoTranscurrido(partida.getTiempoAcumulado());
        }

        //Gameloop
        this.timer = new Timer(INTERVALO_TIMER_MS, e -> this.paso());
        this.tiempoTranscurridoMS = 0;
        this.ultimoPasoNanos = System.nanoTime();

        //Audio
        this.gestorAudio = GestorAudio.getInstancia();
        
        //Panel pausa
        panel.getPanelPausa().getBotonSalir().addActionListener(e -> {
            pausar();
        });

        panel.getPanelPausa().getBotonReiniciar().addActionListener(e ->{
            reiniciar();
        });

        panel.getPanelPausa().getBotonSalida().addActionListener(e ->{
            salirAlMenu();
        });

        //Paneles de fin de nivel (Perdiste | Ganaste)
        panel.getPanelDerrota().getBoton().addActionListener(e ->{
            reiniciar();
            
        });

        panel.getPanelDerrota().getBotonSalir().addActionListener(e -> {
            salirAlMenu();
        });

        panel.getPanelVictoria().getBoton().addActionListener(e ->{
            pasarDeNivel();
            
        });

        panel.getPanelVictoria().getBotonSalir().addActionListener(e -> {
            salirAlMenu();
        });

        this.registrarTeclado();
        this.registrarMouse();
        this.panel.setMensaje("NIVEL 1");
    }

    //Getters
    public PanelJuego getPanel() {
        return this.panel;
    }

    public Nivel getNivel() {
        return this.panel.getNivel();
    }

    //Comportamientos

    //Persistencia
    public void guardarPartida(){ //Actualiza el objeto partida, para ser guardado posteriormente por el partidaDAO
        Nivel nivelActual = this.escenarioActual.getNivel();

        partida.setNivelActual(nivelActual.getNumeroNivel());
        partida.setPuntaje(nivelActual.getPersonaje().getPuntaje());
        partida.setTiempo(panel.getTiempoTranscurrido());
    }

    //Audio
    private void iniciarMusica(){
        gestorAudio.reproducirMusica("src/assets/Musica/musica_fondo_1.mp3", true);
    }

    //Gameloop
    public void salirAlMenu(){
        detener();
        gestorAudio.detenerMusica();
        guardarPartida();
        this.panel.ocultarPaneles();
        reiniciarEstado();
        this.estado = null;
        
        this.controladorPrincipal.volverAlMenu(); //notifica que se solicito la salida
    }

    public void iniciar() {
        this.teclaIzquierda = false;
        this.teclaDerecha = false;
        this.teclaSaltar = false;
        this.saltoPendiente = false;
        this.teclaDisparo = false;
        this.ultimoPasoNanos = System.nanoTime();

        this.cambiarEstado(EstadoJugando.INSTANCIA);
    }

    public void detener() {
        this.timer.stop();
        gestorAudio.pausarMusica();
    }

    public void pausar(){
        if (this.estado != null) {
            this.estado.pausar(this);
        }
    }

    public void reiniciar() {
        this.detener();
        gestorAudio.detenerMusica();

        reiniciarEstado();

        iniciarMusica();

        this.cambiarEstado(EstadoJugando.INSTANCIA);
    }

    private void reiniciarEstado() {
        this.teclaIzquierda = false;
        this.teclaDerecha = false;
        this.teclaSaltar = false;
        this.saltoPendiente = false;
        this.teclaDisparo = false;

        Personaje personaje = this.fabricaNivel.crearPersonajeInicial();
        this.escenarioActual = this.fabricaNivel.crearEscenario(1, personaje);

        if (this.partida.getNivelActual() == 2) {
            this.panel.setTiempoTranscurrido(this.partida.getTiempoAcumulado());
        } else {
            this.panel.setTiempoTranscurrido(0);
        }

        panel.escenarioCambiado(this.escenarioActual);

        this.tiempoTranscurridoMS = 0;
        this.ultimoDisparoMS = -COOLDOWN_DISPARO_MS;
        this.ultimoPasoNanos = System.nanoTime();
        
        this.panel.setMensaje("NIVEL 1");
    }

    private void pasarDeNivel(){ 
        this.partida.setNivelActual(2);
        this.fabricaNivel = FabricaNiveles.para(2);
        reiniciar();
    }

    void cambiarEstado(EstadoJuego nuevoEstado) {
        if (this.estado != null) {
            this.estado.salir(this);
        }

        this.estado = nuevoEstado;

        if (this.estado != null) {
            this.estado.entrar(this);
        }
    }

    void iniciarTimer() {
        this.timer.start();
    }

    void detenerTimer() {
        this.timer.stop();
    }

    void pausarMusica() {
        this.gestorAudio.pausarMusica();
    }

    void reanudarMusica() {
        this.gestorAudio.reanudarMusica();
    }

    void mostrarPausa(boolean visible) {
        this.panel.mostrarPausa(visible);
    }

    void mostrarDerrota(boolean visible) {
        this.panel.mostrarDerrota(visible);
    }

    void mostrarVictoria(boolean visible) {
        this.panel.mostrarVictoria(visible);
    }

    void ocultarPaneles() {
        this.panel.ocultarPaneles();
    }

    //Desplazamiento
    private void registrarTeclado() {
        this.panel.setFocusTraversalKeysEnabled(false);
        this.panel.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                int codigo = e.getKeyCode();

                if (codigo == KeyEvent.VK_R) {
                    ControladorJuego.this.reiniciar();
                    return;
                }

                if (!getNivel().getPersonaje().estaVivo() || getNivel().estaCompleto()) {
                    return;
                }

                if (codigo == KeyEvent.VK_LEFT || codigo == KeyEvent.VK_A) {
                    teclaIzquierda = true;
                } else if (codigo == KeyEvent.VK_RIGHT || codigo == KeyEvent.VK_D) {
                    teclaDerecha = true;
                } else if (codigo == KeyEvent.VK_SPACE || codigo == KeyEvent.VK_W || codigo == KeyEvent.VK_UP) {
                    if (!teclaSaltar) {
                        saltoPendiente = true;
                    }
                    teclaSaltar = true;
                } else if (codigo == KeyEvent.VK_J) {
                    if (!teclaDisparo) {
                        teclaDisparo = true;
                        ControladorJuego.this.disparar();
                    }
                }

                if (codigo == KeyEvent.VK_ESCAPE){
                    pausar();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                int codigo = e.getKeyCode();

                if (codigo == KeyEvent.VK_LEFT || codigo == KeyEvent.VK_A) {
                    teclaIzquierda = false;
                } else if (codigo == KeyEvent.VK_RIGHT || codigo == KeyEvent.VK_D) {
                    teclaDerecha = false;
                } else if (codigo == KeyEvent.VK_SPACE || codigo == KeyEvent.VK_W || codigo == KeyEvent.VK_UP) {
                    teclaSaltar = false;
                } else if (codigo == KeyEvent.VK_J) {
                    teclaDisparo = false;
                }
            }
        });
    }

    private void registrarMouse() {
        this.panel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();
            }
        });

        this.panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    if (estado != null && estado.permiteDisparar()) {
                        mouseX = e.getX();
                        mouseY = e.getY();
                        ControladorJuego.this.disparar();
                    }
                }
            }
        });
    }

    //Mecanicas de juego
    private void disparar() {
        Personaje personaje = this.getNivel().getPersonaje();

        if (this.estado == null || !this.estado.permiteDisparar()) {
            return;
        }

        if (!personaje.estaVivo() || this.getNivel().estaCompleto()) {
            return;
        }

        // Cooldown de 2 segundos entre disparos
        if (this.tiempoTranscurridoMS - this.ultimoDisparoMS < COOLDOWN_DISPARO_MS) {
            return;
        }

        Arma arma = personaje.getArmaEquipada();

        if (arma == null) {
            return;
        }

        int centroX = (int) (personaje.getPosicionX() + personaje.getAncho() / 2);
        int centroY = (int) (personaje.getPosicionY() + personaje.getAlto() / 2);

        double vx;
        double vy;

        if (this.mouseX >= 0 && this.mouseY >= 0) {
            // Apuntado con el mouse
            double dx = this.mouseX - centroX;
            double dy = this.mouseY - centroY;
            double distancia = Math.hypot(dx, dy);

            if (distancia < 1.0) {
                distancia = 1.0;
                dx = personaje.getDireccion();
                dy = 0;
            }

            // Orientar al personaje hacia el objetivo
            if (dx > 0) {
                personaje.setDireccion(1);
            } else if (dx < 0) {
                personaje.setDireccion(-1);
            }

            double velocidadTiro = Math.max(arma.getVelocidad(), 16.0);
            vx = (dx / distancia) * velocidadTiro;
            vy = (dy / distancia) * velocidadTiro;
        } else {
            // Tiro por defecto hacia adelante
            vx = arma.getVelocidad() * personaje.getDireccion();
            vy = -12.0; 
        }

        Pelota pelota = new Pelota(
                arma,
                centroX - 8,
                centroY - 8,
                16, 16,
                vx,
                vy);

        this.getNivel().agregarPelota(pelota);
        this.ultimoDisparoMS = this.tiempoTranscurridoMS;
        gestorAudio.reproducirEfecto(GestorAudio.PELOTA_LANZAMIENTO);
    }

    private int calcularDeltaMS() {
        long ahora = System.nanoTime();
        long transcurridoMS = (ahora - this.ultimoPasoNanos) / 1_000_000L;
        this.ultimoPasoNanos = ahora;

        return (int) Math.max(1L, Math.min(transcurridoMS, DELTA_MAXIMO_MS));
    }

    private void paso() {
        if (this.estado == null || !this.estado.permiteJugar()) {
            return;
        }

        int deltaMS = this.calcularDeltaMS();

        Nivel nivel = this.getNivel();
        Personaje personaje = nivel.getPersonaje();

        if (!personaje.estaVivo()) {
            this.cambiarEstado(EstadoDerrota.INSTANCIA);
            this.panel.repaint();
            return;
        }

        if (nivel.estaCompleto()) {
            if (nivel.getNumeroNivel() == 1) {
                this.cambiarEstado(EstadoVictoria.INSTANCIA);
            } else {
                this.cambiarEstado(EstadoJuegoCompletado.INSTANCIA);
            }

            this.panel.repaint();
            return;
        }

        // Solo sumamos tiempo si el juego está activo
        this.tiempoTranscurridoMS += deltaMS;
        this.panel.setTiempoTranscurrido(this.tiempoTranscurridoMS);

        List<Plataforma> plataformas = nivel.getPlataformas();

        int direccion = 0;
        if (this.teclaIzquierda && !this.teclaDerecha) {
            direccion = -1;
        } else if (this.teclaDerecha && !this.teclaIzquierda) {
            direccion = 1;
        }

        personaje.actualizarHorizontal(direccion, Configuracion.ANCHO);

        //El muro del escenario inicial bloquea el paso hasta encestar en el aro de basquet
        Muro muro = nivel.getMuro();
        if (muro != null) {
            muro.bloquearPersonaje(personaje);
        }

        if (this.saltoPendiente) {
            personaje.saltar();
            this.saltoPendiente = false;
        }

        personaje.aplicarGravedad();
        personaje.actualizarVertical(plataformas, nivel.getEnemigos(), Configuracion.ALTO);
        detectarTransicion(personaje);

        personaje.actualizarArmaTemporal(deltaMS);

        for (Enemigo enemigo : nivel.getEnemigos()) {
            enemigo.actualizarEstados(deltaMS);
            enemigo.actualizar(plataformas, Configuracion.ANCHO, muro);
        }

        nivel.actualizar();
        nivel.eliminarRecolectados();
        nivel.eliminarEnemigosDerrotados();
        nivel.actualizarPelotas(Configuracion.ANCHO, Configuracion.ALTO, deltaMS);

        this.panel.setMensaje("");
        this.panel.repaint();
    }

    //Logica de cambio de escenario
    private void detectarTransicion(Personaje personaje){
        for (ZonaTransicion zona : escenarioActual.getZonasTransicion()){
            if (zona.contiene(personaje.getPosicionX(),personaje.getPosicionY())){
                Escenario destino = this.fabricaNivel.crearEscenario(zona.getIdEscenarioDestino(), personaje);
               
                if (destino == null) {
                    return;
                }

                cambiarEscenario(destino, zona.getSpawnX(), zona.getSpawnY(), personaje);
                return;
            }
        }
    }

    private void cambiarEscenario(Escenario nuevo, int x, int y, Personaje personaje){
        personaje.setPosicion(x, y);
        this.escenarioActual = nuevo;
        this.panel.escenarioCambiado(nuevo);
    }

}