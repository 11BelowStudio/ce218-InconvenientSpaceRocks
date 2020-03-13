package MainPackage;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ControllerAdapter implements Controller, MouseListener, KeyListener {

    protected Action action;

    @Override
    public Action action() {
        return action;
    }

    @Override
    public void noAction() {
        action.noAction();
    }

    @Override
    public void revive() {}

    @Override
    public void noClick() {
        action.clicked = false;
    }

    @Override
    public void setEnemyShip(EnemyShip e) {}

    @Override
    public boolean theAnyButton() {
        return action.theAnyButton();
    }

    @Override
    public boolean isClicked() {
        return action.clicked;
    }

    @Override
    public Point clickLocation() {
        if (isClicked()) {
            noClick();
            return action.clickLocation;
        } else{
            return null;
        }
    }

    @Override
    public boolean shot() {
        return action.shoot;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}
