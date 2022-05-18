package main.scenes;

import main.gui.Board;
import main.Game;
import main.math.ChessPos;

import java.awt.*;

public class GameScene extends Scene {
    private final Board board = new Board();

    @Override
    public void draw(Graphics2D g2) {
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, this.getWidth(), this.getHeight());

        this.board.draw(g2);

        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Helvetia", Font.BOLD, 30));
        Game.drawCenteredString(g2, this.board.whiteTurn ? "White's turn" : "Black's turn", this.getWidth() / 2, 30);
    }

    @Override
    public void onMouseHover(int x, int y) {
        ChessPos chessPos = new ChessPos(x / Game.tileSize - 1, y / Game.tileSize - 1);
        if (this.board.canSelect(chessPos)) this.board.hovered = chessPos;
        else this.board.hovered = new ChessPos(-1, -1);
    }

    @Override
    public void onMouseClick(int x, int y) {
        ChessPos chessPos = new ChessPos(x / Game.tileSize - 1, y / Game.tileSize - 1);
        if (this.board.selected.equals(chessPos)) this.board.selected = new ChessPos(-1, -1);
        else if (this.board.canMoveSelectedTo(chessPos)) {
            this.board.moveSelectedTo(chessPos);
            this.board.selected = new ChessPos(-1, -1);

            this.board.whiteTurn = !this.board.whiteTurn;
            this.board.refreshMoves();
            if (this.board.isCheckMate()) System.out.println("Check mate");
            
        }
        else if (this.board.canSelect(chessPos)) this.board.selected = chessPos;
    }
}
