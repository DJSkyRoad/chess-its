package main.networking.connection;

import main.networking.packet.CompressedPacket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;

public class Server extends Connection {
    private final int port;
    private ServerSocket serverSocket;

    public Server(int port) {
        this.port = port;
        this.start();
    }

    @Override
    protected void connect() {
        try {
            this.serverSocket = new ServerSocket(port);

            this.socket = this.serverSocket.accept();

            // HAS TO BE IN THIS ORDER
            this.out = new ObjectOutputStream(this.socket.getOutputStream());
            this.in = new ObjectInputStream(this.socket.getInputStream());

            this.connected = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update() {
        try {
            Object packet = this.in.readObject();
            if (packet instanceof CompressedPacket) this.handlePacket((CompressedPacket) packet);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        try {
            this.connected = false;
            if (this.in != null) {
                this.in.close();
                this.out.close();
                this.socket.close();
                this.serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
