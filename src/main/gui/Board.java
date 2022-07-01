package main.gui;

import main.Game;
import main.math.ChessPos;
import main.math.Move;
import main.pieces.*;
import main.scenes.GameScene;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Board {
    private int x;
    private int y;

    private int xOffset;
    private int yOffset;

    public static final int scale = 8;
    private static final int pieceScale = (int)(0.6F * Game.tileSize);
    private static final int boardScale = scale * Game.tileSize;

    private int mouseX;
    private int mouseY;
    public boolean dragging;

    public ChessPos hovered = new ChessPos(-1, -1);
    public ChessPos selected = new ChessPos(-1, -1);

    public List<ChessPos> selectedMoves = new ArrayList<>();
    public Move enemyMove;

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

    public Board(int x, int y) {
        this.x = x;
        this.y = y;
        this.xOffset = this.x - boardScale / 2;
        this.yOffset = this.y - boardScale / 2;
    }

    public void resize(int x, int y) {
        this.x = x;
        this.y = y;
        this.xOffset = this.x - boardScale / 2;
        this.yOffset = this.y - boardScale / 2;
    }

    public void deselect() {
        this.selected = new ChessPos(-1, -1);
        this.selectedMoves.clear();
    }

    public void update(int x, int y) {
        this.mouseX = x;
        this.mouseY = y;
    }

    public ChessPos mouseToChessPos(int mouseX, int mouseY) {
        return new ChessPos((mouseX - this.xOffset) / Game.tileSize, (mouseY - this.yOffset) / Game.tileSize);
    }

    public void draw(Graphics2D g2) {
        int xPieceOffset = this.xOffset + Game.tileSize / 2;
        int yPieceOffset = this.yOffset + Game.tileSize / 2;

        for (int x = 0; x < scale; x++) {
            for (int y = 0; y < scale; y++) {
                if ((x % 2 == 0 && y % 2 == 0) || (x % 2 != 0 && y % 2 != 0)) g2.setColor(new Color(0xCC782D));
                else g2.setColor(new Color(0x944F0B));
                g2.fillRect(x * Game.tileSize + this.xOffset, y * Game.tileSize + this.yOffset, Game.tileSize, Game.tileSize);
            }
        }

        g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        if (this.enemyMove != null) {
            g2.setColor(new Color(0x8CFF0000, true));
            g2.fillRect(this.enemyMove.pos.x * Game.tileSize + this.xOffset, this.enemyMove.pos.y * Game.tileSize + this.yOffset, Game.tileSize, Game.tileSize);
            g2.fillRect(this.enemyMove.dest.x * Game.tileSize + this.xOffset, this.enemyMove.dest.y * Game.tileSize + this.yOffset, Game.tileSize, Game.tileSize);
        }

        if (this.selected.isValid()) {
            g2.setColor(new Color(0xB418FF00, true));
            g2.fillRect(this.selected.x * Game.tileSize + this.xOffset, this.selected.y * Game.tileSize + this.yOffset, Game.tileSize, Game.tileSize);
            g2.setColor(new Color(0x7118FF00, true));
            for (ChessPos pos : this.selectedMoves) {
                g2.fillRect(pos.x * Game.tileSize + this.xOffset, pos.y * Game.tileSize + this.yOffset, Game.tileSize, Game.tileSize);
            }
        }
        if (this.hovered.isValid() && !this.selected.equals(this.hovered)) {
            g2.setColor(new Color(0x7118FF00, true));
            g2.fillRect(this.hovered.x * Game.tileSize + this.xOffset, this.hovered.y * Game.tileSize + this.yOffset, Game.tileSize, Game.tileSize);
        }

        for (int x = 0; x < scale; x++) {
            for (int y = 0; y < scale; y++) {
                if (this.pos[y][x] == null || (this.dragging && this.selected.equals(new ChessPos(x, y)))) continue;
                Widget.drawCenteredImage(g2, this.pos[y][x].getImage(), x * Game.tileSize + xPieceOffset, y * Game.tileSize + yPieceOffset, pieceScale, pieceScale);
            }
        }
        if (this.selected.isValid() && this.dragging) {
            Widget.drawCenteredImage(g2, this.pos[this.selected.y][this.selected.x].getImage(), this.mouseX, this.mouseY, pieceScale, pieceScale);
        }
    }
}