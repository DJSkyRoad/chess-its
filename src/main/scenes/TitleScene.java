package main.scenes;

import main.Game;
import main.gui.Button;

import java.awt.*;

public class TitleScene extends Scene {
    @Override
    public void init() {
        this.addButton(new Button("Start Game", Game.panelSize / 2, Game.panelSize / 2, 200, 50, (button) -> {
            Game.INSTANCE.setScene(new GameScene());
        }));
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, this.getWidth(), this.getHeight());
        super.draw(g2);

        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Helvetia", Font.BOLD, 100));
        Game.drawCenteredString(g2, "Chess", this.getWidth() / 2, 100);
    }
}
