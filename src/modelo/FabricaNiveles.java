package modelo;

import java.util.Map;

public final class FabricaNiveles {

    private static final Map<Integer, FabricaNivel> FABRICAS = Map.of(
            1, new FabricaNivel1(),
            2, new FabricaNivel2());

    private FabricaNiveles() {
    }

    public static FabricaNivel para(int numeroNivel) {
        FabricaNivel fabrica = FABRICAS.get(numeroNivel);

        if (fabrica == null) {
            throw new IllegalArgumentException("Nivel no soportado: " + numeroNivel);
        }

        return fabrica;
    }
}
