import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.*;

public class Ver_Producto_Administrador extends JFrame {
    private JPanel panel1;
    private JTextField textField1;
    private JButton buscarButton;
    private JButton volverButton;
    private JTextArea textArea1;
    private JLabel imagenLabel;

    public Ver_Producto_Administrador() {
        setTitle("Administrador");
        setSize(740, 670);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(panel1);

        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    busqueda();
                } catch (SQLException | IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        volverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CRUD_Productos_Administrador cpa = new CRUD_Productos_Administrador();
                cpa.setVisible(true);
                dispose();
            }
        });
    }

    public void busqueda() throws SQLException, IOException {
        int ID = Integer.parseInt(textField1.getText());
        Connection conectamos = connection();
        String sql = "SELECT * FROM InventarioProductos WHERE ID = ?";
        PreparedStatement pstmt = conectamos.prepareStatement(sql);
        pstmt.setInt(1, ID);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            String ide = rs.getString("ID");
            String nombre = rs.getString("Nombre");
            String descripcion = rs.getString("Descripción");
            String precio = rs.getString("Precio");
            String cantidad = rs.getString("Cantidad");
            String fechaEntrada = rs.getString("Fecha_Entrada");
            String fechaActualizacion = rs.getString("Fecha_Actualización");

            textArea1.setText("| ID: " + ide + " | Nombre: " + nombre + " | Descripción: " + descripcion +
                    " | Precio: " + precio + " | Cantidad: " + cantidad + " | Fecha de entrada: " + fechaEntrada +
                    " | Fecha cuando se actualizó: " + fechaActualizacion);

            Blob blob = rs.getBlob("Imagen");
            if (blob != null) {
                byte[] data = blob.getBytes(1, (int) blob.length());
                ByteArrayInputStream bais = new ByteArrayInputStream(data);
                try {
                    Image imagen = ImageIO.read(bais);
                    if (imagen != null) {
                        ImageIcon imageIcon = new ImageIcon(imagen);
                        imagenLabel.setIcon(imageIcon);
                        imagenLabel.setText(""); // Limpiar texto si hay imagen
                    } else {
                        System.out.println("La imagen no pudo ser decodificada.");
                        imagenLabel.setText("No se pudo cargar la imagen.");
                    }
                } catch (IOException ex) {
                    System.out.println("No se pudo decodificar la imagen: " + ex.getMessage());
                    imagenLabel.setText("Formato de imagen no soportado.");
                }
            } else {
                System.out.println("El blob de imagen es null.");
                imagenLabel.setText("No hay imagen disponible.");
            }
        } else {
            System.out.println("No se encontró el producto con ID: " + ID);
            imagenLabel.setText("Producto no encontrado.");
        }
        rs.close();
        pstmt.close();
        conectamos.close();
    }

    public Connection connection() throws SQLException {
        String url= "jdbc:mysql://ufopvc9kf65j4cmx:CM1W2HBoNddWsjJzWMaC@bmfp6c3mefmlhvjslupe-mysql.services.clever-cloud.com:3306/bmfp6c3mefmlhvjslupe";
        String user="ufopvc9kf65j4cmx";
        String password="CM1W2HBoNddWsjJzWMaC";
        return DriverManager.getConnection(url, user, password);
    }

}
