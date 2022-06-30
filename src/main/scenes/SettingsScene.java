package main.scenes;

import main.Game;
import main.gui.Button;

import java.awt.*;


public class SettingsScene extends Scene {
    @Override
    public void init() {
        super.init();
        this.addWidget(new Button("Quit", this.getWidth() / 2, this.getHeight() / 2 + 40, 200, 50, (button) -> {
            Game.INSTANCE.setScene(new TitleScene());
        }));
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, this.getWidth(), this.getHeight());
        super.draw(g2);

        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Helvetia", Font.BOLD, 50));
        Game.drawCenteredString(g2, "Settings", this.getWidth() / 2, 100);
    }
}
