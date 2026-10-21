package modelo;

import java.util.List;

public class SistemaColisiones {

    //Constantes
    private static final int PUNTAJE_MONEDA = 100;
    private static final int PUNTAJE_ENEMIGO = 150;

    //Comportamientos
    public void verificarMonedas(Personaje personaje, List<Moneda> monedas) {
        for (Moneda moneda : monedas) {
            if (!moneda.isRecolectado() && personaje.interseca(moneda)) {
                moneda.recolectar();
                personaje.sumarMoneda();
                personaje.sumarPuntaje(PUNTAJE_MONEDA);
                GestorAudio.getInstancia().reproducirEfecto(GestorAudio.MONEDA);
            }
        }
    }

    public void verificarEnemigos(Personaje personaje, List<Enemigo> enemigos) {
        if (personaje.estaInvulnerable()) {
            return;
        }

        for (Enemigo enemigo : enemigos) {
            if (enemigo.estaCongelado()) {
                continue;
            }

            if (enemigo.puedeAtacar(personaje)) {
                personaje.perderVida();
                break;
            }
        }
    }

    public void eliminarEnemigosDerrotados(Personaje personaje, List<Enemigo> enemigos) {
        enemigos.removeIf(enemigo -> {
            if (enemigo.getPuntosVida() <= 0) {
                personaje.sumarPuntaje(PUNTAJE_ENEMIGO);
                return true;
            }
            return false;
        });
    }

    public void eliminarRecolectados(Personaje personaje, List<Recolectable> recolectables) {
        for (Recolectable recolectable : recolectables) {
            if (recolectable.isRecolectado()) {
                continue;
            }

            if (personaje.interseca(recolectable)) {
                recolectable.recolectar();
                personaje.equiparArmaTemporal(recolectable.getArma());
                GestorAudio.getInstancia().reproducirEfecto(GestorAudio.AGARRE_PELOTA);
            }
        }
    }
}
