public class Board {
    public static final int scale = 8;
    public static final int tileWidth = 7;
    public static final int tileHeight = 3;
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

    public boolean canMovePieceTo(char name, boolean whiteTurn, int xPiece, int yPiece, int xDest, int yDest) {
        ChessPiece piece = pos[xPiece][yPiece];
        ChessPiece destPiece = pos[xDest][yDest];
        if (piece == null
                || piece.getName() != name
                || piece.white != whiteTurn
                || (destPiece != null && destPiece.white == whiteTurn)) return false;
        return piece.canMoveTo(xPiece, yPiece, xDest, yDest);
    }

    public void movePieceTo(int xPiece, int yPiece, int xDest, int yDest) {
        ChessPiece piece = pos[xPiece][yPiece];
        pos[xPiece][yPiece] = null;
        pos[xDest][yDest] = piece;
    }

    public String toString() {
        String s = "";
        s += this.getLetterString();
        for (int r = 0; r < scale; r++) {
            for (int l = 0; l < tileHeight; l++) {
                for (int c = 0; c < scale; c++) {
                    s += this.getTileString(c, r, l);
                }
            }
        }
        s += this.getLetterString();
        return s;
    }

    private String getTileString(int x, int y, int line) {
        if (line >= tileHeight || line < 0) return "INVALID LINE INDEX";
        String s = "";
        if (line == 0 && y > 0) return s;
        if (x == 0) {
            if (line == tileHeight / 2) s += (Board.scale - y) + " ";
            else s += "  ";
        }
        for (int column = 0; column < tileWidth; column++) {
            if (column == 0 && x > 0) continue;
            if (line == 0) {
                if (column == 0) {
                    if (x == 0 && y == 0) s += '\u250C';
                    else if (x == 0) s += '\u251C';
                    else if (y == 0) s += '\u252C';
                    else s += '\u253C';
                } else if (column == tileWidth - 1) {
                    if (x == Board.scale - 1 && y == 0) s += '\u2510';
                    else if (x == Board.scale - 1) s += '\u2524';
                    else if (y == 0) s += '\u252C';
                    else s += '\u253C';
                } else s += '\u2500';
            } else if (line == tileHeight - 1) {
                if (column == 0) {
                    if (x == 0 && y == Board.scale - 1) s += '\u2514';
                    else if (x == 0) s += '\u251C';
                    else if (y == Board.scale - 1) s += '\u2534';
                    else s += '\u253C';
                } else if (column == tileWidth - 1) {
                    if (x == Board.scale - 1 && y == Board.scale - 1) s += '\u2518';
                    else if (x == Board.scale - 1) s += '\u2524';
                    else if (y == Board.scale - 1) s += '\u2534';
                    else s += '\u253C';
                } else s += '\u2500';
            } else {
                if (column == 0 || column == tileWidth - 1) s += '\u2502';
                else if (column == tileWidth / 2 && pos[y][x] != null) {
                    s += pos[y][x];
                } else s += " ";
            }
        }
        if (x == Board.scale - 1) {
            if (line == tileHeight / 2) s += " " + (Board.scale - y);
            s += '\n';
        }
        return s;
    }

    private String getLetterString() {
        String s = "  ";
        for (int tileX = 0; tileX < scale; tileX++) {
            for (int x = 0; x < tileWidth; x++) {
                if (tileX != 0 && x == 0) continue;
                if (x == tileWidth / 2) s += (char)(97 + tileX);
                else s += " ";
            }
        }
        s += "\n";
        return s;
    }
}
