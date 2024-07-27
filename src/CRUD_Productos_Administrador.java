import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CRUD_Productos_Administrador extends JFrame{
    private JPanel panel1;
    private JButton añadirProductoButton;
    private JButton eliminarProductoButton;
    private JButton actualizarProductoButton;
    private JButton buscarProductoButton;
    private JButton volverButton;

    public CRUD_Productos_Administrador(){
        setTitle("Administrador");
        setSize(600,600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(panel1);

        añadirProductoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Crear_Producto_Administrador cpa = new Crear_Producto_Administrador();
                cpa.setVisible(true);
                dispose();
            }
        });
        eliminarProductoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Eliminar_Productos_Administrador epa = new Eliminar_Productos_Administrador();
                epa.setVisible(true);
                dispose();
            }
        });
        buscarProductoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        actualizarProductoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Actualizar_Producto_Administrador apa = new Actualizar_Producto_Administrador();
                apa.setVisible(true);
                dispose();
            }
        });
        volverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menu_administrador ma = new menu_administrador();
                ma.setVisible(true);
                dispose();
            }
        });


    }
}
