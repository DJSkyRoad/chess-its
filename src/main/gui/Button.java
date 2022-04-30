package main.gui;

import main.Game;
import main.math.MathUtils;

import java.awt.*;

public class Button {
    private String title;
    private int x;
    private int y;
    private int width;
    private int height;
    private ClickEvent clickEvent;

    public Button(String title, int x, int y, int width, int height, ClickEvent clickEvent) {
        this.title = title;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.clickEvent = clickEvent;
    }

    public boolean isColliding(int x, int y) {
        return MathUtils.inRange(x, this.x - (this.width / 2), this.x + (this.width / 2))
                && MathUtils.inRange(y, this.y - (this.height / 2), this.y + (this.height / 2));
    }

    public void draw(Graphics2D g2) {
        g2.setColor(Color.GRAY);
        g2.fillRect(this.x - (this.width / 2), this.y - (this.height / 2), this.width, this.height);

        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Helvetia", Font.PLAIN, 20));
        Game.drawCenteredString(g2, this.title, this.x, this.y);
    }

    public void onClick() {
        this.clickEvent.onClick(this);
    }
}
