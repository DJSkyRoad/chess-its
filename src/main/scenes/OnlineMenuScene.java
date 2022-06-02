package main.scenes;

import main.Game;
import main.gui.Button;
import main.networking.connection.Connection;
import main.networking.packet.SetPlayerFactionPacket;

import java.awt.*;

public class OnlineMenuScene extends Scene {
    protected GameScene.Faction playerFaction;
    private Connection connection;
    protected Button factionButton;

    public OnlineMenuScene() {
        this.connection = Game.INSTANCE.getConnection();
    }

    public void setPlayerFaction(GameScene.Faction faction) {
        this.playerFaction = faction;
        this.factionButton.setTitle(faction.toString());
    }

    @Override
    public void init() {
        this.factionButton = new Button(this.playerFaction.toString(), Game.panelSize / 2, Game.panelSize / 2 + 100, 200, 50, (button) -> {
            this.playerFaction = this.playerFaction.opposite();
            button.setTitle(this.playerFaction.toString());
            this.connection.sendPacket(new SetPlayerFactionPacket(this.playerFaction.opposite()));
        });
        this.addButton(this.factionButton);
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, this.getWidth(), this.getHeight());
        super.draw(g2);

        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Helvetia", Font.BOLD, 50));
        Game.drawCenteredString(g2, "Online Game", Game.panelSize / 2, 100);

        g2.setFont(new Font("Helvetia", Font.PLAIN, 20));
        Game.drawCenteredString(g2, this.connection.isConnected() ? "Connected" : "Connecting...", Game.panelSize / 2, 150);
        Game.drawCenteredString(g2, "Player Faction", Game.panelSize / 2, Game.panelSize / 2 + 60);
    }
}
