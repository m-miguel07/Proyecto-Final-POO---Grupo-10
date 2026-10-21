import controlador.ControladorPrincipal;
import persistencia.Conexion;

import java.sql.SQLException;

import javax.swing.SwingUtilities;

public class App {
    public static void main(String[] args) throws Exception {
        try {
            Conexion conexion = Conexion.getInstancia();
            conexion.inicializarTablas();

            SwingUtilities.invokeLater(() -> {
                try {
                    new ControladorPrincipal(); 
                } catch (SQLException e){
                    e.printStackTrace();
                }
             });
        } catch (SQLException e){
            e.printStackTrace();
        }
       
    }
}