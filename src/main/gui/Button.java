package main.gui;

import main.Game;
import main.math.MathUtils;

import java.awt.*;

public class Button extends Widget {
    protected String title;
    protected boolean hovering;
    protected boolean active;
    protected ClickEvent clickEvent;

    public Button(String title, int x, int y, int width, int height, ClickEvent clickEvent) {
        super(x, y, width, height);
        this.title = title;
        this.clickEvent = clickEvent;
        this.active = true;
    }

    public void setActive(boolean value) {
        this.active = value;
    }

    public void setTitle(String value) {
        this.title = value;
    }

    @Override
    public boolean isColliding(int x, int y) {
        return MathUtils.inRange(x, this.x - (this.width / 2), this.x + (this.width / 2))
                && MathUtils.inRange(y, this.y - (this.height / 2), this.y + (this.height / 2));
    }

    @Override
    public void update(int mouseX, int mouseY) {
        this.hovering = isColliding(mouseX, mouseY);
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.setColor(!this.active ? Color.DARK_GRAY : this.hovering ? Color.LIGHT_GRAY : Color.GRAY);
        g2.fillRect(this.x - (this.width / 2), this.y - (this.height / 2), this.width, this.height);

        g2.setColor(this.active ? Color.WHITE : Color.GRAY);
        g2.setFont(new Font("Helvetia", Font.PLAIN, 20));
        Game.drawCenteredString(g2, this.title, this.x, this.y);
    }

    @Override
    public void onClick() {
        if (this.active) this.clickEvent.onClick(this);
    }

    @FunctionalInterface
    public interface ClickEvent {
        void onClick(Button button);
    }
}
