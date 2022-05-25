package main.gui;

import main.Game;
import main.math.ChessPos;
import main.math.MathUtils;
import main.pieces.*;
import main.scenes.GameScene;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Board {
    public static final int scale = 8;
    private static final int offset = Game.tileSize;
    private static final int pieceScale = (int)(0.6F * Game.tileSize);
    private static final int pieceOffset = offset + Game.tileSize / 2;

    private BufferedImage selectImg;
    private float selectScale = 1.25F;
    private float animSpeed = 0.015F;

    public ChessPos hovered = new ChessPos(-1, -1);
    public ChessPos selected = new ChessPos(-1, -1);

    public ChessPiece[][] pos = {
            {new Rook(GameScene.Faction.BLACK), new Knight(GameScene.Faction.BLACK), new Bishop(GameScene.Faction.BLACK), new Queen(GameScene.Faction.BLACK), new King(GameScene.Faction.BLACK), new Bishop(GameScene.Faction.BLACK), new Knight(GameScene.Faction.BLACK), new Rook(GameScene.Faction.BLACK)},
            {new Pawn(GameScene.Faction.BLACK), new Pawn(GameScene.Faction.BLACK), new Pawn(GameScene.Faction.BLACK), new Pawn(GameScene.Faction.BLACK), new Pawn(GameScene.Faction.BLACK), new Pawn(GameScene.Faction.BLACK), new Pawn(GameScene.Faction.BLACK), new Pawn(GameScene.Faction.BLACK)},
            {null,null,null,null,null,null,null,null},
            {null,null,null,null,null,null,null,null},
            {null,null,null,null,null,null,null,null},
            {null,null,null,null,null,null,null,null},
            {new Pawn(GameScene.Faction.WHITE), new Pawn(GameScene.Faction.WHITE), new Pawn(GameScene.Faction.WHITE), new Pawn(GameScene.Faction.WHITE), new Pawn(GameScene.Faction.WHITE), new Pawn(GameScene.Faction.WHITE), new Pawn(GameScene.Faction.WHITE), new Pawn(GameScene.Faction.WHITE)},
            {new Rook(GameScene.Faction.WHITE), new Knight(GameScene.Faction.WHITE), new Bishop(GameScene.Faction.WHITE), new Queen(GameScene.Faction.WHITE), new King(GameScene.Faction.WHITE), new Bishop(GameScene.Faction.WHITE), new Knight(GameScene.Faction.WHITE), new Rook(GameScene.Faction.WHITE)}
    };

    public Board() {
        try {
            this.selectImg = ImageIO.read(Objects.requireNonNull(getClass()
                    .getResourceAsStream("/resources/select.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
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
                this.drawCenteredImage(g2, this.pos[y][x].getImage(), x * Game.tileSize + pieceOffset, y * Game.tileSize + pieceOffset, pieceScale);
            }
        }

        if (this.selected.isValid())
            this.drawCenteredImage(g2, this.selectImg, this.selected.x * Game.tileSize + pieceOffset, this.selected.y * Game.tileSize + pieceOffset, Game.tileSize);
        if (this.hovered.isValid() && !this.selected.equals(this.hovered)) {
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