import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
                CRUD_administrador cd = new CRUD_administrador();
                cd.setVisible(true);
                dispose();
            }
        });
        IRButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                administrador_login al = new administrador_login();
                al.setVisible(true);
                dispose();
            }
        });

        volverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                administrador_login al = new administrador_login();
                al.setVisible(true);
                dispose();
            }
        });

    }
}
