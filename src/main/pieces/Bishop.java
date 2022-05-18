package main.pieces;

import main.gui.Board;
import main.math.ChessPos;
import main.math.Move;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Bishop extends ChessPiece {
    public Bishop(boolean white) {
        super(white);
        try {
            this.image = ImageIO.read(Objects.requireNonNull(getClass()
                    .getResourceAsStream(white ? "/resources/white_bishop.png" : "/resources/black_bishop.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return this.isWhite() ? "\u265D" : "\u2657";
    }

    @Override
    public List<Move> getMoves(ChessPos pos, ChessPiece[][] board) {
        return this.getDiagonals(pos, board);
    }
}
