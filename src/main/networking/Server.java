package main.networking;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.Selector;

public class Server implements Runnable {

    private int port;
    private boolean running = false;
    private ServerSocket serverSocket;

    public Server(int port) {
        this.port = port;

        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void start() {
        new Thread(this).start();
    }

    @Override
    public void run() {
        this.running = true;
        while(running) {
            try {
                Socket socket = serverSocket.accept();
                this.initSocket(socket);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        this.close();
    }

    private void initSocket(Socket socket) {
        Connection connection = new Connection(socket);
        new Thread(connection).start();
    }

    public void close() {
        running = false;

        try {
            serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
