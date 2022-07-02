package main.scenes;

import main.Game;
import main.gui.Button;

import java.awt.*;

public class LoadingScene extends Scene {
    private final String message;
    private final CloseFunction tryClose;

    public LoadingScene(String message, CloseFunction tryClose) {
        this.message = message;
        this.tryClose = tryClose;
    }

    @Override
    public void init() {
        this.addWidget(new Button("Cancel", this.getWidth() / 2, this.getHeight() / 2 + 60, 200, 50, (button) -> {
            Game.INSTANCE.setScene(new TitleScene());
            Game.INSTANCE.closeConnection();
        }));
    }

    @Override
    public void draw(Graphics2D g2) {
        this.drawBackground(g2);
        super.draw(g2);

        g2.setColor(Color.WHITE);
        g2.setFont(Game.INSTANCE.font.deriveFont(Font.PLAIN, 20));
        drawCenteredString(g2, this.message, this.getWidth() / 2, this.getHeight() / 2);

        this.tryClose.execute();
    }

    @FunctionalInterface
    public interface CloseFunction {
        void execute();
    }
}
