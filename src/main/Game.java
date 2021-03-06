package main;

import main.input.InputManager;
import main.networking.connection.Client;
import main.networking.connection.Connection;
import main.networking.connection.Server;
import main.scenes.Scene;
import main.scenes.TitleScene;
import main.sound.AudioPlayer;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

public class Game extends JPanel implements Runnable {
    public static Game INSTANCE;
    public static final int tileSize = 20 * 3;
    public static final int borderSize = 10;
    public static final int panelSize = tileSize * borderSize;
    public final InputManager input = new InputManager();
    public final AudioPlayer audioPlayer = new AudioPlayer();
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
        this.addMouseListener(this.input);

        InputStream inputStream = getClass().getResourceAsStream("/resources/font/TitanOne-Regular.ttf");
        try {
            this.font = Font.createFont(Font.TRUETYPE_FONT, inputStream);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }

        this.setScene(new TitleScene());
        this.audioPlayer.playMusic(AudioPlayer.BGM);
        this.audioPlayer.setMusicVolume(0.5F);
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

    public void setScene(Scene scene) {
        this.scene = scene;
        this.scene.resize(this.getWidth(), this.getHeight());
    }

    public Scene getScene() {
        return this.scene;
    }

    public void setOverlayScene(Scene scene) {
        this.overlayScene = scene;
        if (scene == null) this.input.resetInput();
        else scene.resize(this.getWidth(), this.getHeight());
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

            if (this.input.mouseClicked) {
                this.input.mouseClicked = false;
                currentScene.onMouseClick(x, y);
            }
            else if (this.input.mousePressed) {
                this.input.mousePressed = false;
                currentScene.onMousePress(x, y);
            }
            else if (this.input.mouseReleased) {
                this.input.mouseReleased = false;
                currentScene.onMouseRelease(x, y);
            }
        }
        if (this.input.keyPressed != null) {
            currentScene.onKeyPressed(this.input.keyPressed);
            this.input.keyPressed = null;
        }
        this.repaint();
    }

    @Override
    public void run() {
        int fps = 60;
        double updateInterval = 1000000000/ fps;
        long lastTime = System.nanoTime();
        long currentTime;

        while (this.gameThread != null) {
            currentTime = System.nanoTime();
            this.intervalTime += (currentTime - lastTime) / updateInterval;
            deltaTime += (currentTime - lastTime) * 0.000000001D;
            lastTime = currentTime;

            if (intervalTime >= 1) {
                this.update();
                intervalTime--;
                deltaTime = 0;
            }
        }
    }
}
