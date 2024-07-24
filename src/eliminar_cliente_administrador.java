import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class eliminar_cliente_administrador  extends JFrame{
    private JPanel panel1;
    private JTextField textField1;
    private JButton volverButton;
    private JButton eliminarButton;

    public eliminar_cliente_administrador(){
        setTitle("Administrador");
        setSize(600,600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(panel1);

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
        volverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CRUD_administrador c = new CRUD_administrador();
                c.setVisible(true);
                dispose();
            }
        });
    }

    public void eliminarRegistro() throws SQLException {
        String cedula = textField1.getText();

        Connection conectamos = connection();
        String sql = "DELETE FROM cliente WHERE cedula = ?";
        PreparedStatement pstmt = conectamos.prepareStatement(sql);
        pstmt.setString(1, cedula);

        int rowAffected = pstmt.executeUpdate();
        if (rowAffected > 0) {
            JOptionPane.showMessageDialog(null, "Registro eliminado correctamente");
        } else {
            JOptionPane.showMessageDialog(null, "No se encontro un registro con la cedula especificada");
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
