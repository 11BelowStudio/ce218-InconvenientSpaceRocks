package MainPackage;
import javax.swing.*;
import java.awt.*;


public class GameFrame extends JFrame {

    //yeah, it's pretty much the JEasyFrame thing

    public GameFrame() {
        this.setTitle("Inconvenient Space Rocks (In Space!)");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void addView(View v){
        this.add(v,BorderLayout.CENTER);
        this.setVisible(true);
        pack();
    }


}
