package main.networking.packet;

import java.io.Serializable;

public interface Packet extends Serializable {
    void handle();
}
