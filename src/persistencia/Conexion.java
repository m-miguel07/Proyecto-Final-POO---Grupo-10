
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
}
