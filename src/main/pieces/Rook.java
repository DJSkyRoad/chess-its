package main.pieces;

import main.gui.Board;
import main.math.ChessPos;
import main.math.Move;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Rook extends ChessPiece {
    public Rook(boolean white) {
        super(white);
        try {
            this.image = ImageIO.read(Objects.requireNonNull(getClass()
                    .getResourceAsStream(white ? "/resources/white_rook.png" : "/resources/black_rook.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return this.isWhite() ? "\u265C" : "\u2656";
    }

    @Override
    public List<Move> getMoves(ChessPos pos, ChessPiece[][] board) {
        List<Move> moves = new ArrayList<>();
        for (int x = pos.x; x < Board.scale; x++) {
            ChessPiece piece = board[pos.y][x];
            if (piece != null && piece.isWhite() == this.isWhite()) break;
            moves.add(new Move(pos, new ChessPos(x, pos.y)));
            if (piece != null && piece.isWhite() != this.isWhite()) break;
        }
        for (int x = pos.x; x >= 0; x--) {
            ChessPiece piece = board[pos.y][x];
            if (piece != null && piece.isWhite() == this.isWhite()) break;
            moves.add(new Move(pos, new ChessPos(x, pos.y)));
            if (piece != null && piece.isWhite() != this.isWhite()) break;
        }
        for (int y = pos.y; y < Board.scale; y++) {
            ChessPiece piece = board[y][pos.x];
            if (piece != null && piece.isWhite() == this.isWhite()) break;
            moves.add(new Move(pos, new ChessPos(pos.x, y)));
            if (piece != null && piece.isWhite() != this.isWhite()) break;
        }
        for (int y = pos.y; y >= 0; y--) {
            ChessPiece piece = board[y][pos.x];
            if (piece != null && piece.isWhite() == this.isWhite()) break;
            moves.add(new Move(pos, new ChessPos(pos.x, y)));
            if (piece != null && piece.isWhite() != this.isWhite()) break;
        }
        return moves;
    }
}
