package main.scenes;

import main.Game;
import main.gui.Button;

public class HostMenuScene extends OnlineMenuScene {
    public HostMenuScene() {
        super();

        Game.INSTANCE.startServer();
        this.playerFaction = GameScene.Faction.WHITE;
    }

    @Override
    public void init() {
        super.init();
        this.addButton(new Button("Start Game", Game.panelSize / 2, Game.panelSize / 2, 200, 50, (button) -> {
            Game.INSTANCE.setScene(new GameScene(this.isHost ? GameScene.GameMode.PVPHOST : GameScene.GameMode.PVPGUEST, this.playerFaction));
        }));
    }
}
