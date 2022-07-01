package main.scenes;

import main.Game;
import main.gui.Button;

import java.awt.*;


public class SettingsScene extends Scene {
    @Override
    public void init() {
        super.init();
        this.addWidget(new Button("Back", this.getWidth() / 2, this.getHeight() / 2, 200, 50, (button) -> {
            Game.INSTANCE.setOverlayScene(null);
        }));
        this.addWidget(new Button("Quit to Title", this.getWidth() / 2, this.getHeight() / 2 + 60, 200, 50, (button) -> {
            Game.INSTANCE.setScene(new TitleScene());
            Game.INSTANCE.setOverlayScene(null);
        }));
    }

    @Override
    public void draw(Graphics2D g2) {
        this.drawBackground(g2);
        super.draw(g2);

        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Helvetia", Font.BOLD, 50));
        Game.drawCenteredString(g2, "Settings", this.getWidth() / 2, 100);
    }
}
