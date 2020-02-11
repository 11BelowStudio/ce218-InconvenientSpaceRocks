package lab2;

import utilities.JEasyFrame;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class HelloKey extends KeyAdapter {

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println(e);
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT) {
            System.out.println("Left");
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        System.out.println(e);
    }

    public static void main(String[] args) {
        JEasyFrame frame = new JEasyFrame(new DummyComponent(), "HelloKey");
        frame.addKeyListener(new HelloKey());
    }

    public static class DummyComponent extends JComponent { }
}