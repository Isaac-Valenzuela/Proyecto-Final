import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class menu_login extends JFrame {
    private JPanel panel1;
    private JButton IRButton;
    private JButton IRButton1;

    public menu_login(){
        setTitle("Login");
        setSize(600,600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(panel1);

        IRButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cliente_login c = new cliente_login();
                c.setVisible(true);
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

    }
}
