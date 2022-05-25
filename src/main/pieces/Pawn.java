package main.pieces;

import main.gui.Board;
import main.math.ChessPos;
import main.math.Move;
import main.scenes.GameScene;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Pawn extends ChessPiece {
    public Pawn(GameScene.Faction faction) {
        super(faction);
        try {
            this.image = ImageIO.read(Objects.requireNonNull(getClass()
                    .getResourceAsStream(faction.isWhite() ? "/resources/white_pawn.png" : "/resources/black_pawn.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return this.getFaction().isWhite() ? "\u265F" : "\u2659";
    }

    @Override
    public List<Move> getMoves(ChessPos pos, ChessPiece[][] board) {
        List<Move> moves = new ArrayList<>();

        if (this.getFaction().isWhite()) {
            ChessPos dest = pos.add(0, -1);
            if (dest.isValid() && board[dest.y][dest.x] == null) moves.add(new Move(pos, dest));
            dest = pos.add(1, -1);
            if (dest.isValid() && board[dest.y][dest.x] != null && !board[dest.y][dest.x].getFaction().isWhite()) moves.add(new Move(pos, dest));
            dest = pos.add(-1, -1);
            if (dest.isValid() && board[dest.y][dest.x] != null && !board[dest.y][dest.x].getFaction().isWhite()) moves.add(new Move(pos, dest));
            dest = pos.add(0, -2);
            if (dest.isValid() && board[dest.y][dest.x] == null && pos.y == Board.scale - 2) moves.add(new Move(pos, dest));
        }
        else {
            ChessPos dest = pos.add(0, 1);
            if (dest.isValid() && board[dest.y][dest.x] == null) moves.add(new Move(pos, dest));
            dest = pos.add(1, 1);
            if (dest.isValid() && board[dest.y][dest.x] != null && board[dest.y][dest.x].getFaction().isWhite()) moves.add(new Move(pos, dest));
            dest = pos.add(-1, 1);
            if (dest.isValid() && board[dest.y][dest.x] != null && board[dest.y][dest.x].getFaction().isWhite()) moves.add(new Move(pos, dest));
            dest = pos.add(0, 2);
            if (dest.isValid() && board[dest.y][dest.x] == null && pos.y == 1) moves.add(new Move(pos, dest));
        }

        return moves;
    }
}
