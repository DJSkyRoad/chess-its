package main;

import main.pieces.*;

import java.awt.*;

public class Board {
    public static final int scale = 8;
    public static final int tileWidth = 3;
    public static final int tileHeight = 1;
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

    public boolean canMovePieceTo(char name, boolean whiteTurn, int xPiece, int yPiece, int xDest, int yDest) {
        if (xPiece < 0 || yPiece < 0 || xDest < 0 || yDest < 0
        || xPiece > Board.scale || yPiece > Board.scale || xDest > Board.scale || yDest > Board.scale) return false;
        ChessPiece piece = pos[yPiece][xPiece];
        ChessPiece destPiece = pos[yDest][xDest];
        if (piece == null
                || piece.getName() != name
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
                if ((x % 2 == 0 && y % 2 == 0) || (x % 2 != 0 && y % 2 != 0)) g2.fillRect(x * GamePanel.tileSize + GamePanel.tileSize, y * GamePanel.tileSize + GamePanel.tileSize, GamePanel.tileSize, GamePanel.tileSize);
            }
        }

        for (int x = 0; x < scale; x++) {
            for (int y = 0; y < scale; y++) {
                if (this.pos[y][x] == null) continue;
                g2.drawImage(this.pos[y][x].image, x * GamePanel.tileSize + GamePanel.tileSize, y * GamePanel.tileSize + GamePanel.tileSize, GamePanel.tileSize / 2, GamePanel.tileSize / 2, null);
            }
        }
    }

    public String toString() {
        String s = "";
        s += this.getLetterString();
        for (int r = 0; r < scale; r++) {
            for (int l = 0; l < tileHeight; l++) {
                for (int c = 0; c < scale; c++) {
                    s += this.getTileString(c, r, l);
                }
            }
        }
        s += this.getLetterString();
        return s;
    }

    private String getTileString(int x, int y, int line) {
        if (line >= tileHeight || line < 0) return "INVALID LINE INDEX";
        String s = "";
        if (x == 0) {
            if (line == tileHeight / 2) s += (Board.scale - y) + " ";
            else s += "  ";
        }
        if ((x % 2 == 0 && y % 2 == 0) || (x % 2 != 0 && y % 2 != 0)) s += "\u001B[47m";
        for (int column = 0; column < tileWidth; column++) {
            if (line == tileHeight / 2 && column == tileWidth / 2 && pos[y][x] != null) s += pos[y][x];
            else s += "\u2003";
        }
        s += "\u001B[0m";
        if (x == Board.scale - 1) {
            if (line == tileHeight / 2) s += " " + (Board.scale - y);
            s += '\n';
        }
        return s;
    }

    private String getLetterString() {
        String s = "  ";
        for (int tileX = 0; tileX < scale; tileX++) {
            for (int x = 0; x < tileWidth; x++) {
                if (tileX != 0 && x == 0) continue;
                if (x == tileWidth / 2) s += (char)(97 + tileX);
                else s += " ";
            }
        }
        s += "\n";
        return s;
    }
}
