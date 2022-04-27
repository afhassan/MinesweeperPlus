package Implementation;

// A class representing a Minesweeper board with square fields.
public class SquareBoard extends Board {

    // Create a new SquareBoard object.
    public SquareBoard(int numRows, int numCols, int numMines) {
        super(numRows, numCols, numMines);
    }

    // Set the adjacent fields for the field at coordinate (x, y).
    @Override 
    protected void initializeAdjacentFields(int x, int y) {
        Field field = this.fields.get(x).get(y);

        for (int i = x - 1; i <= x + 1; ++i) {
            for (int j = y - 1; j <= y + 1; ++j) {
                if (isValidField(i, j) && (this.fields.get(i).get(j) != field)) {
                    field.appendToAdjacentFields(this.fields.get(i).get(j));
                }
            }
        }
    }

}
