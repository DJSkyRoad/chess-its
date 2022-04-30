package main.scenes;

import main.gui.Button;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class Scene {
    private int width;
    private int height;
    private List<Button> buttons = new ArrayList<>();

    public void init() {}

    public void draw(Graphics2D g2) {
        for (Button button : this.buttons) button.draw(g2);
    }

    public void resize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    protected void addButton(Button button) {
        this.buttons.add(button);
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public void onMouseHover(int x, int y) {}

    public void onMouseClick(int x, int y) {
        for (Button button : this.buttons) {
            if (button.isColliding(x, y)) button.onClick();
        }
    }
}
