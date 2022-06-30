package main.input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseInput implements MouseListener {
    public boolean mouseClicked;
    public boolean mousePressed;
    public boolean mouseReleased;

    public void resetInput() {
        this.mousePressed = false;
        this.mouseReleased = false;
        this.mouseClicked = false;
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
