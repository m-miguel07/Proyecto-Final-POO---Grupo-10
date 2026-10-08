package modelo;

public enum MaterialPlataforma {

    NORMAL(false),
    MADERA(true);

    private final boolean destructible;

    MaterialPlataforma(boolean destructible) {
        this.destructible = destructible;
    }

    //Comportamientos
    public boolean esDestructible() {
        return this.destructible;
    }
}