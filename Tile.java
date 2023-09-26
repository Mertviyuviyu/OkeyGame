public class Tile {
    
    int value;
    char color;

    /*
     * Creates a tile using the given color and value, colors are represented
     * using the following letters: Y: Yellow, B: Blue, R: Red, K: Black
     * Values can be in the range [1,13]. There are two tiles of each color value
     * combination (13 * 2 * 4) = 104 tiles, false jokers are not included in this game.
     */
    public Tile(int value, char color) {
        this.value = value;
        this.color = color;
    }

    /*
     * TODO: should check if the given tile t and this tile contain the same color and value
     * return true if they are matching, false otherwise
     */
    public boolean matchingTiles(Tile t) {
        return false;
    }

    public int compareToColorFirst(Tile t) {
        if(colorNameToInt() < t.colorNameToInt()) {
            return -1;
        }
        else if(colorNameToInt() > t.colorNameToInt()) {
            return 1;
        }
        else{
             if(getValue() < t.getValue()) {
                return -1;
            }
            else if(getValue() > t.getValue()) {
                return 1;
            }
            else{
                return 0;
            }
        }
    }

    public int compareToValueFirst(Tile t) {
        if(getValue() < t.getValue()) {
            return -1;
        }
        else if(getValue() > t.getValue()) {
            return 1;
        }
        else{
             if(colorNameToInt() < t.colorNameToInt()) {
                return -1;
            }
            else if(colorNameToInt() > t.colorNameToInt()) {
                return 1;
            }
            else{
                return 0;
            }
        }
    }

    public int colorNameToInt() {
        if(color == 'Y') {
            return 0;
        }
        else if(color == 'B') {
            return 1;
        }
        else if(color == 'R') {
            return 2;
        }
        else {
            return 3;
        }
    }

    // determines if this tile can be adjacent to the given tile in a chain
    // returns 0 if the tiles cannot be adjacent in a chain
    // returns 1 if the have matching color with consecutive ordering
    // returns 2 if they have matching value with different coloring
    public int canFormChainWith(Tile t) {
        // can be adjacent if same color and consecutive number
        if(t.getColor() == color && Math.abs(t.getValue() - value) == 1) {
            return 1;
        }

        // can be adjacent if same number but different color
        if(t.getColor() != color && t.getValue() == value) {
            return 2;
        }

        return 0;
    }

    public String toString() {
        return "" + value + color;
    }

    public int getValue() {
        return value;
    }

    public char getColor() {
        return color;
    }
    
}
