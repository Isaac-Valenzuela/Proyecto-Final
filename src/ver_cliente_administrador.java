import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ver_cliente_administrador extends JFrame {
    private JPanel panel1;
    private JTextField textField1;
    private JButton buscarButton;
    private JButton volverButton;

    public ver_cliente_administrador(){
        setTitle("Administrador");
        setSize(600,600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(panel1);

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
        volverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CRUD_administrador Cr = new CRUD_administrador();
                Cr.setVisible(true);
                dispose();
            }
        });
    }

    public void busqueda()throws SQLException {
        int ci = Integer.parseInt(textField1.getText());
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

        }
        rs.close();
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
