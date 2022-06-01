package main.networking.connection;

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

    public Connection() {
        new Thread(this).start();
    }

    public void sendPacket(Packet packet) {
        try {
            this.out.writeObject(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void handlePacket(Packet packet) {
        packet.handle();
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

            this.in.close();
            this.out.close();

            this.socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
