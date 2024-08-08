import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Clase menu del admistrador
 * @author Isaac VALENZUELA
 * @version 1.0.0
 * @since 27/07/2024
 */
public class menu_administrador extends JFrame {
    private JPanel panel1;
    private JButton IRButton;
    private JButton IRButton1;
    private JButton volverButton;

    public menu_administrador(){
        setTitle("Administrador");
        setSize(600,600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(panel1);

        IRButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Administracion_Cajeros cd = new Administracion_Cajeros();
                cd.setVisible(true);
                dispose();
            }
        });
        IRButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Administrador_Productos al = new Administrador_Productos();
                al.setVisible(true);
                dispose();
            }
        });

        volverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Login al = new Login();
                al.setVisible(true);
                dispose();
            }
        });

    }
}
