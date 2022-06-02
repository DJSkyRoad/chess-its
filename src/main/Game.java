package main;

import main.networking.connection.Client;
import main.networking.connection.Connection;
import main.networking.connection.Server;
import main.scenes.Scene;
import main.scenes.TitleScene;

import javax.swing.*;
import java.awt.*;

public class Game extends JPanel implements Runnable {
    public static Game INSTANCE;
    public static final int tileSize = 20 * 3;
    public static final int borderSize = 10;
    public static final int panelSize = tileSize * borderSize;
    private final int fps = 60;
    private final MouseInput mouseInput = new MouseInput();
    public static double deltaTime;
    private double intervalTime;

    private Scene scene;

    private Thread gameThread;

    private Connection connection;

    public Game() {
        INSTANCE = this;
        this.setPreferredSize(new Dimension(panelSize, panelSize));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addMouseListener(this.mouseInput);

        this.setScene(new TitleScene());
    }

    public void startServer() {
        this.connection = new Server(1234);
    }

    public void startClient() {
        this.connection = new Client("localhost", 1234);
    }

    public Connection getConnection() {
        return this.connection;
    }

    public void closeConnection() {
        this.connection.close();
        this.connection = null;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
        this.scene.init();
    }

    public Scene getScene() {
        return this.scene;
    }

    public void startGameThread() {
        this.gameThread = new Thread(this);
        this.gameThread.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        this.scene.resize(this.getWidth(), this.getHeight());
        this.scene.draw(g2);
    }

    public static void drawCenteredString(Graphics2D g2, String text, int x, int y) {
        FontMetrics metrics = g2.getFontMetrics(g2.getFont());
        x = x - metrics.stringWidth(text) / 2;
        y = y - (metrics.getHeight() / 2) + metrics.getAscent();
        g2.drawString(text, x, y);
    }

    public void update() {
        Point pos = this.getMousePosition();
        if (pos != null) {
            int x = pos.x;
            int y = pos.y;
            this.scene.onMouseHover(x, y);

            if (this.mouseInput.mouseClicked) {
                this.mouseInput.mouseClicked = false;
                this.scene.onMouseClick(x, y);
            }
        }
        this.repaint();
    }

    @Override
    public void run() {
        double updateInterval = 1000000000/this.fps;
        long lastTime = System.nanoTime();
        long currentTime;

        while (this.gameThread != null) {
            currentTime = System.nanoTime();
            this.intervalTime += (currentTime - lastTime) / updateInterval;
            this.deltaTime += (currentTime - lastTime) * 0.000000001D;
            lastTime = currentTime;

            if (intervalTime >= 1) {
                this.update();
                intervalTime--;
                deltaTime = 0;
            }
        }
    }
}
