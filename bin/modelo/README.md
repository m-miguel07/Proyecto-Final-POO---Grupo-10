# Descripción modelo 

## Herencia
### Clases padre
#### Abstractas
1. ObjetoDelJuego : Define ancho, alto, posición X/Y y el método interseca().
2. Entidad : Hija de ObjetoDelJuego. Define puntos de vida (PS) y velocidad.
3. Arma : Hija de ObjetoDelJuego. Define daño base y velocidad. Declara el método abstracto aplicarDanio().
4. Enemigo : Hija de Entidad. Define daño base, los estados de congelación y quemadura, y puedeAtacar().
5. Recogible : Hija de ObjetoDelJuego. Define el estado recolectado y el método recolectar().
### Clases hija
1. Personaje : Hija de Entidad. Controlado por el jugador; define arma equipada, monedas recolectadas y puntaje.
2. ArmaEstandar : Hija de Arma. Arma base; aplica el daño base.
3. ArmaFuego : Hija de Arma. Aplica el daño base y quema al enemigo impactado.
4. ArmaHielo : Hija de Arma. Aplica el daño base y congela al enemigo impactado.
5. EnemigoTerrestre : Hija de Enemigo. Movimiento a pie.
6. EnemigoADistancia : Hija de Enemigo. Ataca según su alcance (a distancia).
7. EnemigoVolador : Hija de Enemigo. Movimiento aéreo.
8. Moneda : Hija de Recogible. Se recolecta para sumar puntaje.
9. Recolectable : Hija de Recogible. Otorga un arma temporal al recolectarse.
10. Plataforma : Hija de ObjetoDelJuego. Superficie sólida; puede ser normal o de madera (destructible).
11. Pelota : Hija de ObjetoDelJuego. Proyectil que contiene un Arma y tiene física propia.
12. Aro : Hija de ObjetoDelJuego. Objetivo final; al encestar se completa el nivel.

## Polimorfismo 

1. Clase abstracta 'Arma': Define el método abstracto *aplicarDanio(Entidad)*. Sus clases hijas lo implementan:

+ ArmaEstandar : Aplica el daño base.
+ ArmaHielo    : Aplica el daño base y congela al enemigo impactado.
+ ArmaFuego    : Aplica el daño base y quema al enemigo impactado.

2. Clase 'Enemigo': Define *puedeAtacar(ObjetoDelJuego)*. Sus clases hijas pueden redefinirlo:

+ EnemigoTerrestre / EnemigoVolador : Usan la detección cuerpo a cuerpo heredada.
+ EnemigoADistancia                 : Lo redefine para atacar según su alcance (a distancia).



