package main.networking.packet;

public interface Packet {
    void write(CompressedPacket c);
    void handle();
}
