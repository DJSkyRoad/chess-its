package main.pieces;

import main.gui.Board;
import main.math.ChessPos;
import main.math.Move;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Pawn extends ChessPiece {
    public Pawn(boolean white) {
        super(white);
        try {
            this.image = ImageIO.read(Objects.requireNonNull(getClass()
                    .getResourceAsStream(white ? "/resources/white_pawn.png" : "/resources/black_pawn.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean canMoveTo(int xPiece, int yPiece, int xDest, int yDest, boolean foeOnDest) {
        int yDist = yDest - yPiece;
        int xDist = xDest - xPiece;
        return ((this.white && (yDist == -1 || (yDist == -2 && yPiece == Board.scale - 2)))
                || (!this.white && (yDist == 1 || (yDist == 2 && yPiece == 1))))
                && ((xDist == 0 && !foeOnDest) || (Math.abs(xDist) == 1 && foeOnDest));
    }

    @Override
    public char getName() {
        return white ? 'p' : 'P';
    }

    @Override
    public String toString() {
        return white ? "\u265F" : "\u2659";
    }

    @Override
    public List<Move> getMoves(ChessPos pos, ChessPiece[][] board) {
        List<Move> moves = new ArrayList<>();

        if (this.white) {
            ChessPos dest = pos.add(0, 1);
            if (dest.isValid() && board[dest.y][dest.x] == null) moves.add(new Move(pos, dest));
            dest = pos.add(1, 1);
            if (dest.isValid() && board[dest.y][dest.x] != null && !board[dest.y][dest.x].white) moves.add(new Move(pos, dest));
            dest = pos.add(-1, 1);
            if (dest.isValid() && board[dest.y][dest.x] != null && !board[dest.y][dest.x].white) moves.add(new Move(pos, dest));
            dest = pos.add(0, 2);
            if (dest.isValid() && board[dest.y][dest.x] == null && pos.y == 1) moves.add(new Move(pos, dest));
        }
        else {
            ChessPos dest = pos.add(0, -1);
            if (dest.isValid() && board[dest.y][dest.x] == null) moves.add(new Move(pos, dest));
            dest = pos.add(1, -1);
            if (dest.isValid() && board[dest.y][dest.x] != null && board[dest.y][dest.x].white) moves.add(new Move(pos, dest));
            dest = pos.add(-1, -1);
            if (dest.isValid() && board[dest.y][dest.x] != null && board[dest.y][dest.x].white) moves.add(new Move(pos, dest));
            dest = pos.add(0, -2);
            if (dest.isValid() && board[dest.y][dest.x] == null && pos.y == Board.scale - 2) moves.add(new Move(pos, dest));
        }

        return moves;
    }
}
