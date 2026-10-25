package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import modelo.Puntaje;


public class PanelRanking extends PanelBase {

    private static final Dimension DIM_TABLA = new Dimension(600,145);
    private static final Color COLOR_BG_PANEL = new Color(254, 163, 0);
    private static final Font FONT_TABLA = new Font(Font.SANS_SERIF, Font.BOLD, 24);
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
        tablaRanking.getTableHeader().setReorderingAllowed(false);
        tablaRanking.getTableHeader().setResizingAllowed(false);
        tablaRanking.setFont(FONT_TABLA);
        tablaRanking.setRowHeight(35);

        //Colores
        tablaRanking.setBackground(COLOR_BG_PANEL);
        tablaRanking.setForeground(Color.BLACK);
        tablaRanking.setGridColor(Color.BLACK);

        JTableHeader encabezadoTabla = tablaRanking.getTableHeader();
        encabezadoTabla.setBackground(COLOR_BG_PANEL);
        encabezadoTabla.setForeground(Color.BLACK);
        encabezadoTabla.setFont(FONT_TABLA);
        encabezadoTabla.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        //Formato de columnas
        tablaRanking.getColumnModel().getColumn(0).setPreferredWidth(5);  // Posición
        tablaRanking.getColumnModel().getColumn(1).setPreferredWidth(100); // Jugador
        tablaRanking.getColumnModel().getColumn(2).setPreferredWidth(80); // Puntaje
        tablaRanking.getColumnModel().getColumn(3).setPreferredWidth(80);  // Tiempo
        tablaRanking.getColumnModel().getColumn(4).setPreferredWidth(100); // Fecha

        tablaRanking.setRowSelectionAllowed(false);
        tablaRanking.setColumnSelectionAllowed(false);
        tablaRanking.setCellSelectionEnabled(false);

        JScrollPane scrollPane = new JScrollPane(tablaRanking);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
        scrollPane.setPreferredSize(DIM_TABLA);

        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setWheelScrollingEnabled(false);

        JPanel contenedorTabla = new JPanel(new FlowLayout());
        contenedorTabla.setBorder(BorderFactory.createEmptyBorder(50,0,0,0));
        contenedorTabla.add(scrollPane);
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