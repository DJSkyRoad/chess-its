package main.networking.packet;

import main.Game;
import main.scenes.GameScene;

public class StartGamePacket implements Packet {
    private GameScene.Faction playerFaction;

    public StartGamePacket(GameScene.Faction playerFaction) {
        this.playerFaction = playerFaction;
    }

    public static StartGamePacket read(CompressedPacket c) {
        return new StartGamePacket(c.readEnum(GameScene.Faction.class));
    }

    @Override
    public void write(CompressedPacket c) {
        c.writeEnum(this.playerFaction);
    }

    @Override
    public void handle() {
        Game.INSTANCE.setScene(new GameScene(GameScene.GameMode.PVP_ONLINE, this.playerFaction));
    }
}
