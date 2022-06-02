package main.networking.packet;

import main.Game;
import main.scenes.GameScene;
import main.scenes.OnlineMenuScene;
import main.scenes.Scene;

public class SetPlayerFactionPacket implements Packet {
    private GameScene.Faction faction;

    public SetPlayerFactionPacket(GameScene.Faction faction) {
        this.faction = faction;
    }

    public static SetPlayerFactionPacket read(CompressedPacket c) {
        return new SetPlayerFactionPacket(c.readEnum(GameScene.Faction.class));
    }

    @Override
    public void write(CompressedPacket c) {
        c.writeEnum(this.faction);
    }

    @Override
    public void handle() {
        Scene scene = Game.INSTANCE.getScene();
        if (scene instanceof OnlineMenuScene) {
            ((OnlineMenuScene)scene).setPlayerFaction(this.faction);
        }
    }
}
