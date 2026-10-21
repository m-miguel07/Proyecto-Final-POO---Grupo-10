
package persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    private static Conexion instancia;
    private final Connection conexion; 


    private Conexion(){
        try {
            this.conexion = DriverManager.getConnection("jdbc:sqlite:BF.db");
        } catch (SQLException e){
            throw new IllegalStateException("No se pudo abrir la base: " + e.getMessage());
        }
        
    }

    public static Conexion getInstancia() throws SQLException {
        if (instancia == null){
            instancia = new Conexion();
        }
        return instancia;
    }

    public Connection get() throws SQLException {
        return conexion;
    }

    public void inicializarTablas() throws SQLException {
        String sql = """
        CREATE TABLE IF NOT EXISTS partidas_guardadas (
            id INTEGER PRIMARY KEY CHECK (id = 1),
            puntaje_acumulado INTEGER NOT NULL,
            nivel_actual INTEGER NOT NULL,
            tiempo_acumulado INTEGER NOT NULL,
            volumen_sfx REAL NOT NULL,
            volumen_bgm REAL NOT NULL
        )
        """;

        String sql2 = """
        CREATE TABLE IF NOT EXISTS puntajes (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            jugador VARCHAR(10) NOT NULL,
            puntaje INTEGER NOT NULL,
            tiempo INTEGER NOT NULL,
            fecha  DATETIME NOT NULL
        )
        """;

        try (var st = conexion.createStatement()) {
            st.executeUpdate(sql);
            st.executeUpdate(sql2);
        }
    }
}
