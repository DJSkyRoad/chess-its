package main.networking.packet;

import main.math.ChessPos;
import main.math.Move;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CompressedPacket implements Serializable {
    private final int id;
    private final List<Object> data = new ArrayList<>();
    private int index;

    public CompressedPacket(Packet packet) {
        this.id = PacketManager.PACKETS.indexOf(packet.getClass());
        packet.write(this);
    }

    public Packet decompress() {
        return PacketManager.READ.get(this.id).apply(this);
    }

    public Object readObject() {
        return this.data.get(this.index++);
    }

    public Move readMove() {
        return new Move(this.readChessPos(), this.readChessPos());
    }

    public ChessPos readChessPos() {
        return new ChessPos(this.readInt(), this.readInt());
    }

    public int readInt() {
        return (int)this.readObject();
    }

    public boolean readBoolean() {
        return (boolean)this.readObject();
    }

    public <T extends Enum<T>> T readEnum(Class<T> clazz) {
        return clazz.getEnumConstants()[this.readInt()];
    }

    public void writeEnum(Enum<?> value) {
        this.writeInt(value.ordinal());
    }

    public void writeInt(int value) {
        this.data.add(value);
    }

    public void writeChessPos(ChessPos pos) {
        this.writeInt(pos.x);
        this.writeInt(pos.y);
    }

    public void writeMove(Move move) {
        this.writeChessPos(move.pos);
        this.writeChessPos(move.dest);
    }

    public void writeBoolean(boolean value) {
        this.data.add(value);
    }
}
