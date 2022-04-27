package Implementation;

import java.util.ArrayList;

// An interface representing a field on a Minesweeper board.
public interface FieldInterface {

    // Set the field as a minefield.
    public void setHasMine();

    // Returns true if the field has a mine, and false otherwise.
    public boolean hasMine();

    // Calculates the number of adjacent mines for the field.
    public void calculateNumAdjacentMines();

    // Returns the number of adjacent mines for the field.
    public int getNumAdjacentMines();

    // Set the field as cleared.
    public void setIsCleared();

    // Returns true if the field is cleared, and false otherwise.
    public boolean isCleared();

    // Toggle whether the field is flagged as a minefield.
    public void toggleIsFlagged();

    // Returns true if the field is flagged, and false otherwise.
    public boolean isFlagged();

    // Append a given field to the field's ArrayList of adjacent fields.
    public void appendToAdjacentFields(Field field);

    // Returns an ArrayList of the field's adjacent fields.
    public ArrayList<Field> getAdjacentFields();

}
