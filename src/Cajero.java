import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import java.text.SimpleDateFormat;
import java.util.Date;


import java.io.IOException;

/**
 * Clase cajero
 * @author Isaac VALENZUELA
 * @version 1.0.0
 * @since 27/07/2024
 */

public class Cajero extends JFrame {
    private JPanel panel1;
    private JTextField clienteNombreField;
    private JTextField clienteCedulaField;
    private JTextField clienteDireccionField;
    private JTextField productoIDField;
    private JTextField productoNombreField;
    private JTextField productoPrecioField;
    private JTextField cantidadField;
    private JButton agregarButton;
    private JButton venderButton;
    private JButton volverButton;
    private JTextArea registro;

    private List<Product> productList = new ArrayList<>();

    public Cajero() {
        // Frame configuraciones
        setTitle("Formulario de Facturación");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Inicializar componentes
        panel1 = new JPanel();
        panel1.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel clienteNombreLabel = new JLabel("Nombre del Cliente:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel1.add(clienteNombreLabel, gbc);
        clienteNombreField = new JTextField(20);
        gbc.gridx = 1;
        panel1.add(clienteNombreField, gbc);

        JLabel clienteCedulaLabel = new JLabel("Cédula del Cliente:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel1.add(clienteCedulaLabel, gbc);
        clienteCedulaField = new JTextField(20);
        gbc.gridx = 1;
        panel1.add(clienteCedulaField, gbc);

        JLabel clienteDireccionLabel = new JLabel("Dirección del Cliente:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel1.add(clienteDireccionLabel, gbc);
        clienteDireccionField = new JTextField(20);
        gbc.gridx = 1;
        panel1.add(clienteDireccionField, gbc);

        JLabel productoIDLabel = new JLabel("ID del Producto:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel1.add(productoIDLabel, gbc);
        productoIDField = new JTextField(20);
        gbc.gridx = 1;
        panel1.add(productoIDField, gbc);

        JLabel productoNombreLabel = new JLabel("Nombre del Producto:");
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel1.add(productoNombreLabel, gbc);
        productoNombreField = new JTextField(20);
        productoNombreField.setEditable(false);
        gbc.gridx = 1;
        panel1.add(productoNombreField, gbc);

        JLabel productoPrecioLabel = new JLabel("Precio del Producto:");
        gbc.gridx = 0;
        gbc.gridy = 5;
        panel1.add(productoPrecioLabel, gbc);
        productoPrecioField = new JTextField(20);
        productoPrecioField.setEditable(false);
        gbc.gridx = 1;
        panel1.add(productoPrecioField, gbc);

        JLabel cantidadLabel = new JLabel("Cantidad:");
        gbc.gridx = 0;
        gbc.gridy = 6;
        panel1.add(cantidadLabel, gbc);
        cantidadField = new JTextField(20);
        gbc.gridx = 1;
        panel1.add(cantidadField, gbc);

        agregarButton = new JButton("Agregar Producto");
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        panel1.add(agregarButton, gbc);

        venderButton = new JButton("Registrar Venta");
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        panel1.add(venderButton, gbc);

        registro = new JTextArea(10, 30);
        registro.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(registro);
        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridwidth = 2;
        panel1.add(scrollPane, gbc);

        volverButton = new JButton("Volver");
        gbc.gridx = 1;
        gbc.gridy = 10;
        gbc.anchor = GridBagConstraints.SOUTHEAST;
        panel1.add(volverButton, gbc);

        // añadir el panel al frame
        add(panel1);

        // Action Listeners
        agregarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    agregarProducto();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        venderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    registrarVenta();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        volverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menu_catalogo m = new menu_catalogo();
                m.setVisible(true);
                dispose();
            }
        });
    }

    //Funcion conectar
    public Connection connection() throws SQLException {
        String url = "jdbc:mysql://ufopvc9kf65j4cmx:CM1W2HBoNddWsjJzWMaC@bmfp6c3mefmlhvjslupe-mysql.services.clever-cloud.com:3306/bmfp6c3mefmlhvjslupe";
        String user = "ufopvc9kf65j4cmx";
        String password = "CM1W2HBoNddWsjJzWMaC";
        return DriverManager.getConnection(url, user, password);
    }

    //Funcion agregar productos
    public void agregarProducto() throws SQLException {
        String id = productoIDField.getText();
        Connection conectamos = connection();
        String sql = "SELECT * FROM InventarioProductos WHERE ID = ?";
        PreparedStatement pstmt = conectamos.prepareStatement(sql);
        pstmt.setInt(1, Integer.parseInt(id));

        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            int cantidad = Integer.parseInt(cantidadField.getText());
            Product product = new Product(
                    rs.getInt("ID"),
                    rs.getString("Nombre"),
                    rs.getDouble("Precio"),
                    cantidad
            );
            productList.add(product);
            registro.append("Producto agregado: " + rs.getString("Nombre") + " - Precio: " + rs.getDouble("Precio") + " - Cantidad: " + cantidad + "\n");
        } else {
            registro.append("No se encontró un producto con ese ID\n");
        }

        rs.close();
        pstmt.close();
        conectamos.close();
    }

    //Funcion para registrar ventas
    public void registrarVenta() throws SQLException {
        String clienteNombre = clienteNombreField.getText();
        String clienteCedula = clienteCedulaField.getText();
        String clienteDireccion = clienteDireccionField.getText();
        String fechaActual = new SimpleDateFormat("yyyy-MM-dd").format(new Date()); // Obtener la fecha actual

        Connection conectamos = connection();
        try {
            conectamos.setAutoCommit(false);

            double total = 0;
            for (Product product : productList) {
                total += product.getPrecio() * product.getCantidad();

                String updateSql = "UPDATE InventarioProductos SET Cantidad = Cantidad - ? WHERE ID = ?";
                PreparedStatement updatePstmt = conectamos.prepareStatement(updateSql);
                updatePstmt.setInt(1, product.getCantidad());
                updatePstmt.setInt(2, product.getId());
                updatePstmt.executeUpdate();
                updatePstmt.close();
            }

            String sql = "INSERT INTO Ventas (ClienteNombre, ClienteCedula, ClienteDireccion, Fecha, Total) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conectamos.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, clienteNombre);
            pstmt.setString(2, clienteCedula);
            pstmt.setString(3, clienteDireccion);
            pstmt.setString(4, fechaActual); // Añadir la fecha actual
            pstmt.setDouble(5, total);
            pstmt.executeUpdate();

            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                int ventaId = generatedKeys.getInt(1);

                for (Product product : productList) {
                    String detalleSql = "INSERT INTO DetalleVentas (VentaID, ProductoID, ProductoNombre, Precio, Cantidad) VALUES (?, ?, ?, ?, ?)";
                    PreparedStatement detallePstmt = conectamos.prepareStatement(detalleSql);
                    detallePstmt.setInt(1, ventaId);
                    detallePstmt.setInt(2, product.getId());
                    detallePstmt.setString(3, product.getNombre());
                    detallePstmt.setDouble(4, product.getPrecio());
                    detallePstmt.setInt(5, product.getCantidad());
                    detallePstmt.executeUpdate();
                    detallePstmt.close();
                }
            }

            pstmt.close();
            conectamos.commit();
            registro.append("Venta registrada exitosamente. Total: " + total + "\n");

            // Generar factura PDF
            generarFacturaPDF(clienteNombre, clienteCedula, clienteDireccion, productList, total);
        } catch (SQLException ex) {
            conectamos.rollback();
            registro.append("Error al registrar la venta.\n");
            ex.printStackTrace();
        } finally {
            conectamos.setAutoCommit(true);
            conectamos.close();
            productList.clear();
        }
    }




    // Clase de producto para contener detalles del producto
    class Product {
        private int id;
        private String nombre;
        private double precio;
        private int cantidad;

        public Product(int id, String nombre, double precio, int cantidad) {
            this.id = id;
            this.nombre = nombre;
            this.precio = precio;
            this.cantidad = cantidad;
        }

        public int getId() {
            return id;
        }

        public String getNombre() {
            return nombre;
        }

        public double getPrecio() {
            return precio;
        }

        public int getCantidad() {
            return cantidad;
        }
    }

    //Funcion para generar el pdf
    public void generarFacturaPDF(String clienteNombre, String clienteCedula, String clienteDireccion, List<Product> productList, double total) {
        // Genera un nombre de archivo unico con fecha y hora
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String dest = "factura_" + timestamp + ".pdf";
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 20);
            contentStream.beginText();
            contentStream.newLineAtOffset(220, 750);
            contentStream.showText("Factura de Compra");
            contentStream.endText();

            contentStream.setFont(PDType1Font.HELVETICA, 12);
            contentStream.beginText();
            contentStream.newLineAtOffset(50, 700);
            contentStream.showText("Nombre del Cliente: " + clienteNombre);
            contentStream.endText();

            contentStream.beginText();
            contentStream.newLineAtOffset(50, 680);
            contentStream.showText("Cédula del Cliente: " + clienteCedula);
            contentStream.endText();

            contentStream.beginText();
            contentStream.newLineAtOffset(50, 660);
            contentStream.showText("Dirección del Cliente: " + clienteDireccion);
            contentStream.endText();

            float yPosition = 620;
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
            contentStream.beginText();
            contentStream.newLineAtOffset(50, yPosition);
            contentStream.showText("ID       Nombre       Precio       Cantidad       Total");
            contentStream.endText();

            contentStream.setFont(PDType1Font.HELVETICA, 12);
            for (Product product : productList) {
                yPosition -= 20;
                contentStream.beginText();
                contentStream.newLineAtOffset(50, yPosition);
                contentStream.showText(product.getId() + "       " + product.getNombre() + "       " + product.getPrecio() + "       " + product.getCantidad() + "       " + (product.getPrecio() * product.getCantidad()));
                contentStream.endText();
            }

            yPosition -= 40;
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
            contentStream.beginText();
            contentStream.newLineAtOffset(50, yPosition);
            contentStream.showText("Total: " + total);
            contentStream.endText();

            contentStream.close();  // Ensure the content stream is closed before saving the document
            document.save(dest);
            System.out.println("Factura generada exitosamente: " + dest);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                document.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



}
