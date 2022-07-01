package main.scenes;

import main.Game;
import main.gui.Button;
import main.gui.NumericTextField;
import main.gui.TextField;

import java.awt.*;

public class JoinMenuScene extends Scene {
    private TextField ip;
    private TextField port;

    @Override
    public void init() {
        super.init();
        if (this.ip == null) {
            this.ip = new TextField("localhost", this.getWidth() / 2, this.getHeight() / 2 - 20, 200, 50);
        } else {
            this.ip.resize(this.getWidth() / 2, this.getHeight() / 2 - 20, 200, 50);
        }
        if (this.port == null) {
            this.port = new NumericTextField(1234, this.getWidth() / 2, this.getHeight() / 2 + 80, 200, 50);
        } else {
            this.port.resize(this.getWidth() / 2, this.getHeight() / 2 + 80, 200, 50);
        }
        this.addWidget(this.ip);
        this.addWidget(this.port);
        this.addWidget(new Button("Join", this.getWidth() / 2, this.getHeight() / 2 + 140, 200, 50, (button) -> {
            int p;
            try {
                p = Integer.parseInt(this.port.getContent());
            }
            catch (NumberFormatException e) {
                p = 1234;
            }
            Game.INSTANCE.startClient(this.ip.getContent(), p);
            Game.INSTANCE.setScene(new LoadingScene("Waiting for Opponent...", () -> {
                Game.INSTANCE.getConnection().ifPresent((c) -> {
                    Game.INSTANCE.setScene(new GuestLobbyScene());
                });
            }));
        }));
        this.addWidget(new Button("Back", this.getWidth() / 2, this.getHeight() / 2 + 200, 200, 50, (button) -> {
            Game.INSTANCE.setScene(new TitleScene());
        }));
    }

    @Override
    public void draw(Graphics2D g2) {
        this.drawBackground(g2);
        super.draw(g2);

        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Helvetia", Font.BOLD, 50));
        Game.drawCenteredString(g2, "Join Online Game", this.getWidth() / 2, 100);

        g2.setFont(new Font("Helvetia", Font.PLAIN, 20));
        Game.drawCenteredString(g2, "IP", this.getWidth() / 2, this.getHeight() / 2 - 60);
        Game.drawCenteredString(g2, "Port", this.getWidth() / 2, this.getHeight() / 2 + 40);
    }
}
