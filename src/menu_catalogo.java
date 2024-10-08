import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Clase menu del cajero
 * @author Isaac VALENZUELA
 * @version 1.0.0
 * @since 27/07/2024
 */
public class menu_catalogo extends JFrame{
    private JPanel panel1;
    private JButton facturarButton;
    private JButton reportesButton;
    private JButton cerrarSesionButton;

    public menu_catalogo(){
        setTitle("Menu");
        setSize(600,600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(panel1);

        facturarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Cajero c = new Cajero();
                c.setVisible(true);
                dispose();
            }
        });
        reportesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ReporteVentas r = new ReporteVentas();
                r.setVisible(true);
                dispose();
            }
        });
        cerrarSesionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Login ml = new Login();
                ml.setVisible(true);
                dispose();
            }
        });
    }
}
