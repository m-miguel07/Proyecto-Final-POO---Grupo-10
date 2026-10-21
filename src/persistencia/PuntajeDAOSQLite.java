package persistencia;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.Puntaje;
import modelo.PuntajeDAO;

public class PuntajeDAOSQLite implements PuntajeDAO {

    //Conexion verifica la existencia de la tabla puntaje en inicializarTablas()

    @Override
    public boolean guardar(Puntaje puntaje) {
        if (puntaje == null) {
            return false;
        }

        String sql = """
            INSERT INTO puntajes (jugador, tiempo_acumulado)
            VALUES (?, ?)
            """;

        try (PreparedStatement ps = Conexion.getInstancia()
                .get().prepareStatement(sql)) {

            ps.setInt(1, puntaje.getPuntos());
            ps.setLong(2, puntaje.getTiempo());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Puntaje> mejores(int cantidad) {
        List<Puntaje> resultados = new ArrayList<>();

        if (cantidad <= 0) {
            return resultados;
        }

        String sql = """
            SELECT puntos, tiempo_acumulado
            FROM puntajes
            ORDER BY puntos DESC
            LIMIT ?
            """;

        try (PreparedStatement ps = Conexion.getInstancia()
                .get().prepareStatement(sql)) {

            ps.setInt(1, cantidad);

            /* 
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Puntaje puntaje = new Puntaje(
                        rs.getInt("puntos"),
                        rs.getLong("tiempo_acumulado")
                    );

                    resultados.add(puntaje);
                }
            }
            */

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultados;
    }
}
