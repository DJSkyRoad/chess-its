package main;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    public static final int tileSize = 16 * 3;
    public static final int borderSize = 10;
    public static final int panelSize = tileSize * borderSize;
    private final int fps = 60;
    private final MouseInput mouseInput = new MouseInput();

    private final Board board = new Board();

    private Thread gameThread;

    public GamePanel() {
        this.setPreferredSize(new Dimension(panelSize, panelSize));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addMouseListener(this.mouseInput);
    }

    public void startGameThread() {
        this.gameThread = new Thread(this);
        this.gameThread.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, panelSize, panelSize);

        this.board.draw(g2);
    }

    public void update() {
        Point pos = this.getMousePosition();
        if (pos != null) {
            int x = pos.x / tileSize - 1;
            int y = pos.y / tileSize - 1;

            this.board.hovered.x = x;
            this.board.hovered.y = y;

            if (this.mouseInput.mouseClicked) {
                this.mouseInput.mouseClicked = false;
                this.board.selected.x = x;
                this.board.selected.y = y;
            }
        }

        System.out.println(this.board.selected.compare(this.board.hovered));

        this.repaint();
    }

    @Override
    public void run() {
        double updateIntervall = 1000000000/this.fps;
        double deltaTime = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (this.gameThread != null) {
            currentTime = System.nanoTime();
            deltaTime += (currentTime - lastTime) / updateIntervall;
            lastTime = currentTime;

            if (deltaTime >= 1) {
                // Update functions go here
                this.update();
                deltaTime--;
            }
        }
    }
}
