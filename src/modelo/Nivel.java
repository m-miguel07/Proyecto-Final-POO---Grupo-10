package modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Nivel {

    private Personaje personaje;
    private final Aro aro;
    private final List<Enemigo> enemigos;
    private final List<Moneda> monedas;
    private final List<Plataforma> plataformas;
    private final List<Recolectable> recolectables;

    private final GestorPelotas gestorPelotas;
    private final SistemaColisiones sistemaColisiones;
    private final Marcador marcador;

    //Constructor
    public Nivel(Personaje personaje, Aro aro, List<Enemigo> enemigos, List<Moneda> monedas,
                 List<Plataforma> plataformas, List<Recolectable> recolectables) {
        if (personaje == null || aro == null) {
            throw new IllegalArgumentException("El personaje y el aro son obligatorios");
        }

        this.personaje = personaje;
        this.aro = aro;
        this.enemigos = new ArrayList<>(enemigos);
        this.monedas = new ArrayList<>(monedas);
        this.plataformas = new ArrayList<>(plataformas);
        this.recolectables = new ArrayList<>(recolectables);

        this.gestorPelotas = new GestorPelotas();
        this.sistemaColisiones = new SistemaColisiones();
        this.marcador = new Marcador();
    }

    //Getters
    public Personaje getPersonaje() {
        return this.personaje;
    }

    public Aro getAro() {
        return this.aro;
    }

    public List<Enemigo> getEnemigos() {
        return Collections.unmodifiableList(this.enemigos);
    }

    public List<Moneda> getMonedas() {
        return Collections.unmodifiableList(this.monedas);
    }

    public List<Plataforma> getPlataformas() {
        return Collections.unmodifiableList(this.plataformas);
    }

    public List<Recolectable> getRecolectables() {
        return Collections.unmodifiableList(this.recolectables);
    }

    public List<Pelota> getPelotas() {
        return this.gestorPelotas.getPelotas();
    }


    //Setters
    public void setPersonaje(Personaje personaje){ //Para manejar el traspaso entre escenarios, conservando el estado actual
        this.personaje = personaje;
    }
    //Comportamientos
    public void actualizar() {
        this.sistemaColisiones.verificarMonedas(this.personaje, this.monedas);
        this.sistemaColisiones.verificarEnemigos(this.personaje, this.enemigos);
    }

    public boolean estaCompleto() {
        return this.aro.isEncestado();
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

    public void actualizarPelotas(int anchoPantalla, int altoPantalla) {
        this.gestorPelotas.actualizar(this.aro, this.personaje, this.enemigos, this.plataformas,
                anchoPantalla, altoPantalla);
    }

    public void eliminarEnemigosDerrotados() {
        this.sistemaColisiones.eliminarEnemigosDerrotados(this.personaje, this.enemigos);
    }

    public void eliminarRecolectados() {
        this.sistemaColisiones.eliminarRecolectados(this.personaje, this.recolectables);
    }
}
