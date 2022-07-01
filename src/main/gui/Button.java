package main.gui;

import main.AudioPlayer;
import main.Game;
import main.math.MathUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class Button extends Widget {
    protected String title;
    protected boolean hovering;
    protected boolean active;
    protected ClickEvent clickEvent;
    protected BufferedImage image;

    public Button(String title, int x, int y, int width, int height, ClickEvent clickEvent) {
        super(x, y, width, height);
        this.title = title;
        this.clickEvent = clickEvent;
        this.active = true;
        try {
            InputStream inputStream = Objects.requireNonNull(getClass().getResourceAsStream("/resources/button.png"));
            this.image = ImageIO.read(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BufferedImage tintImage(BufferedImage image, Color color) {
        BufferedImage tintedImage = new BufferedImage(
                image.getWidth(),
                image.getHeight(),
                BufferedImage.TRANSLUCENT);

        Graphics graphics = tintedImage.createGraphics();
        Color newColor = new Color(color.getRed(), color.getBlue(), color.getGreen(), 0);
        graphics.setXORMode(newColor);
        graphics.drawImage(tintedImage, 0, 0, null);
        graphics.dispose();
        return tintedImage;
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
        g2.setXORMode(!this.active ? Color.DARK_GRAY : this.hovering ? new Color(133, 78, 8) : new Color(143, 88, 0));
        Widget.drawCenteredImage(g2, this.image, this.x, this.y, this.width, this.height);
        g2.setPaintMode();

        g2.setColor(this.active ? Color.WHITE : Color.GRAY);
        g2.setFont(new Font("Helvetia", Font.PLAIN, 20));
        Game.drawCenteredString(g2, this.title, this.x, this.y);
    }

    @Override
    public void onClick() {
        if (this.active) {
            Game.INSTANCE.playSound(AudioPlayer.BUTTON_CLICK);
            this.clickEvent.onClick(this);
        }
    }

    @FunctionalInterface
    public interface ClickEvent {
        void onClick(Button button);
    }
}
