package main.networking.packet;

import main.Game;
import main.scenes.OnlineMenuScene;
import main.scenes.Scene;

public class SetReadyPacket implements Packet {
    private boolean ready;

    public SetReadyPacket(boolean ready) {
        this.ready = ready;
    }

    public static SetReadyPacket read(CompressedPacket c) {
        return new SetReadyPacket(c.readBoolean());
    }

    @Override
    public void write(CompressedPacket c) {
        c.writeBoolean(this.ready);
    }

    @Override
    public void handle() {
        Scene scene = Game.INSTANCE.getScene();
        if (scene instanceof OnlineMenuScene) {
            ((OnlineMenuScene)scene).setReady(this.ready);
        }
    }
}
