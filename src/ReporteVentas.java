import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import java.util.Date;

/**
 * Clase reporte de ventas
 * @author Isaac VALENZUELA
 * @version 1.0.0
 * @since 27/07/2024
 */
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

        JButton cerrarSesionButton = new JButton("Cerrar Sesión");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        panelReporte.add(cerrarSesionButton, gbc);

        ventasList = new ArrayList<>();
        String[] columnNames = {"VentaID", "ClienteNombre", "ClienteCedula", "ClienteDireccion", "Total", "Fecha"};
        table = new JTable(new Object[0][6], columnNames);
        scrollPane = new JScrollPane(table);
        gbc.gridx = 0;
        gbc.gridy = 5;
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

        cerrarSesionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cerrarSesion();
            }
        });
    }

    //Funcion para conectar
    public Connection connection() throws SQLException {
        String url = "jdbc:mysql://ufopvc9kf65j4cmx:CM1W2HBoNddWsjJzWMaC@bmfp6c3mefmlhvjslupe-mysql.services.clever-cloud.com:3306/bmfp6c3mefmlhvjslupe";
        String user = "ufopvc9kf65j4cmx";
        String password = "CM1W2HBoNddWsjJzWMaC";
        return DriverManager.getConnection(url, user, password);
    }

    //Funcion para filtrar por ventas
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
                    rs.getInt("VentaID"),
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

    //Funcion para actualizar tabla
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

    //Funcion para generar el pdf
    public void generarReportePDF() throws IOException {
        // Crear un nuevo documento
        PDDocument document = new PDDocument();

        // Añadir una página al documento
        PDPage page = new PDPage();
        document.addPage(page);

        // Iniciar un flujo de contenido para la página
        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        // Configurar la fuente y el tamaño de la fuente
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 14);
        contentStream.beginText();
        contentStream.setLeading(14.5f);
        contentStream.newLineAtOffset(50, 750);

        // Escribir el título del reporte
        contentStream.showText("Reporte de Ventas");
        contentStream.newLine();
        contentStream.newLine();

        // Escribir los encabezados de las columnas
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
        contentStream.showText("ID         Nombre Cliente         Cédula         Dirección         Total         Fecha");
        contentStream.newLine();

        // Escribir los datos de las ventas
        contentStream.setFont(PDType1Font.HELVETICA, 12);
        for (Venta venta : ventasList) {
            String line = String.format("%-10s %-20s %-15s %-20s %-10.2f %s",
                    venta.getId(),
                    venta.getClienteNombre(),
                    venta.getClienteCedula(),
                    venta.getClienteDireccion(),
                    venta.getTotal(),
                    venta.getFecha());
            contentStream.showText(line);
            contentStream.newLine();
        }

        // Terminar el flujo de contenido
        contentStream.endText();
        contentStream.close();

        // Obtener la fecha actual para el nombre del archivo
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String fechaActual = dateFormat.format(new Date());
        String nombreArchivo = "ReporteVentas_" + fechaActual + ".pdf";

        // Guardar el documento en un archivo con la fecha en el nombre
        document.save(nombreArchivo);

        // Cerrar el documento
        document.close();

        // Mostrar mensaje de confirmación
        JOptionPane.showMessageDialog(this, "Reporte PDF generado exitosamente. Guardado como " + nombreArchivo);
    }
    public void cerrarSesion() {
        dispose(); // Cierra la ventana actual
        new Login(); // Abre la ventana de inicio de sesión
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

}
