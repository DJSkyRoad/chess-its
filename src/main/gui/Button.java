package main.gui;

import main.sound.AudioPlayer;
import main.Game;
import main.math.MathUtils;
import main.scenes.Scene;

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
    protected boolean pressed;

    private BufferedImage image;
    private BufferedImage hoveredImage;
    private BufferedImage pressedImage;
    private BufferedImage inactiveImage;

    public Button(String title, int x, int y, int width, int height, ClickEvent clickEvent) {
        super(x, y, width, height);
        this.title = title;
        this.clickEvent = clickEvent;
        this.active = true;
        try {
            InputStream inputStream = Objects.requireNonNull(getClass().getResourceAsStream("/resources/button.png"));
            this.image = this.tintImage(ImageIO.read(inputStream), new Color(218, 90, 0));
            inputStream = Objects.requireNonNull(getClass().getResourceAsStream("/resources/button.png"));
            this.hoveredImage = this.tintImage(ImageIO.read(inputStream), new Color(255, 90, 0));
            inputStream = Objects.requireNonNull(getClass().getResourceAsStream("/resources/button.png"));
            this.inactiveImage = this.tintImage(ImageIO.read(inputStream), new Color(84, 84, 84));
            inputStream = Objects.requireNonNull(getClass().getResourceAsStream("/resources/button_pressed.png"));
            this.pressedImage = this.tintImage(ImageIO.read(inputStream), new Color(217, 90, 1));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setActive(boolean value) {
        this.active = value;
    }

    public void setTitle(String value) {
        this.title = value;
    }

    @Override
    public void update(int mouseX, int mouseY) {
        this.hovering = this.isColliding(mouseX, mouseY);
    }

    @Override
    public void draw(Graphics2D g2) {
        BufferedImage img = !this.active ? this.inactiveImage : this.pressed ? this.pressedImage
                : this.hovering ? this.hoveredImage : this.image;
        Widget.drawCenteredImage(g2, img, this.x, this.y, this.width, this.height);
        g2.setColor(this.active ? Color.WHITE : Color.GRAY);
        g2.setFont(Game.INSTANCE.font.deriveFont(Font.PLAIN, 20));
        Scene.drawCenteredString(g2, this.title, this.x, this.y);
    }

    @Override
    public void onClick() {
        if (this.active && this.hovering) {
            Game.INSTANCE.audioPlayer.playSound(AudioPlayer.BUTTON_CLICK);
            this.clickEvent.onClick(this);
            this.pressed = false;
        }
    }

    @Override
    public void onMouseDown() {
        if (this.active) this.pressed = true;
    }

    @Override
    public void onMouseUp() {
        this.pressed = false;
    }

    @FunctionalInterface
    public interface ClickEvent {
        void onClick(Button button);
    }
}
