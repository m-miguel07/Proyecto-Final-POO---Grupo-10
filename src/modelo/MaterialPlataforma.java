package modelo;

public enum MaterialPlataforma {

    NORMAL(false, false),
    MADERA(true, false),
    NEGRA(false, true);

    private final boolean destructible;
    private final boolean atravesablePorPelota;

    MaterialPlataforma(boolean destructible, boolean atravesablePorPelota) {
        this.destructible = destructible;
        this.atravesablePorPelota = atravesablePorPelota;
    }

    //Comportamientos
    public boolean esDestructible() {
        return this.destructible;
    }

    public boolean permitePasarPelota() {
        return this.atravesablePorPelota;
    }
}
