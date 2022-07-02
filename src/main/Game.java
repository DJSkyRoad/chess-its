package main;

import main.input.KeyInput;
import main.input.MouseInput;
import main.networking.connection.Client;
import main.networking.connection.Connection;
import main.networking.connection.Server;
import main.scenes.Scene;
import main.scenes.TitleScene;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Optional;

public class Game extends JPanel implements Runnable {
    public static Game INSTANCE;
    public static final int tileSize = 20 * 3;
    public static final int borderSize = 10;
    public static final int panelSize = tileSize * borderSize;
    private final int fps = 60;
    private final MouseInput mouseInput = new MouseInput();
    public final KeyInput keyInput = new KeyInput();
    private final AudioPlayer audioPlayer = new AudioPlayer();
    public static double deltaTime;
    private double intervalTime;

    private Scene scene;
    private Scene overlayScene;

    private Thread gameThread;

    private Connection connection;

    public Font font;

    public Game() {
        INSTANCE = this;
        this.setPreferredSize(new Dimension(panelSize, panelSize));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addMouseListener(this.mouseInput);

        InputStream inputStream = getClass().getResourceAsStream("/resources/font/TitanOne-Regular.ttf");
        try {
            this.font = Font.createFont(Font.TRUETYPE_FONT, inputStream);
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.setScene(new TitleScene());
    }

    public void startServer(int port) {
        this.connection = new Server(port);
    }

    public void startClient(String ip, int port) {
        this.connection = new Client(ip, port);
    }

    public Optional<Connection> getConnection() {
        return this.connection == null || !this.connection.isConnected() ? Optional.empty()
                : Optional.of(this.connection);
    }

    public void closeConnection() {
        this.connection.close();
        this.connection = null;
    }

    public void playSound(URL url) {
        this.audioPlayer.open(url);
        this.audioPlayer.start();
    }

    public void setScene(Scene scene) {
        this.scene = scene;
        this.scene.resize(this.getWidth(), this.getHeight());
    }

    public Scene getScene() {
        return this.scene;
    }

    public void setOverlayScene(Scene scene) {
        this.overlayScene = scene;
        this.getOverlayScene().ifPresent((s) -> s.resize(this.getWidth(), this.getHeight()));
    }

    public Optional<Scene> getOverlayScene() {
        return Optional.ofNullable(this.overlayScene);
    }

    public void startGameThread() {
        this.gameThread = new Thread(this);
        this.gameThread.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        if (this.scene.getWidth() != this.getWidth()
        || this.scene.getHeight() != this.getHeight()) this.scene.resize(this.getWidth(), this.getHeight());
        this.scene.draw(g2);
        this.getOverlayScene().ifPresent((scene) -> scene.draw(g2));
    }

    public void update() {
        Point pos = this.getMousePosition();
        Scene currentScene = this.getOverlayScene().orElse(this.getScene());
        if (pos != null) {
            int x = pos.x;
            int y = pos.y;
            currentScene.onMouseHover(x, y);

            if (this.mouseInput.mouseClicked) {
                this.mouseInput.mouseClicked = false;
                currentScene.onMouseClick(x, y);
            }
            else if (this.mouseInput.mousePressed) {
                this.mouseInput.mousePressed = false;
                currentScene.onMousePress(x, y);
            }
            else if (this.mouseInput.mouseReleased) {
                this.mouseInput.mouseReleased = false;
                currentScene.onMouseRelease(x, y);
            }
        }
        if (this.keyInput.keyPressed != null) {
            currentScene.onKeyPressed(this.keyInput.keyPressed);
            this.keyInput.keyPressed = null;
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
