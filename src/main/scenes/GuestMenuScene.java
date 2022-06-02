package main.scenes;

import main.Game;
import main.gui.Button;
import main.networking.packet.SetReadyPacket;

public class GuestMenuScene extends OnlineMenuScene {
    public GuestMenuScene() {
        this.playerFaction = GameScene.Faction.BLACK;
    }

    @Override
    public void init() {
        super.init();
        this.addButton(new Button("Ready", Game.panelSize / 2, Game.panelSize / 2, 200, 50, (button) -> {
            this.setReady(!this.isReady());
            button.setTitle(this.isReady() ? "Unready" : "Ready");
            this.getConnection().sendPacket(new SetReadyPacket(this.isReady()));
        }));
        this.getFactionButton().setActive(false);
    }
}
