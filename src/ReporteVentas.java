import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ReporteVentas extends JFrame {
    private JTabbedPane tabbedPane;
    private JPanel panelReporte;
    private JTextField fechaInicioField;
    private JTextField fechaFinField;
    private JButton filtrarButton;
    private JButton generarPDFButton;
    private JTable table;
    private JScrollPane scrollPane;
    private List<Venta> ventasList;

    public ReporteVentas() {
        setTitle("Reporte de Ventas");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        tabbedPane = new JTabbedPane();
        panelReporte = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel fechaInicioLabel = new JLabel("Fecha Inicio (YYYY-MM-DD):");
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelReporte.add(fechaInicioLabel, gbc);
        fechaInicioField = new JTextField(20);
        gbc.gridx = 1;
        panelReporte.add(fechaInicioField, gbc);

        JLabel fechaFinLabel = new JLabel("Fecha Fin (YYYY-MM-DD):");
        gbc.gridx = 0;
        gbc.gridy = 1;
        panelReporte.add(fechaFinLabel, gbc);
        fechaFinField = new JTextField(20);
        gbc.gridx = 1;
        panelReporte.add(fechaFinField, gbc);

        filtrarButton = new JButton("Filtrar Ventas");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        panelReporte.add(filtrarButton, gbc);

        generarPDFButton = new JButton("Generar PDF");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        panelReporte.add(generarPDFButton, gbc);

        ventasList = new ArrayList<>();
        String[] columnNames = {"ID", "Nombre Cliente", "Cédula", "Dirección", "Total", "Fecha"};
        table = new JTable(new Object[0][6], columnNames);
        scrollPane = new JScrollPane(table);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        panelReporte.add(scrollPane, gbc);

        tabbedPane.addTab("Reporte de Ventas", panelReporte);
        add(tabbedPane);

        // Action Listeners
        filtrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    filtrarVentas();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        generarPDFButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    generarReportePDF();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    public Connection connection() throws SQLException {
        String url = "jdbc:mysql://ufopvc9kf65j4cmx:CM1W2HBoNddWsjJzWMaC@bmfp6c3mefmlhvjslupe-mysql.services.clever-cloud.com:3306/bmfp6c3mefmlhvjslupe";
        String user = "ufopvc9kf65j4cmx";
        String password = "CM1W2HBoNddWsjJzWMaC";
        return DriverManager.getConnection(url, user, password);
    }

    public void filtrarVentas() throws SQLException {
        ventasList.clear();
        String fechaInicio = fechaInicioField.getText();
        String fechaFin = fechaFinField.getText();

        Connection conectamos = connection();
        String sql = "SELECT * FROM Ventas WHERE Fecha BETWEEN ? AND ?";
        PreparedStatement pstmt = conectamos.prepareStatement(sql);
        pstmt.setString(1, fechaInicio);
        pstmt.setString(2, fechaFin);

        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            Venta venta = new Venta(
                    rs.getInt("ID"),
                    rs.getString("ClienteNombre"),
                    rs.getString("ClienteCedula"),
                    rs.getString("ClienteDireccion"),
                    rs.getDouble("Total"),
                    rs.getString("Fecha")
            );
            ventasList.add(venta);
        }

        rs.close();
        pstmt.close();
        conectamos.close();

        actualizarTabla();
    }

    public void actualizarTabla() {
        String[] columnNames = {"ID", "Nombre Cliente", "Cédula", "Dirección", "Total", "Fecha"};
        Object[][] data = new Object[ventasList.size()][6];
        for (int i = 0; i < ventasList.size(); i++) {
            Venta venta = ventasList.get(i);
            data[i][0] = venta.getId();
            data[i][1] = venta.getClienteNombre();
            data[i][2] = venta.getClienteCedula();
            data[i][3] = venta.getClienteDireccion();
            data[i][4] = venta.getTotal();
            data[i][5] = venta.getFecha();
        }
        table.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));
    }

    public void generarReportePDF() throws IOException {
        // Similar to the previous code, generate a PDF using PDFBox
        // You can use ventasList to get the data
        // For brevity, not repeating the PDF generation code here
    }

    class Venta {
        private int id;
        private String clienteNombre;
        private String clienteCedula;
        private String clienteDireccion;
        private double total;
        private String fecha;

        public Venta(int id, String clienteNombre, String clienteCedula, String clienteDireccion, double total, String fecha) {
            this.id = id;
            this.clienteNombre = clienteNombre;
            this.clienteCedula = clienteCedula;
            this.clienteDireccion = clienteDireccion;
            this.total = total;
            this.fecha = fecha;
        }

        public int getId() {
            return id;
        }

        public String getClienteNombre() {
            return clienteNombre;
        }

        public String getClienteCedula() {
            return clienteCedula;
        }

        public String getClienteDireccion() {
            return clienteDireccion;
        }

        public double getTotal() {
            return total;
        }

        public String getFecha() {
            return fecha;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ReporteVentas().setVisible(true);
            }
        });
    }
}
