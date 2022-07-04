package main.scenes;

import main.Game;
import main.gui.Button;

import java.awt.*;

public class SettingsOverlayScene extends SettingsScene {
    @Override
    public void init() {
        super.init();
        this.addWidget(new Button("Back", this.getWidth() / 2, this.getHeight() / 2 + 100, 200, 50, (button) -> {
            Game.INSTANCE.setOverlayScene(null);
        }));
    }

    @Override
    protected void drawBackground(Graphics2D g2) {
        g2.setColor(new Color(0xD5313131, true));
        g2.fillRect(this.getWidth() / 2 - 200, 0, 400, this.getHeight());
    }
}
