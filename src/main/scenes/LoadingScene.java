package main.scenes;

import main.Game;

import java.awt.*;

public class LoadingScene extends Scene {
    private final String message;
    private final CloseFunction tryClose;

    public LoadingScene(String message, CloseFunction tryClose) {
        this.message = message;
        this.tryClose = tryClose;
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, this.getWidth(), this.getHeight());
        super.draw(g2);

        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Helvetia", Font.PLAIN, 20));
        Game.drawCenteredString(g2, this.message, Game.panelSize / 2, Game.panelSize / 2);

        this.tryClose.execute();
    }

    @FunctionalInterface
    public interface CloseFunction {
        void execute();
    }
}
