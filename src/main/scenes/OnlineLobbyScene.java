package main.scenes;

import main.Game;
import main.gui.Button;
import main.networking.connection.Connection;
import main.networking.packet.SetPlayerFactionPacket;

import java.awt.*;

public abstract class OnlineLobbyScene extends Scene {
    protected GameScene.Faction playerFaction;
    private Connection connection;
    private Button factionButton;
    private boolean ready;

    public OnlineLobbyScene() {
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
        this.factionButton = new Button(this.playerFaction.toString(), this.getWidth() / 2, this.getHeight() / 2 + 100, 200, 50, (button) -> {
            this.setPlayerFaction(this.playerFaction.opposite());
            this.connection.sendPacket(new SetPlayerFactionPacket(this.playerFaction.opposite()));
        });
        this.addWidget(this.factionButton);
        this.addWidget(new Button("Back", this.getWidth() / 2, this.getHeight() / 2 + 160, 200, 50, (button) -> {
            Game.INSTANCE.setScene(new TitleScene());
            Game.INSTANCE.closeConnection();
        }));
    }

    @Override
    public void draw(Graphics2D g2) {
        this.drawBackground(g2);
        super.draw(g2);

        g2.setColor(Color.WHITE);
        g2.setFont(Game.INSTANCE.font.deriveFont(Font.BOLD, 50));
        drawCenteredString(g2, "Online Game", this.getWidth() / 2, 100);

        g2.setFont(Game.INSTANCE.font.deriveFont(Font.PLAIN, 20));
        drawCenteredString(g2, "Player Faction", this.getWidth() / 2, this.getHeight() / 2 + 60);
    }
}
