import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class crear_cliente extends JFrame{
    private JPanel panel1;
    private JTextField textField1; //cedula
    private JTextField textField2; //correo
    private JTextField textField3; //nombre
    private JTextField textField4; //contrasenia
    private JTextField textField5; //edad
    private JTextField textField6; //direccion
    private JButton crearButton;
    private JButton volverButton;

    public crear_cliente(){
        setTitle("Crear");
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

        volverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cliente_login cl = new cliente_login();
                cl.setVisible(true);
                dispose();
            }
        });
    }

    public void crear()throws SQLException {
        String cedula = textField1.getText();
        String correo = textField2.getText();
        String nombre = textField3.getText();
        String contrasenia = textField4.getText();
        String edad = textField5.getText();
        String direccion = textField6.getText();

        Connection conectamos = connection();
        String sql = "INSERT INTO cliente(cedula, correo, nombre, contrasenia, edad, direccion)" +
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
