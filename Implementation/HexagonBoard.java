package Implementation;

// A class representing a Minesweeper board with hexagonal fields.
public class HexagonBoard extends Board {

    // Create a new HexagonBoard object.
    public HexagonBoard(int numRows, int numCols, int numMines) {
        super(numRows, numCols, numMines);
    }

    // Set the adjacent fields for the field at coordinate (x, y).
    @Override
    protected void initializeAdjacentFields(int x, int y) {
        Field field = this.fields.get(x).get(y);

        // Check for possible adjacent fields which are common to both indented and non-indented rows.
        if (isValidField(x - 1, y)) {
            field.appendToAdjacentFields(this.fields.get(x - 1).get(y));
        }
        if (isValidField(x, y - 1)) {
            field.appendToAdjacentFields(this.fields.get(x).get(y - 1));
        }
        if (isValidField(x, y + 1)) { 
            field.appendToAdjacentFields(this.fields.get(x).get(y + 1));
        }
        if (isValidField(x + 1, y)) {
            field.appendToAdjacentFields(this.fields.get(x + 1).get(y));
        }

        // Check for possible adjacent fields which are unique to indented (even) rows.
        if (x % 2 == 0) {
            if (isValidField(x - 1, y + 1)) {
                field.appendToAdjacentFields(this.fields.get(x - 1).get(y + 1));
            }
            if (isValidField(x + 1, y + 1)) {
                field.appendToAdjacentFields(this.fields.get(x + 1).get(y + 1));
            }
        }

        // Check for possible adjacent fields which are unique to non-indented (odd) rows.
        else if (x % 2 == 1) {
            if (isValidField(x - 1, y - 1)) {
                field.appendToAdjacentFields(this.fields.get(x - 1).get(y - 1));
            }
            if (isValidField(x + 1, y - 1)) {
                field.appendToAdjacentFields(this.fields.get(x + 1).get(y - 1));
            }
        }
    }

}
