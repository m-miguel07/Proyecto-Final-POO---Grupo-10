package persistencia;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
            INSERT INTO puntajes (jugador, puntaje, tiempo, fecha)
            VALUES (?, ?, ?, ?)
            """;

        try (PreparedStatement ps = Conexion.getInstancia()
                .get().prepareStatement(sql)) {

            ps.setString(1, puntaje.getJugador());
            ps.setInt(2, puntaje.getPuntos());
            ps.setInt(3, puntaje.getTiempo());
            ps.setString(4, puntaje.getFecha());

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
            SELECT jugador, puntaje, tiempo, fecha
            FROM puntajes
            ORDER BY puntaje DESC
            LIMIT ?
            """;

        try (PreparedStatement ps = Conexion.getInstancia()
                .get().prepareStatement(sql)) {

            ps.setInt(1, cantidad);

            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Puntaje puntaje = new Puntaje(rs.getString("jugador"), 
                                                  rs.getInt("puntaje"), 
                                                  rs.getInt("tiempo"), 
                                                  rs.getString("fecha")
                                                );

                    resultados.add(puntaje);
                }
            }
            

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultados;
    }
}
