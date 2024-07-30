import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;

public class Crear_Producto_Administrador extends JFrame {
    private JPanel panel1;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JButton ingresarButton;
    private JButton volverButton;
    private JButton seleccionarButton;
    private JLabel imagenLabel;

    private File imagenSeleccionada;

    public Crear_Producto_Administrador() {
        setTitle("Administrador");
        setSize(600, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(panel1);

        ingresarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ingresa();
                } catch (SQLException | IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        seleccionarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                seleccionarImagen();
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

    public void seleccionarImagen() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            imagenSeleccionada = fileChooser.getSelectedFile();
            imagenLabel.setText("Imagen seleccionada: " + imagenSeleccionada.getName());
        } else {
            imagenLabel.setText("No se seleccionó ninguna imagen.");
        }
    }

    public void ingresa() throws SQLException, IOException {
        String nombre = textField1.getText();
        String descripcion = textField2.getText();
        String precio = textField3.getText();
        String cantidad = textField4.getText();
        String f_ingreso = textField5.getText();

        Connection conectamos = connection();
        String sql = "INSERT INTO InventarioProductos(Nombre, Descripción, Precio, Cantidad, Fecha_Entrada, Imagen) VALUES(?,?,?,?,?,?)";
        PreparedStatement pstmt = conectamos.prepareStatement(sql);
        pstmt.setString(1, nombre);
        pstmt.setString(2, descripcion);
        pstmt.setDouble(3, Double.parseDouble(precio));
        pstmt.setInt(4, Integer.parseInt(cantidad));
        java.sql.Date fechaIngreso = java.sql.Date.valueOf(f_ingreso);
        pstmt.setDate(5, fechaIngreso);

        if (imagenSeleccionada != null) {
            FileInputStream fis = new FileInputStream(imagenSeleccionada);
            pstmt.setBinaryStream(6, fis, (int) imagenSeleccionada.length());
        } else {
            pstmt.setNull(6, Types.BLOB);
        }

        int rowAffected = pstmt.executeUpdate();
        if (rowAffected > 0) {
            JOptionPane.showMessageDialog(null, "Producto ingresado con éxito!");
        }

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
