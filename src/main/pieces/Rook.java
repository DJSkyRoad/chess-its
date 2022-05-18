package main.pieces;

import main.gui.Board;
import main.math.ChessPos;
import main.math.Move;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Rook extends ChessPiece {
    public Rook(boolean white) {
        super(white);
        try {
            this.image = ImageIO.read(Objects.requireNonNull(getClass()
                    .getResourceAsStream(white ? "/resources/white_rook.png" : "/resources/black_rook.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return this.isWhite() ? "\u265C" : "\u2656";
    }

    @Override
    public List<Move> getMoves(ChessPos pos, ChessPiece[][] board) {
        return this.getLines(pos, board);
    }
}
