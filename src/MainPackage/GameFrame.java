package MainPackage;
import javax.swing.*;
import java.awt.*;


public class GameFrame extends JFrame {

    //yeah, it's pretty much the JEasyFrame thing but the view is added via a method
    //which was intended for some sort of optional fullscreen support
    //but this idea was not implemented in time.

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
