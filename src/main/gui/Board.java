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
    public List<ChessPos> isChecked(boolean white) {
        List<ChessPos> list = new ArrayList<ChessPos>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (pos[i][j] != null && pos[i][j] instanceof King && ((King) pos[i][j]).white == white) {
                    for (int k = i; k >= 0; k--) {
                        if (pos[k][j] == null) {
                            continue;
                        }
                        if (!(pos[k][j] instanceof Queen || pos[k][j] instanceof Rook) || pos[k][j].white == white) {
                            break;
                        }
                        if (pos[k][j] instanceof Queen || pos[k][j] instanceof Rook) {
                            list.add(new ChessPos(j, k));
                        }

                    }
                    for (int l = i; l < 8; l++) {
                        if (pos[l][j] == null) {
                            continue;
                        }
                        if (!(pos[l][j] instanceof Queen || pos[l][j] instanceof Rook) || pos[l][j].white == white) {
                            break;
                        }
                        if (pos[l][j] instanceof Queen || pos[l][j] instanceof Rook) {
                            list.add(new ChessPos(j, l));
                        }

                    }
                    for (int m = j; m >= 0; m--) {
                        if (pos[i][m] == null) {
                            continue;
                        }
                        if (!(pos[i][m] instanceof Queen || pos[i][m] instanceof Rook) || pos[i][m].white == white) {
                            break;
                        }
                        if (pos[i][m] instanceof Queen || pos[i][m] instanceof Rook) {
                            list.add(new ChessPos(m, i));
                        }
                    }
                    for (int m = j; m < 8; m++) {
                        if (pos[i][m] == null) {
                            continue;
                        }
                        if (!(pos[i][m] instanceof Queen || pos[i][m] instanceof Rook) || pos[i][m].white == white) {
                            break;
                        }
                        if (pos[i][m] instanceof Queen || pos[i][m] instanceof Rook) {
                            list.add(new ChessPos(m, i));
                        }
                    }
                    int a = i;
                    int b = j;
                    while (a < 8 || b < 8) {
                        if (pos[a][b] == null) {
                            continue;
                        }
                        if (!(pos[a][b] instanceof Queen || pos[a][b] instanceof Bishop) || pos[a][b].white == white) {
                            break;
                        }
                        if (pos[a][b] instanceof Queen || pos[a][b] instanceof Bishop) {
                            list.add(new ChessPos(b, a));
                        }
                        a++;
                        b++;
                    }
                    a = i;
                    b = j;

                    while (a >= 0 || b >= 0) {
                        if (pos[a][b] == null) {
                            continue;
                        }
                        if (!(pos[a][b] instanceof Queen || pos[a][b] instanceof Bishop) || pos[a][b].white == white) {
                            break;
                        }
                        if (pos[a][b] instanceof Queen || pos[a][b] instanceof Bishop) {
                            list.add(new ChessPos(b, a));
                        }
                        a--;
                        b--;
                    }
                    a = i;
                    b = j;

                    while (a < 8 || b >= 0) {
                        if (pos[a][b] == null) {
                            continue;
                        }
                        if (!(pos[a][b] instanceof Queen || pos[a][b] instanceof Bishop) || pos[a][b].white == white) {
                            break;
                        }
                        if (pos[a][b] instanceof Queen || pos[a][b] instanceof Bishop) {
                            list.add(new ChessPos(b, a));
                        }
                        a++;
                        b--;
                    }
                    a = i;
                    b = j;

                    while (a >= 0 || b < 8) {
                        if (pos[a][b] == null) {
                            continue;
                        }
                        if (!(pos[a][b] instanceof Queen || pos[a][b] instanceof Bishop) || pos[a][b].white == white) {
                            break;
                        }
                        if (pos[a][b] instanceof Queen || pos[a][b] instanceof Bishop) {
                            list.add(new ChessPos(b, a));
                        }
                        a--;
                        b++;
                    }
                    if (pos[i + 1][j - 2] instanceof Knight && !(pos[i][j].white == white)) {
                        list.add(new ChessPos(i + 1, j - 2));
                    }
                    if (pos[i + 2][j - 1] instanceof Knight && !(pos[i][j].white == white)) {
                        list.add(new ChessPos(i + 2, j - 1));
                    }
                    if (pos[i + 2][j + 1] instanceof Knight && !(pos[i][j].white == white)) {
                        list.add(new ChessPos(i + 2, j + 1));
                    }
                    if (pos[i + 1][j + 2] instanceof Knight && !(pos[i][j].white == white)) {
                        list.add(new ChessPos(i + 1, j + 2));
                    }
                    if (pos[i - 1][j + 2] instanceof Knight && !(pos[i][j].white == white)) {
                        list.add(new ChessPos(i - 1, j + 2));
                    }
                    if (pos[i - 2][j + 1] instanceof Knight && !(pos[i][j].white == white)) {
                        list.add(new ChessPos(i - 2, j + 1));
                    }
                    if (pos[i - 2][j - 1] instanceof Knight && !(pos[i][j].white == white)) {
                        list.add(new ChessPos(i - 2, j - 1));
                    }
                    if (pos[i - 1][j - 2] instanceof Knight && !(pos[i][j].white == white)) {
                        list.add(new ChessPos(i - 1, j - 2));
                    }

                    if (pos[i][j].white) { //Black Pawn White King
                        if (pos[i + 1][j + 1] instanceof Pawn) {
                            list.add(new ChessPos(i + 1,j + 1));
                        }
                        if (pos[i - 1][j + 1] instanceof Pawn) {
                            list.add(new ChessPos(i - 1,j + 1));
                        }
                    }
                    if (!(pos[i][j].white)) { //White Pawn Black King
                        if (pos[i + 1][j - 1] instanceof Pawn) {
                            list.add(new ChessPos(i + 1,j - 1));
                        }
                        if (pos[i - 1][j - 1] instanceof Pawn) {
                            list.add(new ChessPos(i - 1,j - 1));
                        }
                    }
                    
                }
            }
        }
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
