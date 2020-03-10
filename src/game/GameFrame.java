package game;


import javax.swing.*;
import java.awt.*;



public class GameFrame extends JFrame {


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
