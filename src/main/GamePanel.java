package main;

import main.math.ChessPos;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    public static final int tileSize = 20 * 3;
    public static final int borderSize = 10;
    public static final int panelSize = tileSize * borderSize;
    private final int fps = 60;
    private final MouseInput mouseInput = new MouseInput();
    public static double deltaTime;

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
            ChessPos chessPos = new ChessPos(pos.x / tileSize - 1, pos.y / tileSize - 1);

            if (this.board.canSelect(chessPos)) this.board.hovered = chessPos;
            else this.board.hovered = new ChessPos(-1, -1);

            if (this.mouseInput.mouseClicked) {
                this.mouseInput.mouseClicked = false;

                if (this.board.selected.compare(chessPos)) this.board.selected = new ChessPos(-1, -1);
                else if (this.board.canMoveSelectedTo(chessPos)) {
                    this.board.moveSelectedTo(chessPos);
                    this.board.selected = new ChessPos(-1, -1);
                    this.board.whiteTurn = !this.board.whiteTurn;
                }
                else if (this.board.canSelect(chessPos)) this.board.selected = chessPos;
            }
        }

        this.repaint();
    }

    @Override
    public void run() {
        double updateIntervall = 1000000000/this.fps;
        deltaTime = 0;
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
