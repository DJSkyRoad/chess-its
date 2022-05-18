package main.gui;

import main.Game;
import main.math.ChessPos;
import main.math.MathUtils;
import main.math.Move;
import main.pieces.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Board {
    public static final int scale = 8;
    private static final int offset = Game.tileSize;
    private static final int pieceScale = (int)(0.6F * Game.tileSize);
    private static final int pieceOffset = offset + Game.tileSize / 2;

    private BufferedImage selectImg;
    private float selectScale = 1.25F;
    private float animSpeed = 0.015F;

    public boolean whiteTurn = true;

    public ChessPos hovered = new ChessPos(-1, -1);
    public ChessPos selected = new ChessPos(-1, -1);
    
    private List<Move> moves;

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
            this.selectImg = ImageIO.read(Objects.requireNonNull(getClass()
                    .getResourceAsStream("/resources/select.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.refreshMoves();
    }
    
    public void refreshMoves() {
    	this.moves = this.generateAllMoves(this.pos, this.whiteTurn);
        this.moves = this.getLegalMoves(this.moves, this.whiteTurn);
    }

    public boolean isCheckMate() {
        return this.moves.isEmpty();
    }

    private List<Move> generateAllMoves(ChessPiece[][] pos, boolean white) {
        List<Move> moves = new ArrayList<>();
        for (int y = 0; y < pos.length; y++) {
            for (int x = 0; x < pos.length; x++) {
                ChessPiece piece = pos[y][x];
                if (piece != null && piece.isWhite() == white) moves.addAll(piece.getMoves(new ChessPos(x, y), pos));
            }
        }
        return moves;
    }

    private List<Move> getLegalMoves(List<Move> moves, boolean white) {
        List<Move> newMoves = new ArrayList<>();
        for (Move move : moves) {
            ChessPiece[][] postField = move.getTestPosCopy(this.pos);
            List<Move> postMoves = this.generateAllMoves(postField, !white);
            for (Move postMove : postMoves) {
                ChessPiece piece = postField[postMove.dest.y][postMove.dest.x];
                if (!piece.isKing() || piece.isWhite() != white) newMoves.add(move);
            }
        }
        return newMoves;
    }

    public boolean canSelect(ChessPos chessPos) {
        return chessPos.isValid() && ((this.pos[chessPos.y][chessPos.x] != null
                && this.pos[chessPos.y][chessPos.x].isWhite() == this.whiteTurn)
                || (this.selected.isValid() && this.canMoveSelectedTo(chessPos)));
    }

    public boolean canMoveSelectedTo(ChessPos dest) {
        if (!this.selected.isValid() || !dest.isValid()) return false;
        for (Move move : this.moves) {
            if (move.pos.equals(this.selected) && move.dest.equals(dest)) return true;
        }
        return false;
    }

    private ChessPiece[][] copyPos() {
        ChessPiece[][] pos2 = new ChessPiece[this.pos.length][this.pos.length];
        for (int i = 0; i < this.pos.length; i++) {
            System.arraycopy(this.pos[i], 0, pos2[i], 0, this.pos.length);
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