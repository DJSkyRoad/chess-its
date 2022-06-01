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
        this.addButton(new Button("Go back", Game.panelSize / 2, Game.panelSize / 2, 200, 50, (button) -> Game.INSTANCE.setScene(new TitleScene())));
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, this.getWidth(), this.getHeight());
        super.draw(g2);

        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Helvetia", Font.BOLD, 50));
        Game.drawCenteredString(g2, this.titleMessage, this.getWidth() / 2, 100);
        Game.drawCenteredString(g2, this.subtitle, this.getWidth() / 2, 200);
    }
}