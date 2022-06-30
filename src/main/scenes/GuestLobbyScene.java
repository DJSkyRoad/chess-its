package main.scenes;

import main.Game;
import main.gui.Button;
import main.networking.packet.SetReadyPacket;

public class GuestLobbyScene extends OnlineLobbyScene {
    public GuestLobbyScene() {
        this.playerFaction = GameScene.Faction.BLACK;
    }

    @Override
    public void init() {
        super.init();
        this.addWidget(new Button("Ready", this.getWidth() / 2, this.getHeight() / 2, 200, 50, (button) -> {
            this.setReady(!this.isReady());
            button.setTitle(this.isReady() ? "Unready" : "Ready");
            this.getConnection().sendPacket(new SetReadyPacket(this.isReady()));
        }));
        this.getFactionButton().setActive(false);
    }
}
