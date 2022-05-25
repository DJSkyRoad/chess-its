package main.pieces;

import main.math.ChessPos;
import main.math.Move;
import main.scenes.GameScene;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class Rook extends ChessPiece {
    public Rook(GameScene.Faction faction) {
        super(faction);
        try {
            this.image = ImageIO.read(Objects.requireNonNull(getClass()
                    .getResourceAsStream(faction.isWhite() ? "/resources/white_rook.png" : "/resources/black_rook.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return this.getFaction().isWhite() ? "\u265C" : "\u2656";
    }

    @Override
    public List<Move> getMoves(ChessPos pos, ChessPiece[][] board) {
        return this.getLines(pos, board);
    }
}
