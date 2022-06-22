package main.gui;

import main.Game;
import main.math.MathUtils;

import java.awt.*;
import java.awt.event.KeyEvent;

public class TextField extends Widget {
    protected String content;
    private int x;
    private int y;
    private int width;
    private int height;
    private boolean hovering;
    protected boolean active;

    public TextField(String content, int x, int y, int width, int height) {
        this.content = content;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public String getContent() {
        return this.content;
    }

    @Override
    public boolean isColliding(int x, int y) {
        return MathUtils.inRange(x, this.x - (this.width / 2), this.x + (this.width / 2))
                && MathUtils.inRange(y, this.y - (this.height / 2), this.y + (this.height / 2));
    }

    @Override
    public void onClick() {
        this.active = true;
    }

    @Override
    public void onKeyPressed(KeyEvent event) {
        if (this.active) {
            if (Character.isLetterOrDigit(event.getKeyChar()) || event.getKeyChar() == '.') this.content += event.getKeyChar();
            else if (event.getKeyChar() == KeyEvent.VK_BACK_SPACE && this.content.length() > 0) this.content = this.content.substring(0, this.content.length() - 1);
            else if (event.getKeyChar() == KeyEvent.VK_ENTER) this.active = false;
        }
    }

    @Override
    public void update(int mouseX, int mouseY) {
        this.hovering = isColliding(mouseX, mouseY);
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.setColor(this.hovering || this.active ? Color.LIGHT_GRAY : Color.GRAY);
        g2.drawRect(this.x - (this.width / 2), this.y - (this.height / 2), this.width, this.height);

        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Helvetia", Font.PLAIN, 20));
        Game.drawCenteredString(g2, this.content, this.x, this.y);
    }
}
