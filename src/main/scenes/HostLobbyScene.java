package main.scenes;

import main.Game;
import main.gui.Button;
import main.networking.packet.StartGamePacket;

import java.awt.*;

public class HostLobbyScene extends OnlineLobbyScene {
    private Button startButton;

    public HostLobbyScene() {
        super();
        this.playerFaction = GameScene.Faction.WHITE;
    }

    @Override
    public void init() {
        super.init();
        this.startButton = new Button("Start Game", Game.panelSize / 2, Game.panelSize / 2, 200, 50, (button) -> {
            Game.INSTANCE.setScene(new GameScene(GameScene.GameMode.PVP_ONLINE, this.getPlayerFaction()));
            this.getConnection().sendPacket(new StartGamePacket(this.getPlayerFaction().opposite()));
        });
        this.startButton.setActive(this.isReady());
        this.addWidget(this.startButton);
    }

    @Override
    public void draw(Graphics2D g2) {
        this.startButton.setActive(this.isReady());

        super.draw(g2);

        g2.setFont(new Font("Helvetia", Font.PLAIN, 20));
        Game.drawCenteredString(g2, this.isReady() ? "Ready" : "Waiting for Opponent...", Game.panelSize / 2, 150);
    }
}
