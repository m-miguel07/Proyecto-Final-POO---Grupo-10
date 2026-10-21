package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import modelo.Partida;
import modelo.PartidaDAO;

public class PartidaDAOSQLite implements PartidaDAO { //Almacena datos de partida: Ajustes de volumen, puntaje acumulado, nivel actual y tiempo acumulado

    private final Connection conexion;
    
    public PartidaDAOSQLite() throws SQLException {
        this.conexion = Conexion.getInstancia().get();
    }
    
    @Override
    public boolean guardar(Partida partida){
        String sql = """
            INSERT OR REPLACE INTO partidas_guardadas
            (id, puntaje_acumulado, nivel_actual, tiempo_acumulado,
             volumen_sfx, volumen_bgm)
            VALUES (1, ?, ?, ?, ?, ?)
            """;

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, partida.getPuntajeAcumulado());
            ps.setInt(2, partida.getNivelActual());
            ps.setLong(3, partida.getTiempoAcumulado() / 1000);
            ps.setDouble(4, partida.getVolumenSFX());
            ps.setDouble(5, partida.getVolumenBGM());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("No se pudo guardar la partida: "); 
            e.printStackTrace();
            return false;
        }
    }

    @Override 
    public Partida cargar(){
        String sql = """
            SELECT puntaje_acumulado, nivel_actual, tiempo_acumulado,
                   volumen_sfx, volumen_bgm
            FROM partidas_guardadas
            WHERE id = 1
            """;

        try (PreparedStatement ps = conexion.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return new Partida(
                    rs.getInt("puntaje_acumulado"),
                    rs.getInt("nivel_actual"),
                    rs.getLong("tiempo_acumulado"),
                    rs.getFloat("volumen_sfx"),
                    rs.getFloat("volumen_bgm")
                );
            }

        } catch (SQLException e) {
            System.out.println("No se pudieron cargar los datos guardados: ");
            e.printStackTrace();
        }

        return null;

    }

    @Override 
    public boolean eliminar(){
        String sql = "DELETE FROM partidas_guardadas WHERE id = 1";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println ("No se pudieron eliminar los datos guardados: ");
            e.printStackTrace();
            return false;
        }

    }
}
