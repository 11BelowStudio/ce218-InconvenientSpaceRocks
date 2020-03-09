package game;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class EscapeListener extends KeyAdapter {

    GameRunner runner;

    EscapeListener(GameRunner r){
        runner = r;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE){
            runner.quitPrompt();
        }
    }
}
