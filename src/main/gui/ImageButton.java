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
        super("", x, y, width, height, clickEvent);
        try {
            InputStream inputStream = Objects.requireNonNull(getClass().getResourceAsStream(imagePath));
            this.image = ImageIO.read(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        super.draw(g2);
        Widget.drawCenteredImage(g2, this.image, this.x, this.y, this.width, this.height);
    }
}
