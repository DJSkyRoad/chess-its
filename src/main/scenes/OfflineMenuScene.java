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
        this.addWidget(new Button(this.gameMode.toString(), this.getWidth() / 2, this.getHeight() / 2 + 40, 200, 50, (button) -> {
            this.gameMode = this.gameMode.next();
            button.setTitle(this.gameMode.toString());
        }));
        this.addWidget(new Button(this.playerFaction.toString(), this.getWidth() / 2, this.getHeight() / 2 + 140, 200, 50, (button) -> {
            this.playerFaction = this.playerFaction.opposite();
            button.setTitle(this.playerFaction.toString());
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
        Game.drawCenteredString(g2, "Offline Game", this.getWidth() / 2, 100);

        g2.setFont(new Font("Helvetia", Font.PLAIN, 20));
        Game.drawCenteredString(g2, "Game Mode", this.getWidth() / 2, this.getHeight() / 2);
        Game.drawCenteredString(g2, "Player Faction", this.getWidth() / 2, this.getHeight() / 2 + 100);
    }
}
