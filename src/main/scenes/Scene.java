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
            if (widget.isColliding(x, y)) widget.onClick();
        }
    }

    public void onMousePress(int x, int y) {

    }

    public void onMouseRelease(int x, int y) {

    }

    public void onKeyPressed(KeyEvent event) {
        for (Widget widget : this.getWidgets()) {
            widget.onKeyPressed(event);
        }
    }
}
