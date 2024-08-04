import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;

public class Administrador_Productos extends JFrame {
    private JPanel panel1;
    private JTabbedPane tabbedPane1;
    private JTextField textField1;
    private JTextArea textArea1;
    private JLabel imagenLabel;
    private JButton buscarButton;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JTextField textField6;
    private JButton seleccionarButton;
    private JLabel imagenLabel1;
    private JButton ingresarButton;
    private JTabbedPane tabbedPane2;
    private JTextField textField7;
    private JTextField textField8;
    private JTextField textField9;
    private JButton actualizarButton;
    private JTextField textField10;
    private JButton seleccionarButton1;
    private JButton actualizarButton1;
    private JLabel imagenLabel2;
    private JTextField textField11;
    private JButton eliminarButton;
    private JButton cerrarSesionButton;
    private File imagenSeleccionada;

    public Administrador_Productos(){
        setTitle("Administrador");
        setSize(600,665);
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

        seleccionarButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                seleccionarImagen2();
            }
        });

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

        actualizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    actualizarDatos();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        actualizarButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ImagenActualizar();
                } catch (SQLException | IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        eliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    eliminarRegistro();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        cerrarSesionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Login l = new Login();
                l.setVisible(true);
                dispose();
            }
        });
    }

    //Conexion

    public Connection connection() throws SQLException {
        String url= "jdbc:mysql://ufopvc9kf65j4cmx:CM1W2HBoNddWsjJzWMaC@bmfp6c3mefmlhvjslupe-mysql.services.clever-cloud.com:3306/bmfp6c3mefmlhvjslupe";
        String user="ufopvc9kf65j4cmx";
        String password="CM1W2HBoNddWsjJzWMaC";
        return DriverManager.getConnection(url, user, password);
    }

    //Funcion ingresa productos
    //Imagen
    public void seleccionarImagen() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            imagenSeleccionada = fileChooser.getSelectedFile();
            imagenLabel1.setText("Imagen seleccionada: " + imagenSeleccionada.getName());
        } else {
            imagenLabel1.setText("No se seleccionó ninguna imagen.");
        }
    }
    //Informaciom completa
    public void ingresa() throws SQLException, IOException {
        String nombre = textField2.getText();
        String descripcion = textField3.getText();
        String precio = textField4.getText();
        String cantidad = textField5.getText();
        String f_ingreso = textField6.getText();

        Connection conectamos = connection();
        String sql = "INSERT INTO InventarioProductos(Nombre, Descripción, Precio, Cantidad, Fecha_Entrada, Imagen) VALUES(?,?,?,?,?,?)";
        PreparedStatement pstmt = conectamos.prepareStatement(sql);
        pstmt.setString(1, nombre);
        pstmt.setString(2, descripcion);
        pstmt.setDouble(3, Double.parseDouble(precio));
        pstmt.setInt(4, Integer.parseInt(cantidad));
        Date fechaIngreso = Date.valueOf(f_ingreso);
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
            textField2.setText("");
            textField3.setText("");
            textField4.setText("");
            textField5.setText("");
            textField6.setText("");
        }

        pstmt.close();
        conectamos.close();
    }




    //Funcion buscar productos

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

            textArea1.setText(
                    "ID: " + ide + "\n" +
                            "Nombre: " + nombre + "\n" +
                            "Descripción: " + descripcion + "\n" +
                            "Precio: " + precio + "\n" +
                            "Cantidad: " + cantidad + "\n" +
                            "Fecha de entrada: " + fechaEntrada + "\n" +
                            "Fecha cuando se actualizó: " + fechaActualizacion
            );
            textField1.setText("");

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

    //Funcion actualizar productos

    //Actualizar Datos
    public void actualizarDatos() throws SQLException {
        String id = textField7.getText();
        String columna = textField8.getText();
        String datoNuevo = textField9.getText();

        Connection conectamos = connection();
        String sql = "UPDATE InventarioProductos SET " + columna + " = ? WHERE ID = ?";
        PreparedStatement pstmt = conectamos.prepareStatement(sql);
        pstmt.setString(1, datoNuevo);
        pstmt.setInt(2, Integer.parseInt(id));

        int rowAffected = pstmt.executeUpdate();
        if (rowAffected > 0) {
            JOptionPane.showMessageDialog(null, "Producto actualizado correctamente");
        } else {
            JOptionPane.showMessageDialog(null, "No se encontró un producto con ese ID");
        }
        pstmt.close();
        conectamos.close();
    }

    //Actualizar Imagen
    public void seleccionarImagen2() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            imagenSeleccionada = fileChooser.getSelectedFile();
            imagenLabel2.setText("Imagen seleccionada: " + imagenSeleccionada.getName());
        } else {
            imagenLabel2.setText("No se seleccionó ninguna imagen.");
        }
    }

    public void ImagenActualizar() throws SQLException, IOException {
        String id = textField10.getText();
        Connection conectamos = connection();
        String sql = "UPDATE InventarioProductos SET imagen = ? WHERE ID = ?";
        PreparedStatement pstmt = conectamos.prepareStatement(sql);
        pstmt.setString(2, id);

        if (imagenSeleccionada != null) {
            FileInputStream fis = new FileInputStream(imagenSeleccionada);
            pstmt.setBinaryStream(1, fis, (int) imagenSeleccionada.length());
        } else {
            pstmt.setNull(1, Types.BLOB);
        }

        int rowAffected = pstmt.executeUpdate();
        if (rowAffected > 0) {
            JOptionPane.showMessageDialog(null, "Imagen actualizada con éxito!");
            textField10.setText("");
        }

        pstmt.close();
        conectamos.close();
    }

    //Funcion eliminar productos

    public void eliminarRegistro() throws SQLException {
        String id = textField11.getText();

        Connection conectamos = connection();
        String sql = "DELETE FROM InventarioProductos WHERE ID = ?";
        PreparedStatement pstmt = conectamos.prepareStatement(sql);
        pstmt.setInt(1, Integer.parseInt(id));;

        int rowAffected = pstmt.executeUpdate();
        if (rowAffected > 0) {
            JOptionPane.showMessageDialog(null, "Producto eliminado correctamente");
        } else {
            JOptionPane.showMessageDialog(null, "No se encontro un registro con el ID especificado");
        }
        pstmt.close();
        conectamos.close();
    }


}
