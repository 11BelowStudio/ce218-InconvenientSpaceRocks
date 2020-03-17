package GamePackage;

import java.awt.event.*;

public class PlayerController extends ControllerAndInputAdapter {
    //how the player does most inputs

    public PlayerController() { super(); }

    public void keyPressed(KeyEvent e) {
        action.pressedTheAnyButton();
        int key = e.getKeyCode();
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
            case KeyEvent.VK_P:
                action.p = true;
                break;
        }
    }

    public void keyReleased(KeyEvent e) {
        //these are all off when the key is released
        action.releasedTheAnyButton();
        int key = e.getKeyCode();
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
            case KeyEvent.VK_P:
                action.p = false;
                break;
        }
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        action.releasedTheAnyButton();
        action.clicked = false;
        //doesn't count unless the click was in the GameFrame
        if (e.getSource() instanceof GameFrame){
            action.pressedTheAnyButton();
            action.clicked = true;
            action.clickLocation = e.getPoint();
        }
    }


}