package main.networking.connection;

import main.networking.packet.CompressedPacket;

import java.io.*;
import java.net.ConnectException;
import java.net.Socket;

public class Client extends Connection {
    private final String host;
    private final int port;

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
        this.start();
    }

    @Override
    protected void connect() {
        try {
            this.socket = new Socket(this.host, this.port);

            // HAS TO BE IN THIS ORDER
            this.out = new ObjectOutputStream(this.socket.getOutputStream());
            this.in = new ObjectInputStream(this.socket.getInputStream());

            this.connected = true;
        } catch (ConnectException e) {
            System.out.println("Could not connect to server");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void update() {
        try {
            Object packet = this.in.readObject();
            if (packet instanceof CompressedPacket) this.handlePacket((CompressedPacket) packet);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
