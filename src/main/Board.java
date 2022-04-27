package main;

import main.math.ChessPos;
import main.math.MathUtils;
import main.pieces.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Board {
    public static final int scale = 8;
    private static final int offset = 1 * GamePanel.tileSize;
    private static final int pieceScale = (int)(0.6F * GamePanel.tileSize);
    private static final int pieceOffset = offset + GamePanel.tileSize / 2;

    private BufferedImage selectImg;
    private float selectScale = 1.25F;
    private float animSpeed = 0.015F;

    public boolean whiteTurn = true;

    public ChessPos hovered = new ChessPos(-1, -1);
    public ChessPos selected = new ChessPos(-1, -1);

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
        return chessPos.isValid() && ((this.pos[chessPos.y][chessPos.x] != null && this.pos[chessPos.y][chessPos.x].white == this.whiteTurn) || (this.selected.isValid() && this.canMoveSelectedTo(chessPos)));
    }

    public boolean canMoveSelectedTo(ChessPos dest) {
        if (!this.selected.isValid() || !dest.isValid()) return false;
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
        return piece.canMoveTo(this.selected.x, this.selected.y, dest.x, dest.y, destPiece != null);
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

    public void moveSelectedTo(ChessPos dest) {
        ChessPiece piece = pos[this.selected.y][this.selected.x];
        pos[this.selected.y][this.selected.x] = null;
        pos[dest.y][dest.x] = piece;
    }

    public void draw(Graphics2D g2) {
        for (int x = 0; x < scale; x++) {
            for (int y = 0; y < scale; y++) {
                //if (this.hovered.compare(x, y) || this.selected.compare(x, y)) g2.setColor(Color.YELLOW);
                if ((x % 2 == 0 && y % 2 == 0) || (x % 2 != 0 && y % 2 != 0)) g2.setColor(new Color(0x1f1f1f));
                else g2.setColor(new Color(0xededed));
                g2.fillRect(x * GamePanel.tileSize + offset, y * GamePanel.tileSize + offset, GamePanel.tileSize, GamePanel.tileSize);
            }
        }

        for (int x = 0; x < scale; x++) {
            for (int y = 0; y < scale; y++) {
                if (this.pos[y][x] == null) continue;
                this.drawCenteredImage(g2, this.pos[y][x].image, x * GamePanel.tileSize + pieceOffset, y * GamePanel.tileSize + pieceOffset, pieceScale);
            }
        }

        if (this.selected.isValid())
            this.drawCenteredImage(g2, this.selectImg, this.selected.x * GamePanel.tileSize + pieceOffset, this.selected.y * GamePanel.tileSize + pieceOffset, GamePanel.tileSize);
        if (this.hovered.isValid() && !this.selected.compare(this.hovered)) {
            this.drawCenteredImage(g2, this.selectImg, this.hovered.x * GamePanel.tileSize + pieceOffset, this.hovered.y * GamePanel.tileSize + pieceOffset, (int) (GamePanel.tileSize * this.selectScale));
            this.selectScale = MathUtils.clamp(this.selectScale + this.animSpeed, 1F, 1.25F);
            this.animSpeed = !MathUtils.inRangeEx(this.selectScale, 1F, 1.25F) ? this.animSpeed * -1 : this.animSpeed;
        }
    }

    private void drawCenteredImage(Graphics2D g2, BufferedImage image, int x, int y, int scale) {
        int center = scale / 2;
        g2.drawImage(image, x - center, y - center, scale, scale, null);
    }
}
