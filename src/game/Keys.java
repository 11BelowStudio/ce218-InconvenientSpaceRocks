package game;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Keys extends KeyAdapter implements Controller {
    Action action;
    public Keys() {
        action = new Action();
    }

    public Action action() {
        // this is defined to comply with the standard interface
        return action;
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        action.theAnyButton = true;
        switch (key) {
            case KeyEvent.VK_UP:
                action.thrust = 1;
                break;
            case KeyEvent.VK_LEFT:
                action.turn = -1;
                break;
            case KeyEvent.VK_RIGHT:
                action.turn = 1;
                break;
            case KeyEvent.VK_SPACE:
                action.shoot = true;
                break;
            case KeyEvent.VK_DOWN:
                action.warp = true;
                break;
            case KeyEvent.VK_B:
                action.bomb = true;
                break;
        }
    }

    public void keyReleased(KeyEvent e) {
        //these are all off when the key is released
        int key = e.getKeyCode();
        action.theAnyButton = false;
        switch (key) {
            case KeyEvent.VK_UP:
                action.thrust = 0;
                break;
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_RIGHT:
                action.turn = 0;
                break;
            case KeyEvent.VK_SPACE:
                action.shoot = false;
                break;
            case KeyEvent.VK_DOWN:
                action.warp = false;
                break;
            case KeyEvent.VK_B:
                action.bomb = false;
                break;
        }
    }
}