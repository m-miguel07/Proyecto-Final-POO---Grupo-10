package vista;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


import modelo.Puntaje;


public class PanelRanking extends PanelBase {

    private JTable tablaRanking;
    
    public PanelRanking(){
        super("src/assets/ui/bg-ranking.png", 
            new Dimension(690,425), 
            "src/assets/ui/img-ranking.png", 
            new Dimension(335,55), 
            true);

        DefaultTableModel modelo = new DefaultTableModel(
            new Object[] { "Pos.", "Jugador", "Puntaje", "Tiempo", "Fecha"},0
        ) {
            @Override 
            public boolean isCellEditable (int fila, int col){
                return false;
            }
        };

        this.tablaRanking = new JTable(modelo);
        tablaRanking.setPreferredSize(new Dimension(600,105));
        tablaRanking.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));
        tablaRanking.setRowHeight(35);

        tablaRanking.getColumnModel().getColumn(0).setPreferredWidth(5);  // Posición
        tablaRanking.getColumnModel().getColumn(1).setPreferredWidth(100); // Jugador
        tablaRanking.getColumnModel().getColumn(2).setPreferredWidth(80); // Puntaje
        tablaRanking.getColumnModel().getColumn(3).setPreferredWidth(80);  // Tiempo
        tablaRanking.getColumnModel().getColumn(4).setPreferredWidth(100); // Fecha

        JPanel contenedorTabla = new JPanel(new FlowLayout());
        contenedorTabla.add(tablaRanking);
        contenedorTabla.setOpaque(false);

        this.add(contenedorTabla, BorderLayout.CENTER);
    }

    public void mostrarPuntajes(List<Puntaje> puntajes) {
    
    DefaultTableModel modelo = (DefaultTableModel) tablaRanking.getModel();
    modelo.setRowCount(0);

    int posicion = 1;

    for (Puntaje p : puntajes) {
        modelo.addRow(new Object[] {
            posicion++,
            p.getJugador(),
            p.getPuntos(),
            p.getTiempo(),
            p.getFecha(),
        });
    }
}
}