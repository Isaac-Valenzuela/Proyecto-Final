import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Ver_Producto_Administrador extends JFrame {
    private JPanel panel1;
    private JTextField textField1;
    private JButton buscarButton;
    private JButton volverButton;

    public Ver_Producto_Administrador(){
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
                CRUD_Productos_Administrador cpa = new CRUD_Productos_Administrador();
                cpa.setVisible(true);
                dispose();
            }
        });
    }

    public void busqueda()throws SQLException {
        int ID = Integer.parseInt(textField1.getText());
        Connection conectamos = connection();
        String sql = "Select * from InventarioProductos where ID = ?";
        PreparedStatement pstmt = conectamos.prepareStatement(sql);
        pstmt.setInt(1, ID);
        ResultSet rs = pstmt.executeQuery();
        if(rs.next()){
            String ide = rs.getString("ID");
            String nombre = rs.getString("Nombre");
            String descripcin = rs.getString("Descripción");
            String precio = rs.getString("Precio");
            String cantidad = rs.getString("Cantidad");
            String Fecha_entrada = rs.getString("Fecha_entrada");
            String Fecha_act = rs.getString("Fecha_actualización");
            JOptionPane.showMessageDialog(null, "| ID: " +ide +" | Nombre: "
                    +nombre +" | Descripción: " +descripcin+" | Precio: " +precio+" | Cantidad: "
                    +cantidad+" | Fecha de entrada: " + Fecha_entrada+
                    " | Fecha cuando se actualizo: "+ Fecha_act);

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
