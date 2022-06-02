package main.networking.packet;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class PacketManager {
    public static final List<Class<? extends Packet>> PACKETS = new ArrayList<>();
    public static final List<Function<CompressedPacket, ? extends Packet>> READ = new ArrayList<>();

    static {
        register(MovePacket.class, MovePacket::read);
        register(SetReadyPacket.class, SetReadyPacket::read);
        register(StartGamePacket.class, StartGamePacket::read);
        register(SetPlayerFactionPacket.class, SetPlayerFactionPacket::read);
    }

    private static <P extends Packet> void register(Class<P> clazz, Function<CompressedPacket, P> read) {
        PACKETS.add(clazz);
        READ.add(read);
    }
}
