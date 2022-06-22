package main.networking.connection;

import main.networking.packet.CompressedPacket;
import main.networking.packet.Packet;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public abstract class Connection implements Runnable {
    protected Socket socket;

    protected ObjectInputStream in;
    protected ObjectOutputStream out;

    protected boolean connected;

    public void start() {
        new Thread(this).start();
    }

    public void sendPacket(Packet packet) {
        try {
            CompressedPacket compressedPacket = new CompressedPacket(packet);
            this.out.writeObject(compressedPacket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void handlePacket(CompressedPacket compressedPacket) {
        compressedPacket.decompress().handle();
    }

    @Override
    public void run() {
        this.connect();
        while (this.connected) this.update();
    }

    protected abstract void connect();

    protected abstract void update();

    public boolean isConnected() {
        return this.connected;
    }

    public void close() {
        try {
            this.connected = false;

            if (this.in != null) this.in.close();
            if (this.out != null) this.out.close();

            if (this.socket != null) this.socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
