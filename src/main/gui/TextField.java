package main.gui;

import main.math.MathUtils;
import main.scenes.Scene;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class TextField extends Widget {
    protected String content;
    private boolean hovering;
    protected boolean active;
    private BufferedImage image;
    private int cursorPos;
    protected int maxLength;

    public TextField(String content, int x, int y, int width, int height) {
        super(x, y, width, height);
        this.content = content;
        this.cursorPos = content.length();
        this.maxLength = 15;

        try {
            InputStream inputStream = Objects.requireNonNull(getClass().getResourceAsStream("/resources/text_field.png"));
            this.image = ImageIO.read(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        this.active = this.hovering;
    }

    @Override
    public void onMouseDown() {

    }

    @Override
    public void onMouseUp() {

    }

    @Override
    public void onKeyPressed(KeyEvent event) {
        if (this.active) {
            if (event.getKeyCode() == KeyEvent.VK_LEFT && this.cursorPos > 0) --this.cursorPos;
            else if (event.getKeyCode() == KeyEvent.VK_RIGHT && this.cursorPos < this.content.length()) ++this.cursorPos;
            else {
                if (this.isValidChar(event.getKeyChar()) && this.content.length() < this.maxLength) {
                    this.content = this.content.substring(0, this.cursorPos) + event.getKeyChar() + this.content.substring(this.cursorPos);
                    ++this.cursorPos;
                }
                else if (event.getKeyChar() == KeyEvent.VK_BACK_SPACE && this.cursorPos > 0) {
                    this.content = this.content.substring(0, this.cursorPos - 1) + this.content.substring(this.cursorPos);
                    --this.cursorPos;
                }
                else if (event.getKeyChar() == KeyEvent.VK_ENTER) this.active = false;
            }
        }
    }

    protected boolean isValidChar(char c) {
        return Character.isLetterOrDigit(c) || c == '.';
    }

    @Override
    public void update(int mouseX, int mouseY) {
        this.hovering = isColliding(mouseX, mouseY);
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.setXORMode(this.hovering || this.active ? new Color(204, 114, 0) : new Color(154, 86, 0));
        Widget.drawCenteredImage(g2, this.image, this.x, this.y, this.width, this.height);
        g2.setPaintMode();

        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Helvetia", Font.PLAIN, 20));
        String message = this.active ? this.content.substring(0, this.cursorPos) + "|" + this.content.substring(this.cursorPos) : this.content;
        Scene.drawCenteredString(g2, message, this.x, this.y);
    }
}
