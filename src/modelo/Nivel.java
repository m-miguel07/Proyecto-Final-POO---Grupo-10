package modelo;

// Archivo Nivel.java
// Vendria a contener todo lo que existe en un nivel. (Enemigos, Personaje, Monedas, Plataformas, Aros, etc)

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Nivel {

    private final Personaje personaje;
    private final Aro aro;
    private final List<Enemigo> enemigos;
    private final List<Moneda> monedas;
    private final List<Plataforma> plataformas;

    //Constructor
    public Nivel(Personaje personaje, Aro aro, List<Enemigo> enemigos, List<Moneda> monedas, List<Plataforma> plataformas) {
        if (personaje == null || aro == null) {
            throw new IllegalArgumentException("El personaje y el aro son obligatorios");
        }

        this.personaje = personaje;
        this.aro = aro;
        this.enemigos = new ArrayList<>(enemigos);
        this.monedas = new ArrayList<>(monedas);
        this.plataformas = new ArrayList<>(plataformas);
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

    //Comportamientos
    public void actualizar() {
        this.chequearMonedas();
        this.chequearEnemigos();
    }

    public boolean estaCompleto() {
        return this.aro.isEncestado();
    }

    public int contarMonedasRecolectadas() {
        int cantidad = 0;
        for (Moneda moneda : this.monedas) {
            if (moneda.isRecolectada()) {
                cantidad++;
            }
        }
        return cantidad;
    }

    public boolean hayInterseccionConPlataforma(double posicionX, double posicionY) {
        for (Plataforma plataforma : this.plataformas) {
            if (this.colisionaConRectangulo(posicionX, posicionY, plataforma)) {
                return true;
            }
        }
        return false;
    }

    public boolean hayInterseccionConEnemigo(double posicionX, double posicionY) {
        for (Enemigo enemigo : this.enemigos) {
            if (this.colisionaConRectangulo(posicionX, posicionY, enemigo)) {
                return true;
            }
        }
        return false;
    }

    private boolean colisionaConRectangulo(double posicionX, double posicionY, ObjetoDelJuego objeto) {
        return Colisiones.hayInterseccion(
                posicionX, posicionY, this.personaje.getAncho(), this.personaje.getAlto(),
                objeto.getPosicionX(), objeto.getPosicionY(), objeto.getAncho(), objeto.getAlto());
    }

    private void chequearMonedas() {
        for (Moneda moneda : this.monedas) {
            if (!moneda.isRecolectada()
                    && this.colisionaConRectangulo(this.personaje.getPosicionX(), this.personaje.getPosicionY(), moneda)) {
                moneda.recolectar();
            }
        }
    }

    private void chequearEnemigos() {
        for (Enemigo enemigo : this.enemigos) {
            if (enemigo.puedeAtacar(this.personaje)) {
                enemigo.atacar(this.personaje);
            }
        }
    }
}