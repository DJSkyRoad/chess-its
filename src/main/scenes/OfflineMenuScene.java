package main.scenes;

import main.Game;
import main.gui.Button;

import java.awt.*;

public class OfflineMenuScene extends Scene {
    private GameScene.GameMode gameMode = GameScene.GameMode.PVP_OFFLINE;
    private GameScene.Faction playerFaction = GameScene.Faction.WHITE;

    @Override
    public void init() {
        this.addButton(new Button("Start Game", Game.panelSize / 2, Game.panelSize / 2 - 60, 200, 50, (button) -> {
            Game.INSTANCE.setScene(new GameScene(this.gameMode, this.playerFaction));
        }));
        this.addButton(new Button(this.gameMode.toString(), Game.panelSize / 2, Game.panelSize / 2 + 40, 200, 50, (button) -> {
            this.gameMode = this.gameMode.next();
            button.setTitle(this.gameMode.toString());
        }));
        this.addButton(new Button(this.playerFaction.toString(), Game.panelSize / 2, Game.panelSize / 2 + 140, 200, 50, (button) -> {
            this.playerFaction = this.playerFaction.opposite();
            button.setTitle(this.playerFaction.toString());
        }));
        this.addButton(new Button("Back", Game.panelSize / 2, Game.panelSize / 2 + 200, 200, 50, (button) -> {
            Game.INSTANCE.setScene(new TitleScene());
        }));
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, this.getWidth(), this.getHeight());
        super.draw(g2);

        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Helvetia", Font.BOLD, 50));
        Game.drawCenteredString(g2, "Offline Game", Game.panelSize / 2, 100);

        g2.setFont(new Font("Helvetia", Font.PLAIN, 20));
        Game.drawCenteredString(g2, "Game Mode", Game.panelSize / 2, Game.panelSize / 2);
        Game.drawCenteredString(g2, "Player Faction", Game.panelSize / 2, Game.panelSize / 2 + 100);
    }
}
