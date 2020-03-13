package MainPackage;

import java.awt.event.*;

public class PlayerController extends ControllerAdapter {
    //Action action;
    public PlayerController() {
        action = new Action();
    }

    public Action action() {
        // this is defined to comply with the standard interface
        return action;
    }

    public void keyPressed(KeyEvent e) {
        action.theAnyButton = true;
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
        action.theAnyButton = false;
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
        //action.theAnyButton = true;
        action.theAnyButton = false;
        action.clicked = false;
        if (e.getSource() instanceof GameFrame){
            action.theAnyButton = true;
            action.clicked = true;
            action.clickLocation = e.getPoint();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        /*
        action.theAnyButton = true;
        if (e.getSource() instanceof GameFrame){
            action.mousePressed = true;
            action.mousePressLocation = e.getPoint();
        } */
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        action.theAnyButton = false;
        //action.clicked = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }



}