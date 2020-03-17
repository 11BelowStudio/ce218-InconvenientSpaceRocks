package GamePackage;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ControllerAndInputAdapter extends ControllerAdapter implements MouseListener, KeyListener {


    @Override
    public void keyTyped(KeyEvent e) {action.releasedTheAnyButton();}

    @Override
    public void keyPressed(KeyEvent e) { action.pressedTheAnyButton(); }

    @Override
    public void keyReleased(KeyEvent e) { action.releasedTheAnyButton();}

    @Override
    public void mouseClicked(MouseEvent e) {action.releasedTheAnyButton();}

    @Override
    public void mousePressed(MouseEvent e) { action.pressedTheAnyButton();}

    @Override
    public void mouseReleased(MouseEvent e) {action.releasedTheAnyButton();}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}
