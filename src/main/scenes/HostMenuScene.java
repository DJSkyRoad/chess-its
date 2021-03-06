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
        if (this.port == null) {
            this.port = new NumericTextField(1234, this.getWidth() / 2, this.getHeight() / 2 + 40, 200, 50);
        } else {
            this.port.resize(this.getWidth() / 2, this.getHeight() / 2 + 40, 200, 50);
        }
        this.addWidget(this.port);
        this.addWidget(new Button("Host", this.getWidth() / 2, this.getHeight() / 2 + 100, 200, 50, (button) -> {
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
        this.addWidget(new Button("Back", this.getWidth() / 2, this.getHeight() / 2 + 160, 200, 50, (button) -> {
            Game.INSTANCE.setScene(new TitleScene());
        }));
    }

    @Override
    public void draw(Graphics2D g2) {
        this.drawBackground(g2);
        super.draw(g2);

        g2.setColor(Color.WHITE);
        g2.setFont(Game.INSTANCE.font.deriveFont(Font.BOLD, 50));
        drawCenteredString(g2, "Join Online Game", this.getWidth() / 2, 100);

        g2.setFont(Game.INSTANCE.font.deriveFont(Font.PLAIN, 20));
        drawCenteredString(g2, "Port", this.getWidth() / 2, this.getHeight() / 2);
    }
}
