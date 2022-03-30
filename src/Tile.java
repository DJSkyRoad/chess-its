public class Tile {

    private final int x;
    private final int y;
    public static final int size = 3;

    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public String toString(int line) {
        if (line >= size || line < 0) return "";
        String s = "";
        if (line == 0)
        {
            for (int i = 0; i < size; i++)
            {
                if (i == 0) s += '\u250C';
                else if (i == size - 1) s += '\u2510';
                else s += '\u2500';
            }
        }
        else if (line == size - 1)
        {
            for (int i = 0; i < size; i++)
            {
                if (i == 0) s += '\u2514';
                else if (i == size - 1) s += '\u2518';
                else s += '\u2500';
            }
        }
        else
        {
            for (int i = 0; i < size; i++)
            {
                if (i == 0 || i == size - 1) s += '\u2502';
                else s += ' ';
            }
        }
        return s;
    }
}
