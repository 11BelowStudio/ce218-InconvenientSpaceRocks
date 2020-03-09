package game;

import utilities.HighScoreHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.rmi.server.ExportException;

import static game.Constants.DELAY;


public class GameFrame extends JFrame {


    public GameFrame() {
        this.setTitle("blideo bame");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void addView(View v){
        this.add(v,BorderLayout.CENTER);
        this.setVisible(true);
        pack();
    }


}
