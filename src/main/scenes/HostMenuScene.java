package main.scenes;

import main.Game;
import main.gui.Button;
import main.gui.NumericTextField;
import main.gui.TextField;

import java.awt.*;

public class HostMenuScene extends Scene {
    private TextField port;

    @Override
    public void init() {
        super.init();
        this.port = new NumericTextField(1234, Game.panelSize / 2, Game.panelSize / 2 + 40, 200, 50);
        this.addWidget(this.port);
        this.addWidget(new Button("Host", Game.panelSize / 2, Game.panelSize / 2 + 100, 200, 50, (button) -> {
            int p;
            try {
                p = Integer.parseInt(this.port.getContent());
            }
            catch (NumberFormatException e) {
                p = 1234;
            }
            Game.INSTANCE.startServer(p);
            Game.INSTANCE.setScene(new LoadingScene("Waiting for Opponent...", () -> {
                Game.INSTANCE.getConnection().ifPresent((c) -> {
                    Game.INSTANCE.setScene(new HostLobbyScene());
                });
            }));
        }));
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, this.getWidth(), this.getHeight());
        super.draw(g2);

        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Helvetia", Font.BOLD, 50));
        Game.drawCenteredString(g2, "Join Online Game", Game.panelSize / 2, 100);

        g2.setFont(new Font("Helvetia", Font.PLAIN, 20));
        Game.drawCenteredString(g2, "Port", Game.panelSize / 2, Game.panelSize / 2);
    }
}
