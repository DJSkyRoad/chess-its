package main.gui;

import main.Game;
import main.math.ChessPos;
import main.math.MathUtils;
import main.pieces.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Board {
    public static final int scale = 8;
    private static final int offset = 1 * Game.tileSize;
    private static final int pieceScale = (int)(0.6F * Game.tileSize);
    private static final int pieceOffset = offset + Game.tileSize / 2;

    private BufferedImage selectImg;
    private float selectScale = 1.25F;
    private float animSpeed = 0.015F;

    public boolean whiteTurn = true;

    public ChessPos hovered = new ChessPos(-1, -1);
    public ChessPos selected = new ChessPos(-1, -1);
    private ChessPos canMoveTo = new ChessPos(-1, -1);

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

    public Board() {
        try {
            this.selectImg = ImageIO.read(getClass()
                    .getResourceAsStream("/resources/select.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean canSelect(ChessPos chessPos) {
        return chessPos.isValid() && ((this.pos[chessPos.y][chessPos.x] != null
                && this.pos[chessPos.y][chessPos.x].white == this.whiteTurn)
                || (this.selected.isValid() && this.canMoveSelectedTo(chessPos)));
    }

    public boolean isChecked(ChessPiece[][] pos, boolean white) {
        ChessPos king = new ChessPos(-1, -1);

        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                if (pos[y][x] != null && pos[y][x] instanceof King && ((King) pos[y][x]).white == white) {
                    king = new ChessPos(x, y);
                }
            }
        }

        // Check Lines
        for (int k = king.y; k >= 0; k--) {
            if (pos[k][king.x] == null) continue;
            if (!(pos[k][king.x] instanceof Queen || pos[k][king.x] instanceof Rook) || pos[k][king.x].white == white) break;
            return true;
        }
        for (int l = king.y; l < scale; l++) {
            if (pos[l][king.x] == null) continue;
            if (!(pos[l][king.x] instanceof Queen || pos[l][king.x] instanceof Rook) || pos[l][king.x].white == white) break;
            return true;
        }
        for (int m = king.x; m >= 0; m--) {
            if (pos[king.y][m] == null) continue;
            if (!(pos[king.y][m] instanceof Queen || pos[king.y][m] instanceof Rook) || pos[king.y][m].white == white) break;
            return true;
        }
        for (int m = king.x; m < scale; m++) {
            if (pos[king.y][m] == null) continue;
            if (!(pos[king.y][m] instanceof Queen || pos[king.y][m] instanceof Rook) || pos[king.y][m].white == white) break;
            return true;
        }

        // Check Diagonal
        int a = king.y; int b = king.x;
        while (a < scale || b < 8) {
            if (pos[a][b] == null) {
                a++; b++;
                continue;
            }
            if (!(pos[a][b] instanceof Queen || pos[a][b] instanceof Bishop) || pos[a][b].white == white) break;
            return true;
        }
        a = king.y; b = king.x;
        while (a >= 0 || b >= 0) {
            if (pos[a][b] == null) {
                a--; b--;
                continue;
            }
            if (!(pos[a][b] instanceof Queen || pos[a][b] instanceof Bishop) || pos[a][b].white == white) break;
            return true;
        }
        a = king.y; b = king.x;
        while (a < scale || b >= 0) {
            if (pos[a][b] == null) {
                a++; b--;
                continue;
            }
            if (!(pos[a][b] instanceof Queen || pos[a][b] instanceof Bishop) || pos[a][b].white == white) break;
            return true;
        }
        a = king.y; b = king.x;
        while (a >= 0 || b < scale) {
            if (pos[a][b] == null) {
                a--; b++;
                continue;
            }
            if (!(pos[a][b] instanceof Queen || pos[a][b] instanceof Bishop) || pos[a][b].white == white) break;
            return true;
        }

        // Check Knights
        a = king.y + 1; b = king.x - 2;
        if (new ChessPos(b, a).isValid() && pos[a][b] instanceof Knight && pos[a][b].white != white) return true;
        a = king.y + 2; b = king.x - 1;
        if (new ChessPos(b, a).isValid() && pos[a][b] instanceof Knight && pos[a][b].white != white) return true;
        a = king.y + 2; b = king.x + 1;
        if (new ChessPos(b, a).isValid() && pos[a][b] instanceof Knight && pos[a][b].white != white) return true;
        a = king.y + 1; b = king.x + 2;
        if (new ChessPos(b, a).isValid() && pos[a][b] instanceof Knight && pos[a][b].white != white) return true;
        a = king.y - 1; b = king.x + 2;
        if (new ChessPos(b, a).isValid() && pos[a][b] instanceof Knight && pos[a][b].white != white) return true;
        a = king.y - 2; b = king.x + 1;
        if (new ChessPos(b, a).isValid() && pos[a][b] instanceof Knight && pos[a][b].white != white) return true;
        a = king.y - 2; b = king.x - 1;
        if (new ChessPos(b, a).isValid() && pos[a][b] instanceof Knight && pos[a][b].white != white) return true;
        a = king.y - 1; b = king.x - 2;
        if (new ChessPos(b, a).isValid() && pos[a][b] instanceof Knight && pos[a][b].white != white) return true;

        // Check Pawns
        a = king.y + 1; b = king.x + 1;
        if (new ChessPos(b, a).isValid() && pos[a][b] instanceof Pawn && pos[a][b].white != white) return true;
        a = king.y - 1; b = king.x - 1;
        if (new ChessPos(b, a).isValid() && pos[a][b] instanceof Pawn && pos[a][b].white != white) return true;
        a = king.y + 1; b = king.x - 1;
        if (new ChessPos(b, a).isValid() && pos[a][b] instanceof Pawn && pos[a][b].white != white) return true;
        a = king.y - 1; b = king.x + 1;
        if (new ChessPos(b, a).isValid() && pos[a][b] instanceof Pawn && pos[a][b].white != white) return true;

        return false;
    }

    public boolean canMoveSelectedTo(ChessPos dest) {
        if (!this.selected.isValid() || !dest.isValid()) return false;
        if (this.canMoveTo.compare(dest)) return true;
        ChessPiece piece = pos[this.selected.y][this.selected.x];
        ChessPiece destPiece = pos[dest.y][dest.x];
        if (piece == null
                || piece.white != this.whiteTurn
                || (destPiece != null && destPiece.white == this.whiteTurn)) return false;
        if (!(piece instanceof Knight)) {
            ChessPos testPos = this.selected.next(dest);
            while (Math.abs(dest.x - testPos.x) > 0 || Math.abs(dest.y - testPos.y) > 0) {
                if (testPos.isValid() && pos[testPos.y][testPos.x] != null) return false;
                testPos = testPos.next(dest);
            }
        }
        if (piece.canMoveTo(this.selected, dest, destPiece != null)) {
            ChessPiece[][] pos2 = this.copyPos();
            ChessPiece p = pos2[this.selected.y][this.selected.x];
            pos2[this.selected.y][this.selected.x] = null;
            pos2[dest.y][dest.x] = p;

            if (!this.isChecked(pos2, this.whiteTurn)) {
                this.canMoveTo = dest;
                return true;
            }
            else System.out.println("Check");
        }
        return false;
    }

    private ChessPiece[][] copyPos() {
        ChessPiece[][] pos2 = new ChessPiece[this.pos.length][this.pos.length];
        for (int y = 0; y < this.pos.length; y++) {
            for (int x = 0; x < this.pos.length; x++) {
                pos2[y][x] = this.pos[y][x];
            }
        }
        return pos2;
    }

    public void moveSelectedTo(ChessPos dest) {
        ChessPiece piece = pos[this.selected.y][this.selected.x];
        pos[this.selected.y][this.selected.x] = null;
        pos[dest.y][dest.x] = piece;
    }

    public void draw(Graphics2D g2) {
        for (int x = 0; x < scale; x++) {
            for (int y = 0; y < scale; y++) {
                if ((x % 2 == 0 && y % 2 == 0) || (x % 2 != 0 && y % 2 != 0)) g2.setColor(new Color(0x1f1f1f));
                else g2.setColor(new Color(0xededed));
                g2.fillRect(x * Game.tileSize + offset, y * Game.tileSize + offset, Game.tileSize, Game.tileSize);
            }
        }

        for (int x = 0; x < scale; x++) {
            for (int y = 0; y < scale; y++) {
                if (this.pos[y][x] == null) continue;
                this.drawCenteredImage(g2, this.pos[y][x].image, x * Game.tileSize + pieceOffset, y * Game.tileSize + pieceOffset, pieceScale);
            }
        }

        if (this.selected.isValid())
            this.drawCenteredImage(g2, this.selectImg, this.selected.x * Game.tileSize + pieceOffset, this.selected.y * Game.tileSize + pieceOffset, Game.tileSize);
        if (this.hovered.isValid() && !this.selected.compare(this.hovered)) {
            this.drawCenteredImage(g2, this.selectImg, this.hovered.x * Game.tileSize + pieceOffset, this.hovered.y * Game.tileSize + pieceOffset, (int) (Game.tileSize * this.selectScale));
            this.selectScale = MathUtils.clamp(this.selectScale + this.animSpeed, 1F, 1.25F);
            this.animSpeed = !MathUtils.inRangeEx(this.selectScale, 1F, 1.25F) ? this.animSpeed * -1 : this.animSpeed;
        }
    }

    private void drawCenteredImage(Graphics2D g2, BufferedImage image, int x, int y, int scale) {
        int center = scale / 2;
        g2.drawImage(image, x - center, y - center, scale, scale, null);
    }
}