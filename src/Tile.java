public class Tile {

    private final int x;
    private final int y;
    public static final int width = 5;
    public static final int height = 3;
    public static final String startPos = "";

    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public String toString(int line) {
        if (line >= height || line < 0) return "INVALID LINE INDEX";
        String s = "";
        if (this.x == 0) {
            if (line == height / 2) s += s += (Board.scale - this.y) + " ";
            else s += "  ";
        }
        for (int i = 0; i < width; i++) {
            if (i == 0 && this.x > 0) continue;
            if (line == 0) {
                if (this.y > 0) return s;
                if (i == 0) {
                    if (this.x == 0 && this.y == 0) s += '\u250C';
                    else if (this.x == 0) s += '\u251C';
                    else if (this.y == 0) s += '\u252C';
                    else s += '\u253C';
                }
                else if (i == width - 1) {
                    if (this.x == Board.scale - 1 && this.y == 0) s += '\u2510';
                    else if (this.x == Board.scale - 1) s += '\u2524';
                    else if (this.y == 0) s += '\u252C';
                    else s += '\u253C';
                }
                else s += '\u2500';
            }
            else if (line == height - 1) {
                if (i == 0) {
                    if (this.x == 0 && this.y == Board.scale - 1) s += '\u2514';
                    else if (this.x == 0) s += '\u251C';
                    else if (this.y == Board.scale - 1) s += '\u2534';
                    else s += '\u253C';
                }
                else if (i == width - 1) {
                    if (this.x == Board.scale - 1 && this.y == Board.scale - 1) s += '\u2518';
                    else if (this.x == Board.scale - 1) s += '\u2524';
                    else if (this.y == Board.scale - 1) s += '\u2534';
                    else s += '\u253C';
                }
                else s += '\u2500';
            }
            else {
                if (i == 0 || i == width - 1) s += '\u2502';
                else if (this.x == 0 && this.y == 1) s += '\u265C';
                else s += " ";
            }
        }
        if (this.x == Board.scale - 1) {
            if (line == height / 2) s += s += " " + (Board.scale - this.y) + '\n';
            else s += '\n';
        }
        return s;
    }
}
