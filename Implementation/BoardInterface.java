package Implementation;

// An interface representing a Minesweeper board.
public interface BoardInterface {

    // Clear the field at coordinate (x, y).
    public void clearField(int x, int y, boolean firstClick);

    // Toggle whether the field at coordinate (x, y) is flagged as a minefield.
    public void toggleFlagForField(int x, int y);

    // Returns true if the board has been solved, and false otherwise.
    public boolean isSolved();

    // Returns true if the game is over (i.e., if the board has been solved or a minefield has been clicked), and false otherwise.
    public boolean isGameOver();

    // Returns the number of flags remaining.
    public int getNumFlagsRemaining();

    // Returns true if the field at coordinate (x, y) has a mine, and false otherwise.
    public boolean getHasMine(int x, int y);

    // Returns true if the field at coordinate (x, y) is cleared, and false otherwise.
    public boolean getIsCleared(int x, int y);

    // Returns true if the field at coordinate (x, y) is cleared, and false otherwise.
    public boolean getIsFlagged(int x, int y);

    // Returns the number of adjacent mines for field at coordinate (x, y).
    public int getNumAdjacentMines(int x, int y);

}
