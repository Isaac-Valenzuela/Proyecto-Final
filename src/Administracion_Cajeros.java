import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Administracion_Cajeros extends JFrame{
    private JPanel panel1;
    private JTabbedPane tabbedPane1;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JTextField textField6;
    private JButton crearButton;
    private JTextField textField7;
    private JTextField textField8;
    private JTextField textField9;
    private JButton actualizarButton;
    private JTextField textField10;
    private JButton buscarButton;
    private JTextField textField11;
    private JButton eliminarButton;
    private JButton salirButton;


    public Administracion_Cajeros(){
        setTitle("Admistrador");
        setSize(600,600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(panel1);

        crearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    crear();
                }catch (SQLException ex){
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

        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    busqueda();
                }catch (SQLException ex){
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

        salirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });
    }

    //Conexion

    public Connection connection() throws SQLException {
        String url= "jdbc:mysql://ufopvc9kf65j4cmx:CM1W2HBoNddWsjJzWMaC@bmfp6c3mefmlhvjslupe-mysql.services.clever-cloud.com:3306/bmfp6c3mefmlhvjslupe";
        String user="ufopvc9kf65j4cmx";
        String password="CM1W2HBoNddWsjJzWMaC";
        return DriverManager.getConnection(url,user,password);
    }


    // Funcion Crear Cajeros

    public void crear()throws SQLException {
        String cedula = textField1.getText();
        String correo = textField2.getText();
        String nombre = textField3.getText();
        String contrasenia = textField4.getText();
        String edad = textField5.getText();
        String direccion = textField6.getText();

        Connection conectamos = connection();
        String sql = "INSERT INTO cajero(cedula, correo, nombre, contrasenia, edad, direccion)" +
                "VALUES(?,?,?,?,?,?)";
        PreparedStatement pstmt = conectamos.prepareStatement(sql);
        pstmt.setString(1, cedula);
        pstmt.setString(2, correo);
        pstmt.setString(3, nombre);
        pstmt.setString(4, contrasenia);
        pstmt.setInt(5, Integer.parseInt(edad));
        pstmt.setString(6, direccion);

        int rowAffected= pstmt.executeUpdate();
        if(rowAffected >0){
            JOptionPane.showMessageDialog(null, "Cuenta creada con Exito!!");
            textField1.setText("");
            textField2.setText("");
            textField3.setText("");
            textField4.setText("");
            textField5.setText("");
            textField6.setText("");
        }
        pstmt.close();
        conectamos.close();
    }

    //Funcion Actualizar Cajeros

    public void actualizarDatos() throws SQLException {
        String cedula = textField7.getText();
        String columna = textField8.getText();
        String datoNuevo = textField9.getText();

        Connection conectamos = connection();
        String sql = "UPDATE cajero SET " + columna + " = ? WHERE cedula = ?";
        PreparedStatement pstmt = conectamos.prepareStatement(sql);
        pstmt.setString(1, datoNuevo);
        pstmt.setString(2, cedula);

        int rowAffected = pstmt.executeUpdate();
        if (rowAffected > 0) {
            JOptionPane.showMessageDialog(null, "Registro actualizado correctamente");
            textField7.setText("");
            textField8.setText("");
            textField9.setText("");
        } else {
            JOptionPane.showMessageDialog(null, "No se encontró un registro con esa cédula");
        }
        pstmt.close();
        conectamos.close();
    }

    //Funcion Buscar Cajeros

    public void busqueda()throws SQLException {
        int ci = Integer.parseInt(textField10.getText());
        Connection conectamos = connection();
        String sql = "Select * from cajero where cedula = ?";
        PreparedStatement pstmt = conectamos.prepareStatement(sql);
        pstmt.setInt(1, ci);
        ResultSet rs = pstmt.executeQuery();
        if(rs.next()){
            String cedula = rs.getString("cedula");
            String correo = rs.getString("correo");
            String nombre = rs.getString("nombre");
            String constrasenia = rs.getString("contrasenia");
            String edad = rs.getString("edad");
            String direccion = rs.getString("direccion");
            JOptionPane.showMessageDialog(null, "| Cedula: " +cedula +" | Correo: "
                    +correo +" | Nombre: " +nombre+" | Contraseña: " +constrasenia+" | Edad: " +edad+" | Direccion: "
                    + direccion);
            textField10.setText("");

        }
        rs.close();
        pstmt.close();
        conectamos.close();
    }

    //Funcion Eliminar Cajeros

    public void eliminarRegistro() throws SQLException {
        String cedula = textField11.getText();

        Connection conectamos = connection();
        String sql = "DELETE FROM cajero WHERE cedula = ?";
        PreparedStatement pstmt = conectamos.prepareStatement(sql);
        pstmt.setString(1, cedula);

        int rowAffected = pstmt.executeUpdate();
        if (rowAffected > 0) {
            JOptionPane.showMessageDialog(null, "Registro eliminado correctamente");
            textField11.setText("");
        } else {
            JOptionPane.showMessageDialog(null, "No se encontro un registro con la cedula especificada");
        }
        pstmt.close();
        conectamos.close();
    }

}
