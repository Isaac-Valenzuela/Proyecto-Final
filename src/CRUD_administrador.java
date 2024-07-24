import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CRUD_administrador extends JFrame{
    private JPanel panel1;
    private JButton crearButton;
    private JButton verButton;
    private JButton eliminarButton;
    private JButton actualizarButton;
    private JButton volverButton;

    public CRUD_administrador(){
        setTitle("Administrador");
        setSize(600,600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(panel1);

        crearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                crear_cliente_administrador cca = new crear_cliente_administrador();
                cca.setVisible(true);
                dispose();
            }
        });
        verButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ver_cliente_administrador vca = new ver_cliente_administrador();
                vca.setVisible(true);
                dispose();
            }
        });
        eliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminar_cliente_administrador eca = new eliminar_cliente_administrador();
                eca.setVisible(true);
                dispose();
            }
        });
        actualizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizar_cliente_administrador aca = new actualizar_cliente_administrador();
                aca.setVisible(true);
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
