package main.pieces;

import main.math.ChessPos;
import main.math.Move;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Knight extends ChessPiece {
    public Knight(boolean white) {
        super(white);
        try {
            this.image = ImageIO.read(Objects.requireNonNull(getClass()
                    .getResourceAsStream(white ? "/resources/white_knight.png" : "/resources/black_knight.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return this.isWhite() ? "\u265E" : "\u2658";
    }

    @Override
    public List<Move> getMoves(ChessPos pos, ChessPiece[][] board) {
        List<Move> moves = new ArrayList<>();

        addIfPossible(moves, pos, pos.add(2, 1), board);
        addIfPossible(moves, pos, pos.add(1, 2), board);
        addIfPossible(moves, pos, pos.add(-2, 1), board);
        addIfPossible(moves, pos, pos.add(-1, 2), board);
        addIfPossible(moves, pos, pos.add(2, -1), board);
        addIfPossible(moves, pos, pos.add(1, -2), board);
        addIfPossible(moves, pos, pos.add(-2, -1), board);
        addIfPossible(moves, pos, pos.add(-1, -2), board);

        return moves;
    }

    private void addIfPossible(List<Move> moves, ChessPos pos, ChessPos dest, ChessPiece[][] board) {
        if (dest.isValid() && (board[dest.y][dest.x] == null || board[dest.y][dest.x].isWhite() != this.isWhite()))
            moves.add(new Move(pos, dest));
    }
}
