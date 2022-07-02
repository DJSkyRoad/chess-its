package main.scenes;

import main.Game;
import main.gui.Button;

import java.awt.*;

public class OfflineMenuScene extends Scene {
    private GameScene.GameMode gameMode = GameScene.GameMode.PVP_OFFLINE;
    private GameScene.Faction playerFaction = GameScene.Faction.WHITE;

    @Override
    public void init() {
        this.addWidget(new Button("Start Game", this.getWidth() / 2, this.getHeight() / 2 - 60, 200, 50, (button) -> {
            Game.INSTANCE.setScene(new GameScene(this.gameMode, this.playerFaction));
        }));
        Button factionButton = new Button(this.playerFaction.toString(), this.getWidth() / 2, this.getHeight() / 2 + 140, 200, 50, (button) -> {
            this.playerFaction = this.playerFaction.opposite();
            button.setTitle(this.playerFaction.toString());
        });
        factionButton.setActive(this.gameMode != GameScene.GameMode.PVP_OFFLINE);
        this.addWidget(new Button(this.gameMode.toString(), this.getWidth() / 2, this.getHeight() / 2 + 40, 200, 50, (button) -> {
            this.gameMode = this.gameMode.next();
            button.setTitle(this.gameMode.toString());
            factionButton.setActive(this.gameMode != GameScene.GameMode.PVP_OFFLINE);
        }));
        this.addWidget(factionButton);
        this.addWidget(new Button("Back", this.getWidth() / 2, this.getHeight() / 2 + 200, 200, 50, (button) -> {
            Game.INSTANCE.setScene(new TitleScene());
        }));
    }

    @Override
    public void draw(Graphics2D g2) {
        this.drawBackground(g2);
        super.draw(g2);

        g2.setColor(Color.WHITE);
        g2.setFont(Game.INSTANCE.font.deriveFont(Font.BOLD, 50));
        drawCenteredString(g2, "Offline Game", this.getWidth() / 2, 100);

        g2.setFont(Game.INSTANCE.font.deriveFont(Font.PLAIN, 20));
        drawCenteredString(g2, "Game Mode", this.getWidth() / 2, this.getHeight() / 2);
        drawCenteredString(g2, "Player Faction", this.getWidth() / 2, this.getHeight() / 2 + 100);
    }
}
