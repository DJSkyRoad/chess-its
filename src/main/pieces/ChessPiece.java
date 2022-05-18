package main.pieces;

import main.gui.Board;
import main.math.ChessPos;
import main.math.Move;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public abstract class ChessPiece {
    protected BufferedImage image;
	private final boolean white;

    public ChessPiece(boolean white) {
        this.white = white;
    }
    public abstract String toString();
    public abstract List<Move> getMoves(ChessPos pos, ChessPiece[][] board);

    public BufferedImage getImage() {
        return this.image;
    }
    public boolean isWhite() {
        return this.white;
    }
    public boolean isKing() {
        return false;
    }

    protected List<Move> getLines(ChessPos pos, ChessPiece[][] board) {
        List<Move> moves = new ArrayList<>();
        for (int x = pos.x + 1; x < Board.scale; x++) {
            ChessPiece piece = board[pos.y][x];
            if (piece != null && piece.isWhite() == this.isWhite()) break;
            moves.add(new Move(pos, new ChessPos(x, pos.y)));
            if (piece != null) break;
        }
        for (int x = pos.x - 1; x >= 0; x--) {
            ChessPiece piece = board[pos.y][x];
            if (piece != null && piece.isWhite() == this.isWhite()) break;
            moves.add(new Move(pos, new ChessPos(x, pos.y)));
            if (piece != null) break;
        }
        for (int y = pos.y + 1; y < Board.scale; y++) {
            ChessPiece piece = board[y][pos.x];
            if (piece != null && piece.isWhite() == this.isWhite()) break;
            moves.add(new Move(pos, new ChessPos(pos.x, y)));
            if (piece != null) break;
        }
        for (int y = pos.y - 1; y >= 0; y--) {
            ChessPiece piece = board[y][pos.x];
            if (piece != null && piece.isWhite() == this.isWhite()) break;
            moves.add(new Move(pos, new ChessPos(pos.x, y)));
            if (piece != null) break;
        }
        return moves;
    }

    protected List<Move> getDiagonals(ChessPos pos, ChessPiece[][] board) {
        List<Move> moves = new ArrayList<>();
        for (int i = 1; pos.x + i < Board.scale && pos.y + i < Board.scale; i++) {
            int x = pos.x + i; int y = pos.y + i;
            ChessPiece piece = board[y][x];
            if (piece != null && piece.isWhite() == this.isWhite()) break;
            moves.add(new Move(pos, new ChessPos(x, y)));
            if (piece != null && piece.isWhite() != this.isWhite()) break;
        }
        for (int i = 1; pos.x - i >= 0 && pos.y - i >= 0; i++) {
            int x = pos.x - i; int y = pos.y - i;
            ChessPiece piece = board[y][x];
            if (piece != null && piece.isWhite() == this.isWhite()) break;
            moves.add(new Move(pos, new ChessPos(x, y)));
            if (piece != null && piece.isWhite() != this.isWhite()) break;
        }
        for (int i = 1; pos.x + i < Board.scale && pos.y - i >= 0; i++) {
            int x = pos.x + i; int y = pos.y - i;
            ChessPiece piece = board[y][x];
            if (piece != null && piece.isWhite() == this.isWhite()) break;
            moves.add(new Move(pos, new ChessPos(x, y)));
            if (piece != null && piece.isWhite() != this.isWhite()) break;
        }
        for (int i = 1; pos.x - i >= 0 && pos.y + i < Board.scale; i++) {
            int x = pos.x - i; int y = pos.y + i;
            ChessPiece piece = board[y][x];
            if (piece != null && piece.isWhite() == this.isWhite()) break;
            moves.add(new Move(pos, new ChessPos(x, y)));
            if (piece != null && piece.isWhite() != this.isWhite()) break;
        }
        return moves;
    }
}
