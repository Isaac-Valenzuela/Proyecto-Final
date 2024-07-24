import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class actualizar_cliente_administrador extends JFrame{
    private JPanel panel1;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JButton actualizarButton;
    private JButton volverButton;

    public actualizar_cliente_administrador(){
        setTitle("Administrador");
        setSize(600,600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(panel1);

        volverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CRUD_administrador x = new CRUD_administrador();
                x.setVisible(true);
                dispose();
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
    }
    public void actualizarDatos() throws SQLException {
        String cedula = textField1.getText();
        String columna = textField2.getText();
        String datoNuevo = textField3.getText();

        Connection conectamos = connection();
        String sql = "UPDATE cliente SET " + columna + " = ? WHERE cedula = ?";
        PreparedStatement pstmt = conectamos.prepareStatement(sql);
        pstmt.setString(1, datoNuevo);
        pstmt.setString(2, cedula);

        int rowAffected = pstmt.executeUpdate();
        if (rowAffected > 0) {
            JOptionPane.showMessageDialog(null, "Registro actualizado correctamente");
        } else {
            JOptionPane.showMessageDialog(null, "No se encontró un registro con esa cédula");
        }
        pstmt.close();
        conectamos.close();
    }
    public Connection connection() throws SQLException {
        String url= "jdbc:mysql://ufopvc9kf65j4cmx:CM1W2HBoNddWsjJzWMaC@bmfp6c3mefmlhvjslupe-mysql.services.clever-cloud.com:3306/bmfp6c3mefmlhvjslupe";
        String user="ufopvc9kf65j4cmx";
        String password="CM1W2HBoNddWsjJzWMaC";
        return DriverManager.getConnection(url,user,password);
    }
}
