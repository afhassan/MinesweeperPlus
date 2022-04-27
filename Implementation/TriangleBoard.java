package Implementation;
import java.util.ArrayList;

// A class representing a Minesweeper board with triangular fields.
public class TriangleBoard extends Board {

    private ArrayList<Integer> upperTriangleColsEven;

    // Create a new TriangleBoard object.
    public TriangleBoard(int numRows, int numCols, int numMines) {
        super(numRows, numCols, numMines);
    }

    // Set the adjacent fields for the field at coordinate (x, y).
    @Override 
    protected void initializeAdjacentFields(int x, int y) {
        // Initialize upperTriangleColsEven.
        if (this.upperTriangleColsEven == null) {
            this.upperTriangleColsEven = new ArrayList<>();
            for (int col = 0; col < this.numCols; col += 3) {
                upperTriangleColsEven.add(col);
                if (col != 0) {
                    col += 1;
                    if (col < this.numCols) {
                        upperTriangleColsEven.add(col);
                    }
                }
            }
        }

        Field field = this.fields.get(x).get(y);

        if (isValidField(x, y + 1)) {
            field.appendToAdjacentFields(this.fields.get(x).get(y + 1));
        }
        if (isValidField(x, y - 1)) {
            field.appendToAdjacentFields(this.fields.get(x).get(y - 1));
        }

        if ((x % 2 == 0 && this.upperTriangleColsEven.contains(y)) || (x % 2 == 1 && !this.upperTriangleColsEven.contains(y))) { 
            if (isValidField(x - 1, y)) {
                field.appendToAdjacentFields(this.fields.get(x - 1).get(y));
            }
        }
        else {
            if (isValidField(x + 1, y)) {
                field.appendToAdjacentFields(this.fields.get(x + 1).get(y));
            }
        }
    }

}
