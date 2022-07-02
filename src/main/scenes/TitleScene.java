package main.scenes;

import main.Game;
import main.gui.Button;

import java.awt.*;

public class TitleScene extends Scene {
    @Override
    public void init() {
        this.addWidget(new Button("Host Game", this.getWidth() / 2, this.getHeight() / 2, 200, 50, (button) -> {
            Game.INSTANCE.setScene(new HostMenuScene());
        }));
        this.addWidget(new Button("Join Game", this.getWidth() / 2, this.getHeight() / 2 + 60, 200, 50, (button) -> {
            Game.INSTANCE.setScene(new JoinMenuScene());
        }));
        this.addWidget(new Button("Play Offline", this.getWidth() / 2, this.getHeight() / 2 + 120, 200, 50, (button) -> {
            Game.INSTANCE.setScene(new OfflineMenuScene());
        }));
    }

    @Override
    public void draw(Graphics2D g2) {
        this.drawBackground(g2);
        super.draw(g2);

        g2.setColor(Color.WHITE);
        g2.setFont(Game.INSTANCE.font.deriveFont(Font.BOLD, 100));
        drawCenteredString(g2, "Chess", this.getWidth() / 2, 100);
    }
}
