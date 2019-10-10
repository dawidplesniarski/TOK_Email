import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    private JComboBox schematy;
    private JComboBox kadry;
    private JTable tabela;
    private JPanel panel;

    public GUI() {
        schematy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selection = (String) schematy.getSelectedItem();
                System.out.println(tabela.getColumnCount());


            }
        });
    }

    public JPanel getPanel() {
        return panel;
    }
}
