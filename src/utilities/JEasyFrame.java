package utilities;

import javax.swing.*;
import java.awt.*;


//le ce218 sample code has arrived (Provided by Dr Dimitri Ognibene)
public class JEasyFrame extends JFrame {
    public Component comp;
    public JEasyFrame(Component comp, String title) {
        super(title);
        this.comp = comp;
        getContentPane().add(BorderLayout.CENTER, comp);
        pack();
        this.setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        repaint();
    }

}