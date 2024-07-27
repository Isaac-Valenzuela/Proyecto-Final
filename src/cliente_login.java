import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class cliente_login extends JFrame{
    private JPanel panel1;
    private JTextField textField1; //Correo
    private JPasswordField passwordField1; //Constrasenia
    private JButton iniciarSesionButton;
    private JButton volverButton;

    public cliente_login(){
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

        volverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menu_login ml = new menu_login();
                ml.setVisible(true);
                dispose();
            }
        });


    }

    public void verficar() throws SQLException{
        String user = textField1.getText();
        String pass = passwordField1.getText();

        Connection conectamos = connection();
        String query="SELECT * FROM cliente WHERE correo = ? AND contrasenia=?";
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
    public Connection connection() throws SQLException {
        String url= "jdbc:mysql://ufopvc9kf65j4cmx:CM1W2HBoNddWsjJzWMaC@bmfp6c3mefmlhvjslupe-mysql.services.clever-cloud.com:3306/bmfp6c3mefmlhvjslupe";
        String user="ufopvc9kf65j4cmx";
        String password="CM1W2HBoNddWsjJzWMaC";
        return DriverManager.getConnection(url,user,password);
    }

}
