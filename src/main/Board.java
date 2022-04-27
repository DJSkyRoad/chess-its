package main;

import main.math.ChessPos;
import main.pieces.*;

import java.awt.*;

public class Board {
    public static final int scale = 8;
    private static final int offset = 1 * GamePanel.tileSize;
    private static final float pieceScale = 0.6F;
    private static final int pieceOffset = offset + GamePanel.tileSize / 2;

    public final ChessPos hovered = new ChessPos(-1, -1);
    public final ChessPos selected = new ChessPos(-1, -1);

    public ChessPiece[][] pos = {
            {new Rook(false), new Knight(false), new Bishop(false), new Queen(false), new King(false), new Bishop(false), new Knight(false), new Rook(false)},
            {new Pawn(false), new Pawn(false), new Pawn(false), new Pawn(false), new Pawn(false), new Pawn(false), new Pawn(false), new Pawn(false)},
            {null,null,null,null,null,null,null,null},
            {null,null,null,null,null,null,null,null},
            {null,null,null,null,null,null,null,null},
            {null,null,null,null,null,null,null,null},
            {new Pawn(true), new Pawn(true), new Pawn(true), new Pawn(true), new Pawn(true), new Pawn(true), new Pawn(true), new Pawn(true)},
            {new Rook(true), new Knight(true), new Bishop(true), new Queen(true), new King(true), new Bishop(true), new Knight(true), new Rook(true)}
    };

    public boolean canMovePieceTo(boolean whiteTurn, int xPiece, int yPiece, int xDest, int yDest) {
        if (xPiece < 0 || yPiece < 0 || xDest < 0 || yDest < 0
        || xPiece > Board.scale || yPiece > Board.scale || xDest > Board.scale || yDest > Board.scale) return false;
        ChessPiece piece = pos[yPiece][xPiece];
        ChessPiece destPiece = pos[yDest][xDest];
        if (piece == null
                || piece.white != whiteTurn
                || (destPiece != null && destPiece.white == whiteTurn)) return false;
        if (!(piece instanceof Knight)) {
            int x = xPiece, y = yPiece;
            while (Math.abs(xDest - x) > 1 || Math.abs(yDest - y) > 1) {
                if (pos[y][x] != null) return false;
                x = xDest - x > 0 ? x + 1 : x - 1;
                y = yDest - y > 0 ? y + 1 : y - 1;
            }
        }
        return piece.canMoveTo(xPiece, yPiece, xDest, yDest, destPiece != null);
    }
    public boolean check(char name, boolean whiteTurn, int xPiece, int yPiece, int xDest, int yDest) {
        if (false) {    //condition for check
            return true;
        } else {
            return false;
        }
    }
    public boolean canEscapeCheckTo(char name, boolean whiteTurn, int xPiece, int yPiece, int xDest, int yDest) {     //Check if Dest valid
        if (false) {
            return true;
        } else {
            return false;
        }
    }

    public void movePieceTo(int xPiece, int yPiece, int xDest, int yDest) {
        ChessPiece piece = pos[yPiece][xPiece];
        pos[yPiece][xPiece] = null;
        pos[yDest][xDest] = piece;
    }

    public void draw(Graphics2D g2) {
        g2.setColor(Color.WHITE);
        for (int x = 0; x < scale; x++) {
            for (int y = 0; y < scale; y++) {
                if (this.hovered.compare(x, y) || this.selected.compare(x, y)) g2.setColor(Color.YELLOW);
                else if (g2.getColor() != Color.WHITE) g2.setColor(Color.WHITE);
                if ((x % 2 == 0 && y % 2 == 0) || (x % 2 != 0 && y % 2 != 0)
                || this.hovered.compare(x, y)
                || this.selected.compare(x, y)) g2.fillRect(x * GamePanel.tileSize + offset, y * GamePanel.tileSize + offset, GamePanel.tileSize, GamePanel.tileSize);
            }
        }

        for (int x = 0; x < scale; x++) {
            for (int y = 0; y < scale; y++) {
                if (this.pos[y][x] == null) continue;
                int scale = (int)(GamePanel.tileSize * pieceScale);
                int center = scale / 2;
                g2.drawImage(this.pos[y][x].image, x * GamePanel.tileSize + pieceOffset - center, y * GamePanel.tileSize + pieceOffset - center, scale, scale, null);
            }
        }
    }
}
