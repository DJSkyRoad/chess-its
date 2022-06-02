package main.scenes;

import main.Game;
import main.gui.Button;

public class GuestMenuScene extends OnlineMenuScene {
    public GuestMenuScene() {
        Game.INSTANCE.startClient();
        this.playerFaction = GameScene.Faction.BLACK;
    }

    @Override
    public void init() {
        super.init();
        this.addButton(new Button("Ready", Game.panelSize / 2, Game.panelSize / 2, 200, 50, (button) -> {
            Game.INSTANCE.setScene(new GameScene(this.isHost ? GameScene.GameMode.PVPHOST : GameScene.GameMode.PVPGUEST, this.playerFaction));
        }));
        this.factionButton.setActive(false);
    }
}
