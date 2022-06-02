package main.scenes;

import main.Game;
import main.gui.Button;
import main.networking.connection.Connection;
import main.networking.packet.SetPlayerFactionPacket;

import java.awt.*;

public abstract class OnlineMenuScene extends Scene {
    protected GameScene.Faction playerFaction;
    private Connection connection;
    private Button factionButton;
    private boolean ready;

    public OnlineMenuScene() {
        this.connection = Game.INSTANCE.getConnection().get();
    }

    protected Connection getConnection() {
        return this.connection;
    }

    protected boolean isReady() {
        return this.ready;
    }

    public void setReady(boolean value) {
        this.ready = value;
    }

    protected Button getFactionButton() {
        return this.factionButton;
    }

    protected GameScene.Faction getPlayerFaction() {
        return this.playerFaction;
    }

    public void setPlayerFaction(GameScene.Faction faction) {
        this.playerFaction = faction;
        this.factionButton.setTitle(faction.toString());
    }

    @Override
    public void init() {
        this.factionButton = new Button(this.playerFaction.toString(), Game.panelSize / 2, Game.panelSize / 2 + 100, 200, 50, (button) -> {
            this.setPlayerFaction(this.playerFaction.opposite());
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
        Game.drawCenteredString(g2, "Player Faction", Game.panelSize / 2, Game.panelSize / 2 + 60);
    }
}
