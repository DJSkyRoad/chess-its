package main.scenes;

import main.AudioPlayer;
import main.gui.Board;
import main.Game;
import main.math.ChessPos;
import main.math.Move;
import main.networking.packet.MovePacket;
import main.pieces.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameScene extends Scene {
    private final Board board = new Board();
    private final GameMode gameMode;
    private final Random random = new Random();
    private final Faction playerFaction;
    public Faction currentTurn;

    private boolean dontDeselect;

    private List<Move> currentMoves;
    private Move lastMove;

    public GameScene(GameMode gameMode, Faction playerFaction) {
        this.gameMode = gameMode;
        this.playerFaction = playerFaction;
        this.currentTurn = Faction.WHITE;

        this.generateMoves(this.currentTurn);

        if (this.currentTurn != this.playerFaction && this.gameMode == GameMode.PVC) {
            this.doAITurn();
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, this.getWidth(), this.getHeight());

        this.board.draw(g2);

        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Helvetia", Font.BOLD, 30));
        Game.drawCenteredString(g2, this.currentTurn+"'s turn", this.getWidth() / 2, 30);
    }

    @Override
    public void onMouseHover(int x, int y) {
        this.board.update(x, y);
        if (this.gameMode != GameMode.PVP_OFFLINE && this.currentTurn != this.playerFaction) return;
        ChessPos chessPos = new ChessPos(x / Game.tileSize - 1, y / Game.tileSize - 1);
        if (this.canHover(chessPos)) this.board.hovered = chessPos;
        else this.board.hovered = new ChessPos(-1, -1);
    }

    @Override
    public void onMousePress(int x, int y) {
        if (this.gameMode != GameMode.PVP_OFFLINE && this.currentTurn != this.playerFaction) return;
        ChessPos chessPos = new ChessPos(x / Game.tileSize - 1, y / Game.tileSize - 1);
        if (this.canSelect(chessPos)) {
            this.board.selected = chessPos;
            this.dontDeselect = true;
            this.board.selectedMoves.clear();
            for (Move move : this.currentMoves) {
                if (move.pos.equals(chessPos)) this.board.selectedMoves.add(move.dest);
            }
        }
        if (this.board.selected.equals(chessPos)) this.board.dragging = true;
    }

    @Override
    public void onMouseRelease(int x, int y) {
        if (this.gameMode != GameMode.PVP_OFFLINE && this.currentTurn != this.playerFaction) return;
        ChessPos chessPos = new ChessPos(x / Game.tileSize - 1, y / Game.tileSize - 1);
        if (this.board.selected.equals(chessPos) && !this.dontDeselect) this.board.deselect();
        else if (this.board.selected.isValid() && this.canMoveSelectedTo(chessPos)) {
            Move move = new Move(this.board.selected, chessPos);
            this.performMove(move);
            if (this.gameMode.isOnline()) Game.INSTANCE.getConnection().ifPresent((c) -> c.sendPacket(new MovePacket(move)));
            this.changeTurn();
        }
        this.board.dragging = false;
        this.dontDeselect = false;
    }

    public ChessPiece getKing(Faction faction) {
        for (int y = 0; y < Board.scale; y++) {
            for (int x = 0; x < Board.scale; x++) {
                ChessPiece piece = this.board.pos[y][x];
                if (piece != null && piece.isKing() && piece.getFaction() == faction) return piece;
            }
        }
        return null;
    }

    public void changeTurn() {
        this.board.deselect();
        this.board.hovered = new ChessPos(-1, -1);

        this.generateMoves(this.currentTurn);
        boolean checked = this.isChecked(this.currentMoves, this.board.pos, this.currentTurn.opposite());
        this.getKing(this.currentTurn.opposite()).setChecked(checked);
        this.generateMoves(this.currentTurn.opposite());
        if (this.currentMoves.isEmpty()) {
            this.onGameOver(checked);
            return;
        }

        this.currentTurn = this.currentTurn.opposite();

        if (this.currentTurn == this.playerFaction || this.gameMode == GameMode.PVP_OFFLINE) {
            this.board.enemyMove = this.lastMove;
        }

        if (this.currentTurn != this.playerFaction && this.gameMode == GameMode.PVC) {
            this.doAITurn();
        }
    }

    private void onGameOver(boolean checked) {
        String title = checked ? "Checkmate" : "Patt";
        String subtitle = this.currentTurn+" won";
        Game.INSTANCE.getConnection().ifPresent(c -> Game.INSTANCE.closeConnection());
        Game.INSTANCE.setScene(new GameOverScene(title, subtitle));
    }

    private void doAITurn() {
        List<Move> possibleMoves = new ArrayList<>();
        int priority = 0;
        for (Move move : this.currentMoves) {
            ChessPiece enemy = this.getPiece(move.dest);
            ChessPiece[][] postBoard = move.getTestPosCopy(this.board.pos);
            List<Move> postMoves = this.getLegalMoves(this.generateAllMoves(postBoard, this.currentTurn), this.currentTurn);
            int p = 0;

            if (enemy != null) {
                p = 1;
                if (enemy.isKing()) {
                    p = 2;
                }
            }
            if (this.isChecked(postMoves, postBoard, this.currentTurn.opposite())) {
                p = 3;
            }

            if (p >= priority) {
                if (p > priority) possibleMoves.clear();
                possibleMoves.add(move);
                priority = p;
            }
        }

        int i = this.random.nextInt(possibleMoves.size());
        this.performMove(possibleMoves.get(i));
        this.changeTurn();
    }

    private ChessPiece getPiece(ChessPos chessPos) {
        return this.board.pos[chessPos.x][chessPos.y];
    }

    private void generateMoves(Faction faction) {
        this.currentMoves = this.getLegalMoves(this.generateAllMoves(this.board.pos, faction), faction);
    }

    private List<Move> generateAllMoves(ChessPiece[][] pos, Faction faction) {
        List<Move> moves = new ArrayList<>();
        for (int y = 0; y < pos.length; y++) {
            for (int x = 0; x < pos.length; x++) {
                ChessPiece piece = pos[y][x];
                if (piece != null && piece.getFaction() == faction) moves.addAll(piece.getMoves(new ChessPos(x, y), pos));
            }
        }
        return moves;
    }

    private List<Move> getLegalMoves(List<Move> moves, Faction faction) {
        List<Move> newMoves = new ArrayList<>();
        for (Move move : moves) {
            // test move
            ChessPiece[][] postField = move.getTestPosCopy(this.board.pos);
            List<Move> postMoves = this.generateAllMoves(postField, faction.opposite());
            // check whether the move was illegal
            if (!this.isChecked(postMoves, postField, faction)) newMoves.add(move);
        }
        return newMoves;
    }

    private boolean isChecked(List<Move> moves, ChessPiece[][] board, Faction faction) {
        for (Move move : moves) {
            ChessPiece piece = board[move.dest.y][move.dest.x];
            if (piece != null && piece.isKing() && piece.getFaction() == faction) {
                return true;
            }
        }
        return false;
    }

    private boolean canSelect(ChessPos chessPos) {
        return chessPos.isValid() && this.board.pos[chessPos.y][chessPos.x] != null
                && this.board.pos[chessPos.y][chessPos.x].getFaction() == this.currentTurn;
    }

    private boolean canHover(ChessPos chessPos) {
        return chessPos.isValid() && ((this.board.pos[chessPos.y][chessPos.x] != null
                && this.board.pos[chessPos.y][chessPos.x].getFaction() == this.currentTurn)
                || (this.board.selected.isValid() && this.canMoveSelectedTo(chessPos)));
    }

    private boolean canMoveSelectedTo(ChessPos dest) {
        if (!this.board.selected.isValid() || !dest.isValid()) return false;
        for (Move move : this.currentMoves) {
            if (move.pos.equals(this.board.selected) && move.dest.equals(dest)) return true;
        }
        return false;
    }

    public void performMove(Move move) {
        ChessPiece piece = this.board.pos[move.pos.y][move.pos.x];
        this.board.pos[move.pos.y][move.pos.x] = null;
        this.board.pos[move.dest.y][move.dest.x] = piece;
        this.lastMove = move;
        Game.INSTANCE.playSound(AudioPlayer.PLACE_SOUND);
        piece.onMoved(this, move);
    }

    public void spawnPiece(ChessPiece piece, ChessPos pos) {
        this.board.pos[pos.y][pos.x] = piece;
    }

    public enum Faction {
        WHITE, BLACK;

        public Faction opposite() {
            switch(this) {
                case WHITE: return BLACK;
                default:
                case BLACK: return WHITE;
            }
        }

        @Override
        public String toString() {
            switch(this) {
                default:
                case WHITE: return "White";
                case BLACK: return "Black";
            }
        }

        public boolean isWhite() {
            return this == WHITE;
        }
    }

    public enum GameMode {
        PVP_OFFLINE, PVC, PVP_ONLINE;

        @Override
        public String toString() {
            switch(this) {
                default:
                case PVP_OFFLINE: return "PvP Offline";
                case PVC: return "PvC";
                case PVP_ONLINE: return "PvP Online";
            }
        }

        public boolean isOnline() {
            return this == PVP_ONLINE;
        }

        public GameMode next() {
            int i = this.ordinal()+1 < values().length ? this.ordinal()+1 : 0;
            GameMode next = values()[i];
            return next.isOnline() ? next.next() : next;
        }
    }
}
