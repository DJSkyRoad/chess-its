package main.scenes;

import main.gui.Widget;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public abstract class Scene {
    private int width;
    private int height;
    private List<Widget> widgets = new ArrayList<>();

    public void init() {}

    private List<Widget> getWidgets() {
        return new ArrayList<>(this.widgets);
    }

    public void draw(Graphics2D g2) {
        for (Widget widget : this.getWidgets()) widget.draw(g2);
    }

    public void resize(int width, int height) {
        this.width = width;
        this.height = height;
        this.clearWidgets();
        this.init();
    }

    protected void addWidget(Widget widget) {
        this.widgets.add(widget);
    }

    protected void clearWidgets() {
        this.widgets.clear();
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public void onMouseHover(int x, int y) {
        for (Widget widget : this.getWidgets()) {
            widget.update(x, y);
        }
    }

    public void onMouseClick(int x, int y) {
        for (Widget widget : this.getWidgets()) {
            widget.onClick();
        }
    }

    public void onMousePress(int x, int y) {
        for (Widget widget : this.getWidgets()) {
            if (widget.isColliding(x, y)) widget.onMouseDown();
        }
    }

    public void onMouseRelease(int x, int y) {
        for (Widget widget : this.getWidgets()) {
            widget.onMouseUp();
        }
    }

    public void onKeyPressed(KeyEvent event) {
        for (Widget widget : this.getWidgets()) {
            widget.onKeyPressed(event);
        }
    }

    protected void drawBackground(Graphics2D g2) {
        g2.setColor(new Color(0x414141));
        g2.fillRect(0, 0, this.getWidth(), this.getHeight());
    }

    public static void drawCenteredString(Graphics2D g2, String text, int x, int y) {
        FontMetrics metrics = g2.getFontMetrics(g2.getFont());
        x = x - metrics.stringWidth(text) / 2;
        y = y - (metrics.getHeight() / 2) + metrics.getAscent();
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.drawString(text, x, y);
    }
}
