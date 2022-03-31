public class Tile {
    private final int x;
    private final int y;
    public static final int width = 7;
    public static final int height = 3;

    public static final char r = '\u265C';
    public static final char n = '\u265E';
    public static final char b = '\u265D';
    public static final char q = '\u265B';
    public static final char k = '\u265A';
    public static final char p = '\u265F';
    public static final char R = '\u2656';
    public static final char N = '\u2658';
    public static final char B = '\u2657';
    public static final char Q = '\u2655';
    public static final char K = '\u2654';
    public static final char P = '\u2659';
    public static char[][] pos = {
            {R, N, B, Q, K, B, N, R},
            {P, P, P, P, P, P, P, P},
            {'.','.','.','.','.','.','.','.'},
            {'.','.','.','.','.','.','.','.'},
            {'.','.','.','.','.','.','.','.'},
            {'.','.','.','.','.','.','.','.'},
            {p, p, p, p, p, p, p, p},
            {r, n, b, q, k, b, n, r}};


    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public String toString(int line) {
        if (line >= height || line < 0) return "INVALID LINE INDEX";
        String s = "";
        if (line == 0 && this.y > 0) return s;
        if (this.x == 0) {
            if (line == height / 2) s += (Board.scale - this.y) + " ";
            else s += "  ";
        }
        for (int column = 0; column < width; column++) {
            if (column == 0 && this.x > 0) continue;
            if (line == 0) {
                if (column == 0) {
                    if (this.x == 0 && this.y == 0) s += '\u250C';
                    else if (this.x == 0) s += '\u251C';
                    else if (this.y == 0) s += '\u252C';
                    else s += '\u253C';
                } else if (column == width - 1) {
                    if (this.x == Board.scale - 1 && this.y == 0) s += '\u2510';
                    else if (this.x == Board.scale - 1) s += '\u2524';
                    else if (this.y == 0) s += '\u252C';
                    else s += '\u253C';
                } else s += '\u2500';
            } else if (line == height - 1) {
                if (column == 0) {
                    if (this.x == 0 && this.y == Board.scale - 1) s += '\u2514';
                    else if (this.x == 0) s += '\u251C';
                    else if (this.y == Board.scale - 1) s += '\u2534';
                    else s += '\u253C';
                } else if (column == width - 1) {
                    if (this.x == Board.scale - 1 && this.y == Board.scale - 1) s += '\u2518';
                    else if (this.x == Board.scale - 1) s += '\u2524';
                    else if (this.y == Board.scale - 1) s += '\u2534';
                    else s += '\u253C';
                } else s += '\u2500';
            } else {
                if (column == 0 || column == width - 1) s += '\u2502';
                else if (column == width / 2 && pos[this.y][this.x] != '.') {
                    s += pos[this.y][this.x];
                }else s += " ";
            }
        }
        if (this.x == Board.scale - 1) {
            if (line == height / 2) s += " " + (Board.scale - this.y);
            s += '\n';
        }
        return s;
    }
}
