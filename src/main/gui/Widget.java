package main.gui;


import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public abstract class Widget {
    protected int x;
    protected int y;
    protected int width;
    protected int height;

    public Widget(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public abstract void draw(Graphics2D g2);
    public abstract void update(int x, int y);
    public abstract boolean isColliding(int x, int y);
    public abstract void onClick();
    public void onKeyPressed(KeyEvent event) {}

    public void resize(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    protected static void drawCenteredImage(Graphics g, BufferedImage image, int x, int y, int width, int height) {
        g.drawImage(image, x - width / 2, y - height / 2, width, height, null);
    }
}
