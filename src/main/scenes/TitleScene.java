package main.scenes;

import main.Game;
import main.gui.Button;

import java.awt.*;

public class TitleScene extends Scene {
    @Override
    public void init() {
        this.addWidget(new Button("Host Game", Game.panelSize / 2, Game.panelSize / 2, 200, 50, (button) -> {
            Game.INSTANCE.startServer();
            Game.INSTANCE.setScene(new LoadingScene("Waiting for Opponent...", () -> {
                Game.INSTANCE.getConnection().ifPresent((c) -> {
                    Game.INSTANCE.setScene(new HostMenuScene());
                });
            }));
        }));
        this.addWidget(new Button("Join Game", Game.panelSize / 2, Game.panelSize / 2 + 60, 200, 50, (button) -> {
            Game.INSTANCE.setScene(new JoinMenuScene());
        }));
        this.addWidget(new Button("Play Offline", Game.panelSize / 2, Game.panelSize / 2 + 120, 200, 50, (button) -> {
            Game.INSTANCE.setScene(new OfflineMenuScene());
        }));
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, this.getWidth(), this.getHeight());
        super.draw(g2);

        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Helvetia", Font.BOLD, 100));
        Game.drawCenteredString(g2, "Chess", Game.panelSize / 2, 100);
    }
}
