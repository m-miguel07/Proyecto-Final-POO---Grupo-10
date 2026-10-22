package modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Nivel {

    private int numeroNivel;

    private Personaje personaje;
    private final Aro aro;
    private final Muro muro;
    private final List<Enemigo> enemigos;
    private final List<Moneda> monedas;
    private final List<Plataforma> plataformas;
    private final List<Recolectable> recolectables;
    private final List<Boton> botones;

    private final List<Enemigo> enemigosVista;
    private final List<Moneda> monedasVista;
    private final List<Plataforma> plataformasVista;
    private final List<Recolectable> recolectablesVista;
    private final List<Boton> botonesVista;

    private final GestorPelotas gestorPelotas;
    private final SistemaColisiones sistemaColisiones;
    private final Marcador marcador;

    //Constructor
    public Nivel(int numeroNivel, Personaje personaje, Aro aro, List<Enemigo> enemigos, List<Moneda> monedas,
                 List<Plataforma> plataformas, List<Recolectable> recolectables, List<Boton> botones, Muro muro) {
        if (personaje == null) {
            throw new IllegalArgumentException("El personaje es obligatorio");
        }

        this.numeroNivel = numeroNivel;
        this.personaje = personaje;
        this.aro = aro;
        this.muro = muro;
        this.enemigos = new ArrayList<>(enemigos);
        this.monedas = new ArrayList<>(monedas);
        this.plataformas = new ArrayList<>(plataformas);
        this.recolectables = new ArrayList<>(recolectables);
        this.botones = botones == null ? new ArrayList<>() : new ArrayList<>(botones);

        this.enemigosVista = Collections.unmodifiableList(this.enemigos);
        this.monedasVista = Collections.unmodifiableList(this.monedas);
        this.plataformasVista = Collections.unmodifiableList(this.plataformas);
        this.recolectablesVista = Collections.unmodifiableList(this.recolectables);
        this.botonesVista = Collections.unmodifiableList(this.botones);

        this.gestorPelotas = new GestorPelotas();
        this.sistemaColisiones = new SistemaColisiones();
        this.marcador = new Marcador();
    }

    //Getters
    public int getNumeroNivel(){
        return this.numeroNivel;
    }
    public Personaje getPersonaje() {
        return this.personaje;
    }

    public Aro getAro() {
        return this.aro;
    }

    public Muro getMuro() {
        return this.muro;
    }

    public List<Enemigo> getEnemigos() {
        return this.enemigosVista;
    }

    public List<Moneda> getMonedas() {
        return this.monedasVista;
    }

    public List<Plataforma> getPlataformas() {
        return this.plataformasVista;
    }

    public List<Recolectable> getRecolectables() {
        return this.recolectablesVista;
    }

    public List<Boton> getBotones() {
        return this.botonesVista;
    }

    public List<Pelota> getPelotas() {
        return this.gestorPelotas.getPelotas();
    }

    //Comportamientos
    public void actualizar() {
        this.sistemaColisiones.verificarMonedas(this.personaje, this.monedas);
        this.sistemaColisiones.verificarEnemigos(this.personaje, this.enemigos);

        if (this.aro != null) {
            this.aro.actualizar();
        }

        if (this.muro != null) {
            if ((this.aro != null && this.aro.isEncestado()) || this.hayBotonActivado()) {
                this.muro.abrir();
            }

            this.muro.actualizar();
        }
    }

    private boolean hayBotonActivado() {
        for (Boton boton : this.botones) {
            if (boton.isActivado()) {
                return true;
            }
        }

        return false;
    }

    public boolean estaCompleto() {
        return this.aro != null && this.aro.isEncestado() && this.aro.esMeta();
    }

    public int contarMonedasRecolectadas() {
        return this.marcador.contarMonedasRecolectadas(this.monedas);
    }

    public int getMonedasTotales() {
        return this.marcador.contarMonedasTotales(this.monedas);
    }

    public void agregarPelota(Pelota pelota) {
        this.gestorPelotas.agregar(pelota);
    }

    public void actualizarPelotas(int anchoPantalla, int altoPantalla, int deltaMS) {
        this.gestorPelotas.actualizar(this.aro, this.personaje, this.enemigos, this.plataformas, this.botones,
                anchoPantalla, altoPantalla, deltaMS);
    }

    public void eliminarEnemigosDerrotados() {
        this.sistemaColisiones.eliminarEnemigosDerrotados(this.personaje, this.enemigos);
    }

    public void eliminarRecolectados() {
        this.sistemaColisiones.eliminarRecolectados(this.personaje, this.recolectables);
    }
}
