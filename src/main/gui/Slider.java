package main.gui;

import main.math.MathUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class Slider extends Widget {
    private float value;
    private BufferedImage bg;
    private BufferedImage fg;
    private boolean drag;
    private int fgX;
    private DragEvent dragEvent;

    public Slider(float value, int x, int y, int width, int height, DragEvent dragEvent) {
        super(x, y, width, height);

        try {
            InputStream inputStream = Objects.requireNonNull(getClass().getResourceAsStream("/resources/text_field.png"));
            this.bg = this.tintImage(ImageIO.read(inputStream), new Color(218, 90, 0));
            inputStream = Objects.requireNonNull(getClass().getResourceAsStream("/resources/button.png"));
            this.fg = this.tintImage(ImageIO.read(inputStream), new Color(255, 136, 0));
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.value = MathUtils.clamp(value, 0, 1);
        this.fgX = (int)(this.x - this.width / 2 + this.width * this.value);
        this.dragEvent = dragEvent;
    }

    @Override
    public void draw(Graphics2D g2) {
        Widget.drawCenteredImage(g2, this.bg, this.x, this.y, this.width, this.height / 2);
        Widget.drawCenteredImage(g2, this.fg, this.fgX, this.y, this.height, this.height);
    }

    @Override
    public void update(int mouseX, int mouseY) {
        if (this.drag) {
            this.fgX = MathUtils.clamp(mouseX, this.x - this.width / 2, this.x + this.width / 2);
            this.value = (float)(this.fgX - (this.x - this.width / 2)) / this.width;
            this.dragEvent.onDrag(this);
        }
    }

    @Override
    public void onClick() {

    }

    @Override
    public void onMouseDown() {
        this.drag = true;
    }

    @Override
    public void onMouseUp() {
        this.drag = false;
    }

    public float getValue() {
        return this.value;
    }

    @FunctionalInterface
    public interface DragEvent {
        void onDrag(Slider slider);
    }
}
