package main.input;

import java.awt.event.*;

public class InputManager implements KeyListener, MouseListener {
    public KeyEvent keyPressed;
    public boolean mouseClicked;
    public boolean mousePressed;
    public boolean mouseReleased;

    public void resetInput() {
        this.mousePressed = false;
        this.mouseReleased = false;
        this.mouseClicked = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        this.keyPressed = e;
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        this.mouseClicked = true;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        this.mousePressed = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        this.mouseReleased = true;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
