package main.pieces;

import main.gui.Board;
import main.math.ChessPos;
import main.math.Move;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Bishop extends ChessPiece {
    public Bishop(boolean white) {
        super(white);
        try {
            this.image = ImageIO.read(Objects.requireNonNull(getClass()
                    .getResourceAsStream(white ? "/resources/white_bishop.png" : "/resources/black_bishop.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean canMoveTo(int xPiece, int yPiece, int xDest, int yDest, boolean foeOnDest) {
        return Math.abs(xDest - xPiece) == Math.abs(yDest - yPiece);
    }

    @Override
    public char getName() {
        return white ? 'b' : 'B';
    }

    @Override
    public String toString() {
        return white ? "\u265D" : "\u2657";
    }

    @Override
    public List<Move> getMoves(ChessPos pos, ChessPiece[][] board) {
        List<Move> moves = new ArrayList<>();
        for (int i = 0; pos.x + i < Board.scale && pos.y + i < Board.scale; i++) {
            int x = pos.x + i; int y = pos.y + i;
            ChessPiece piece = board[y][x];
            if (piece != null && piece.white == this.white) break;
            moves.add(new Move(pos, new ChessPos(x, y)));
            if (piece != null && piece.white != this.white) break;
        }
        for (int i = 0; pos.x - i < Board.scale && pos.y - i < Board.scale; i++) {
            int x = pos.x - i; int y = pos.y - i;
            ChessPiece piece = board[y][x];
            if (piece != null && piece.white == this.white) break;
            moves.add(new Move(pos, new ChessPos(x, y)));
            if (piece != null && piece.white != this.white) break;
        }
        for (int i = 0; pos.x + i < Board.scale && pos.y - i < Board.scale; i++) {
            int x = pos.x + i; int y = pos.y - i;
            ChessPiece piece = board[y][x];
            if (piece != null && piece.white == this.white) break;
            moves.add(new Move(pos, new ChessPos(x, y)));
            if (piece != null && piece.white != this.white) break;
        }
        for (int i = 0; pos.x - i < Board.scale && pos.y + i < Board.scale; i++) {
            int x = pos.x - i; int y = pos.y + i;
            ChessPiece piece = board[y][x];
            if (piece != null && piece.white == this.white) break;
            moves.add(new Move(pos, new ChessPos(x, y)));
            if (piece != null && piece.white != this.white) break;
        }
        return moves;
    }
}
