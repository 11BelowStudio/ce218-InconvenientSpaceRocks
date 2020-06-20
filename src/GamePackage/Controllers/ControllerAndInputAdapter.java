package GamePackage.Controllers;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ControllerAndInputAdapter extends ControllerAdapter implements MouseListener, KeyListener {
    //controllerAdapter but its also KeyAdapter and MouseAdapter as well! (with some default methods)

    @Override
    public void keyTyped(KeyEvent e) { }

    @Override
    public void keyPressed(KeyEvent e) { action.pressedTheAnyButton(); }

    @Override
    public void keyReleased(KeyEvent e) { action.releasedTheAnyButton();}

    @Override
    public void mouseClicked(MouseEvent e) { }

    @Override
    public void mousePressed(MouseEvent e) { action.pressedTheAnyButton();}

    @Override
    public void mouseReleased(MouseEvent e) {action.releasedTheAnyButton();}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}
