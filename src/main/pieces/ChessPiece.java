package main.pieces;

import main.gui.Board;
import main.math.ChessPos;
import main.math.Move;
import main.scenes.GameScene;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class ChessPiece {
    protected BufferedImage image;
	private final GameScene.Faction faction;

    public ChessPiece(GameScene.Faction faction, String name) {
        this.faction = faction;

        try {
            String path = "/resources/" + (faction.isWhite() ? "white" : "black") + "_" + name + ".png";
            InputStream inputStream = Objects.requireNonNull(getClass().getResourceAsStream(path));
            this.image = ImageIO.read(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onMoved(GameScene scene, Move move) {}

    public abstract String toString();
    public abstract List<Move> getMoves(ChessPos pos, ChessPiece[][] board);

    public BufferedImage getImage() {
        return this.image;
    }
    public GameScene.Faction getFaction() {
        return this.faction;
    }
    public boolean isKing() {
        return false;
    }

    protected List<Move> getLines(ChessPos pos, ChessPiece[][] board) {
        List<Move> moves = new ArrayList<>();
        for (int x = pos.x + 1; x < Board.scale; x++) {
            ChessPiece piece = board[pos.y][x];
            if (piece != null && piece.getFaction() == this.getFaction()) break;
            moves.add(new Move(pos, new ChessPos(x, pos.y)));
            if (piece != null) break;
        }
        for (int x = pos.x - 1; x >= 0; x--) {
            ChessPiece piece = board[pos.y][x];
            if (piece != null && piece.getFaction() == this.getFaction()) break;
            moves.add(new Move(pos, new ChessPos(x, pos.y)));
            if (piece != null) break;
        }
        for (int y = pos.y + 1; y < Board.scale; y++) {
            ChessPiece piece = board[y][pos.x];
            if (piece != null && piece.getFaction() == this.getFaction()) break;
            moves.add(new Move(pos, new ChessPos(pos.x, y)));
            if (piece != null) break;
        }
        for (int y = pos.y - 1; y >= 0; y--) {
            ChessPiece piece = board[y][pos.x];
            if (piece != null && piece.getFaction() == this.getFaction()) break;
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
            if (piece != null && piece.getFaction() == this.getFaction()) break;
            moves.add(new Move(pos, new ChessPos(x, y)));
            if (piece != null && piece.getFaction() != this.getFaction()) break;
        }
        for (int i = 1; pos.x - i >= 0 && pos.y - i >= 0; i++) {
            int x = pos.x - i; int y = pos.y - i;
            ChessPiece piece = board[y][x];
            if (piece != null && piece.getFaction() == this.getFaction()) break;
            moves.add(new Move(pos, new ChessPos(x, y)));
            if (piece != null && piece.getFaction() != this.getFaction()) break;
        }
        for (int i = 1; pos.x + i < Board.scale && pos.y - i >= 0; i++) {
            int x = pos.x + i; int y = pos.y - i;
            ChessPiece piece = board[y][x];
            if (piece != null && piece.getFaction() == this.getFaction()) break;
            moves.add(new Move(pos, new ChessPos(x, y)));
            if (piece != null && piece.getFaction() != this.getFaction()) break;
        }
        for (int i = 1; pos.x - i >= 0 && pos.y + i < Board.scale; i++) {
            int x = pos.x - i; int y = pos.y + i;
            ChessPiece piece = board[y][x];
            if (piece != null && piece.getFaction() == this.getFaction()) break;
            moves.add(new Move(pos, new ChessPos(x, y)));
            if (piece != null && piece.getFaction() != this.getFaction()) break;
        }
        return moves;
    }
}
