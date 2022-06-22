package main.gui;


import java.awt.*;
import java.awt.event.KeyEvent;

public abstract class Widget {
    public abstract void draw(Graphics2D g2);
    public abstract void update(int x, int y);
    public abstract boolean isColliding(int x, int y);
    public abstract void onClick();
    public void onKeyPressed(KeyEvent event) {

    }
}
