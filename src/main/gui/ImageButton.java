package main.gui;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class ImageButton extends Button {
    private BufferedImage image;

    public ImageButton(String imagePath, int x, int y, int width, int height, ClickEvent clickEvent) {
        super(imagePath, x, y, width, height, clickEvent);
        try {
            InputStream inputStream = Objects.requireNonNull(getClass().getResourceAsStream(imagePath));
            this.image = ImageIO.read(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.setColor(!this.active ? Color.DARK_GRAY : this.hovering ? Color.LIGHT_GRAY : Color.GRAY);
        g2.fillRect(this.x - (this.width / 2), this.y - (this.height / 2), this.width, this.height);
        Widget.drawCenteredImage(g2, this.image, this.x, this.y, this.width, this.height);
    }
}
