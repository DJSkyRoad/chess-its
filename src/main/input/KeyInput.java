package main.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyInput implements KeyListener {
    public KeyEvent keyPressed;

    @Override
    public void keyTyped(KeyEvent e) {
        this.keyPressed = e;
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
