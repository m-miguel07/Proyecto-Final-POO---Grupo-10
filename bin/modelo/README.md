# Descripción modelo 

## Herencia
### Clases padre
#### Abstractas
1. Entidad : Define PS y velocidad
2. Arma : Inflinge daño a entidades, define daño base, alcance y velocidad
3. Enemigo : Hija de Entidad, define daño base, ataca entidades. 
### Clases hija
1. Personaje : Hija de Entidad, controlado por el jugador, define arma equipada, monedas recolectadas y puntaje.
3. ArmaEstandar : Hija de Arma, arma base. 
4. ArmaFuego : Hija de Arma, define daño por quemadura. 
5. ArmaHielo : Hija de Arma, define duración de Ralentización.
6. EnemigoTerrestre : Hija de Enemigo, movimiento a pie. 
7. EnemigoADistancia : Hija de Enemigo, ataques a larga distancia.
8. EnemigoVolador : Hija de Enemigo, movimiento aereo. 

## Polimorfismo 
1.  Clase abstracta 'Entidad': Define un metodo abstracto *atacar()*. Sus clases hijas lo ejecutan de la siguiente manera:
+ Enemigo : Inflinge el daño base al objetivo, idem clases hijas.
+ Personaje : Inflinge un valor de daño, determinado según el arma actual equipada. 

2. Clase abstracta 'Arma': Define un método abstracto *aplicarDanio()*. Sus clases hijas lo ejecutan de la siguiente manera:

+ ArmaEstandar : Aplica daño base
+ ArmaHielo    : Aplica ralentización y congelación dado n golpes
+ ArmaFuego    : Aplica quemadura 



