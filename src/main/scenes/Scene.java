package main.scenes;

import main.gui.Button;
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

    public void draw(Graphics2D g2) {
        for (Widget widget : this.widgets) widget.draw(g2);
    }

    public void resize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    protected void addWidget(Widget widget) {
        this.widgets.add(widget);
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public void onMouseHover(int x, int y) {
        for (Widget widget : this.widgets) {
            widget.update(x, y);
        }
    }

    public void onMouseClick(int x, int y) {
        for (Widget widget : this.widgets) {
            if (widget.isColliding(x, y)) widget.onClick();
        }
    }

    public void onMousePress(int x, int y) {

    }

    public void onMouseRelease(int x, int y) {

    }

    public void onKeyPressed(KeyEvent event) {
        for (Widget widget : this.widgets) {
            widget.onKeyPressed(event);
        }
    }
}
