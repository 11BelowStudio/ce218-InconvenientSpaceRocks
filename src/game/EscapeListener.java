package game;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class EscapeListener extends KeyAdapter {

    private GameRunner runner;

    EscapeListener(GameRunner r){ runner = r; }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE){
            runner.quitPrompt();
        }
    }
}
