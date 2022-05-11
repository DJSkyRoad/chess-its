package main.pieces;

import main.math.ChessPos;
import main.math.Move;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class King extends ChessPiece {
    public King(boolean white) {
        super(white);
        try {
            this.image = ImageIO.read(Objects.requireNonNull(getClass()
                    .getResourceAsStream(white ? "/resources/white_king.png" : "/resources/black_king.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean canMoveTo(int xPiece, int yPiece, int xDest, int yDest, boolean foeOnDest) {
        int xDist = Math.abs(xDest - xPiece);
        int yDist = Math.abs(yDest - yPiece);
        return !(xDist > 1 || yDist > 1);
    }

    @Override
    public char getName() {
        return white ? 'k' : 'K';
    }

    @Override
    public String toString() {
        return white ? "\u265A" : "\u2654";
    }

    @Override
    public List<Move> getMoves(ChessPos pos, ChessPiece[][] board) {
        List<Move> moves = new ArrayList<>();

        addIfPossible(moves, pos, pos.add(1, 1), board);
        addIfPossible(moves, pos, pos.add(-1, -1), board);
        addIfPossible(moves, pos, pos.add(-1, 1), board);
        addIfPossible(moves, pos, pos.add(1, -1), board);

        addIfPossible(moves, pos, pos.add(1, 0), board);
        addIfPossible(moves, pos, pos.add(0, 1), board);
        addIfPossible(moves, pos, pos.add(-1, 0), board);
        addIfPossible(moves, pos, pos.add(0, -1), board);

        return moves;
    }

    private void addIfPossible(List<Move> moves, ChessPos pos, ChessPos dest, ChessPiece[][] board) {
        if (dest.isValid() && (board[dest.y][dest.x] == null || board[dest.y][dest.x].white != this.white))
            moves.add(new Move(pos, dest));
    }
}
