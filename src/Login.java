import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Login extends JFrame {
    private JPanel panel1;
    private JTabbedPane tabbedPane1;
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JButton iniciarSesionButton;
    private JTextField textField2;
    private JPasswordField passwordField2;
    private JButton iniciarSesionButton1;

    /**
     * Clase login
     * @author Isaac VALENZUELA
     * @since 27/07/2024
     */
    public Login(){
        setTitle("Login");
        setSize(600,600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(panel1);

        iniciarSesionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    verficar();
                }catch (SQLException ex){
                    ex.printStackTrace();
                }
            }
        });

        iniciarSesionButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    iniciar();
                }catch (SQLException ex){
                    ex.printStackTrace();
                }
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

    //Funcion Iniciar Sesion Cajero

    public void verficar() throws SQLException{
        String user = textField1.getText();
        String pass = passwordField1.getText();

        Connection conectamos = connection();
        String query="SELECT * FROM cajero WHERE correo = ? AND contrasenia=?";
        PreparedStatement pstmt = conectamos.prepareStatement(query);
        pstmt.setString(1, user);
        pstmt.setString(2, pass);

        ResultSet rs = pstmt.executeQuery();

        if (rs.next()){
            menu_catalogo m = new menu_catalogo();
            m.setVisible(true);
            dispose();

        }

    }

    //Funcion Iniciar Sesion Administrador

    public void iniciar() throws SQLException {
        String user = textField2.getText();
        String pass = passwordField2.getText();

        Connection conectamos = connection();
        String query = "SELECT * FROM administrador WHERE correo = ? AND contrasenia=?";
        PreparedStatement pstmt = conectamos.prepareStatement(query);
        pstmt.setString(1, user);
        pstmt.setString(2, pass);

        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            menu_administrador m = new menu_administrador();
            m.setVisible(true);
            dispose();

        }
    }
}
