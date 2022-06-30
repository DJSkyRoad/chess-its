package main.networking.packet;

import main.Game;
import main.math.Move;
import main.scenes.GameScene;
import main.scenes.Scene;

public class MovePacket implements Packet {
    private Move move;

    public MovePacket(Move move) {
        this.move = move;
    }

    public static MovePacket read(CompressedPacket c) {
        return new MovePacket(c.readMove());
    }

    @Override
    public void write(CompressedPacket c) {
        c.writeMove(this.move);
    }

    @Override
    public void handle() {
        Scene scene = Game.INSTANCE.getScene();
        if (scene instanceof GameScene) {
            ((GameScene)scene).performMove(this.move);
            ((GameScene)scene).changeTurn();
        }
    }
}
