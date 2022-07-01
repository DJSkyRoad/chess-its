package main.scenes;

import main.Game;
import main.gui.Button;

import java.awt.*;

public class GameOverScene extends Scene {
    private final String titleMessage;
    private final String subtitle;

    public GameOverScene(String titleMessage, String subtitle) {
        this.titleMessage = titleMessage;
        this.subtitle = subtitle;
    }

    @Override
    public void init() {
        this.addWidget(new Button("Back", this.getWidth() / 2, this.getHeight() / 2, 200, 50, (button) -> {
            Game.INSTANCE.setScene(new TitleScene());
        }));
    }

    @Override
    public void draw(Graphics2D g2) {
        this.drawBackground(g2);
        super.draw(g2);

        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Helvetia", Font.BOLD, 50));
        Game.drawCenteredString(g2, this.titleMessage, this.getWidth() / 2, 100);
        Game.drawCenteredString(g2, this.subtitle, this.getWidth() / 2, 200);
    }
}
