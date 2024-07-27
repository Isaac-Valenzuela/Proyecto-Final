import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Crear_Producto_Administrador extends JFrame{
    private JPanel panel1;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JButton ingresarButton;
    private JButton volverButton;

    public Crear_Producto_Administrador(){
        setTitle("Administrador");
        setSize(600,600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(panel1);

        ingresarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    ingresa();
                }catch (SQLException ex){
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
    public void ingresa()throws SQLException {
        String nombre = textField1.getText();
        String descripcion = textField2.getText();
        String precio = textField3.getText();
        String cantidad = textField4.getText();
        String f_ingreso = textField5.getText();

        Connection conectamos = connection();
        String sql = "INSERT INTO InventarioProductos(Nombre, Descripción, Precio," +
                " Cantidad, Fecha_Entrada)" +
                "VALUES(?,?,?,?,?)";
        PreparedStatement pstmt = conectamos.prepareStatement(sql);
        pstmt.setString(1, nombre);
        pstmt.setString(2, descripcion);
        pstmt.setDouble(3, Double.parseDouble(precio));
        pstmt.setInt(4, Integer.parseInt(cantidad));;
        java.sql.Date fechaIngreso = java.sql.Date.valueOf(f_ingreso);
        pstmt.setDate(5, fechaIngreso);

        int rowAffected= pstmt.executeUpdate();
        if(rowAffected >0){
            JOptionPane.showMessageDialog(null, "Producto Ingresado con exito!!");
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
