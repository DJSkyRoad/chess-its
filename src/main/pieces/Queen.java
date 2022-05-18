package main.pieces;

import main.gui.Board;
import main.math.ChessPos;
import main.math.Move;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Queen extends ChessPiece {
    public Queen(boolean white) {
        super(white);
        try {
            this.image = ImageIO.read(Objects.requireNonNull(getClass()
                    .getResourceAsStream(white ? "/resources/white_queen.png" : "/resources/black_queen.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return this.isWhite() ? "\u265B" : "\u2655";
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

        for (int i = 0; pos.x + i < Board.scale && pos.y + i < Board.scale; i++) {
            int x = pos.x + i; int y = pos.y + i;
            ChessPiece piece = board[y][x];
            if (piece != null && piece.isWhite() == this.isWhite()) break;
            moves.add(new Move(pos, new ChessPos(x, y)));
            if (piece != null && piece.isWhite() != this.isWhite()) break;
        }
        for (int i = 0; pos.x - i < Board.scale && pos.y - i < Board.scale; i++) {
            int x = pos.x - i; int y = pos.y - i;
            ChessPiece piece = board[y][x];
            if (piece != null && piece.isWhite() == this.isWhite()) break;
            moves.add(new Move(pos, new ChessPos(x, y)));
            if (piece != null && piece.isWhite() != this.isWhite()) break;
        }
        for (int i = 0; pos.x + i < Board.scale && pos.y - i < Board.scale; i++) {
            int x = pos.x + i; int y = pos.y - i;
            ChessPiece piece = board[y][x];
            if (piece != null && piece.isWhite() == this.isWhite()) break;
            moves.add(new Move(pos, new ChessPos(x, y)));
            if (piece != null && piece.isWhite() != this.isWhite()) break;
        }
        for (int i = 0; pos.x - i < Board.scale && pos.y + i < Board.scale; i++) {
            int x = pos.x - i; int y = pos.y + i;
            ChessPiece piece = board[y][x];
            if (piece != null && piece.isWhite() == this.isWhite()) break;
            moves.add(new Move(pos, new ChessPos(x, y)));
            if (piece != null && piece.isWhite() != this.isWhite()) break;
        }

        return moves;
    }
}
