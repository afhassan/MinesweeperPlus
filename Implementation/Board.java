package Implementation;

import java.util.ArrayList;
import java.util.Random;

// A class representing a Minesweeper board.
public abstract class Board implements BoardInterface {

    protected int numRows, numCols, numMines, numFlagsRemaining, numUnclearedFields;
    protected ArrayList<ArrayList<Field>> fields;
    protected boolean isGameOver;

    public Board(int numRows, int numCols, int numMines) {
        this.numRows = numRows;
        this.numCols = numCols;
        this.numMines = numMines;
        this.numFlagsRemaining = numMines;
        this.numUnclearedFields = numRows*numCols;
        this.isGameOver = false;
        this.fields = new ArrayList<>();

        for (int i = 0; i < this.numRows; ++i) {
            ArrayList<Field> tempRow = new ArrayList<>(numCols);

            for (int j = 0; j < this.numCols; ++j) {
                tempRow.add(new Field());
            }

            this.fields.add(tempRow);
        }

        this.initializeAllAdjacentFields();
    }

    public void clearField(int x, int y, boolean firstClick) {
        if (firstClick) {
            this.generateMines(x, y);
            this.initializeAllNumAdjacentMines();
        }
        
        Field field = this.fields.get(x).get(y);

        if (field.isCleared() || field.isFlagged()) {
            return; // Any given field can only be cleared once,
        }           // and a flagged field cannot be cleared.

        // If the field is uncleared, not flagged, and does not have a mine,
        // clear the field and decrement the number of uncleared fields by 1.
        else if (!field.hasMine()) {
            field.setIsCleared();
            this.numUnclearedFields -= 1;

            // If there are no adjacent minefields, clear all adjacent fields.
            if (field.getNumAdjacentMines() == 0) {
                for (Field adjField : field.getAdjacentFields()) {
                    this.clearAdjacentField(adjField);
                }
            }

            this.updateIsGameOver();
        }

        // Else, the field is uncleared and has a mine. Game over.
        else {
            field.setIsCleared();
            this.isGameOver = true;
        }
    }

    public void toggleFlagForField(int x, int y) {
        Field field = this.fields.get(x).get(y);

        if (!field.isCleared() && (this.numFlagsRemaining > 0 || field.isFlagged())) {
            field.toggleIsFlagged();

            // If, after the flag has been toggled, the field is no longer flagged,
            // increment the number of flags remaining by 1.
            if (!field.isFlagged()) {
                this.numFlagsRemaining += 1;
            }
                
            // Else, the field is now flagged, so we decrement
            // the number of flags remaining by 1.
            else {
                this.numFlagsRemaining -= 1;
            }
        }
    }

    public boolean isSolved() {
        return this.numUnclearedFields == this.numMines;
    }

    public boolean isGameOver() {
        return this.isGameOver;
    }

    public int getNumFlagsRemaining() {
        return this.numFlagsRemaining;
    }

    public boolean getHasMine(int x, int y) {
        return this.fields.get(x).get(y).hasMine();
    }

    public boolean getIsCleared(int x, int y) {
        return this.fields.get(x).get(y).isCleared();
    }

    public boolean getIsFlagged(int x, int y) {
        return this.fields.get(x).get(y).isFlagged();
    }

    public int getNumAdjacentMines(int x, int y) {
        return this.fields.get(x).get(y).getNumAdjacentMines();
    }

    // Check if the board has been solved, and if so, update the isGameOver instance variable accordingly.
    private void updateIsGameOver() {
        if (this.isSolved()) {
            this.isGameOver = true;
        }
    }

    // Randomly generate and place mines around the board. The coordinate (x, y) 
    // represents the first field the player left-clicked on, which cannot be a mine.
    private void generateMines(int x, int y) {
        ArrayList<Integer> randNumbers = getRandomNonRepeatingIntegers(this.numMines, 0, this.numRows*this.numCols - 1, x, y);
        for (int i = 0; i < this.numMines; ++i) {
            int mineCoordX = randNumbers.get(i)%this.numCols;
            int mineCoordY = randNumbers.get(i)/this.numCols;

            this.fields.get(mineCoordY).get(mineCoordX).setHasMine();
        }
    }

    // Calculate and store the number of adjacent mines for all fields on the board.
    private void initializeAllNumAdjacentMines() {
        for (int i = 0; i < this.numRows; ++i) {
            for (int j = 0; j < this.numCols; ++j) {
                this.fields.get(i).get(j).calculateNumAdjacentMines();
            }
        }
    }

    // Set the adjacent fields for all fields on the board.
    private void initializeAllAdjacentFields() {
        for (int i = 0; i < this.numRows; ++i) {
            for (int j = 0; j < this.numCols; ++j) {
                initializeAdjacentFields(i, j);
            }
        }
    }

    // Clear a given adjacent field (and possibly all adjacent fields of that field, and so on).
    private void clearAdjacentField(Field field) {
        if (field.isCleared() || field.isFlagged()) {
            return; // Any given field can only be cleared once,
        }           // and a flagged field cannot be cleared.

        else {
            field.setIsCleared();
            this.numUnclearedFields -= 1;

            // If there are no adjacent minefields, clear all adjacent fields.
            if (field.getNumAdjacentMines() == 0) {
                for (Field adjField : field.getAdjacentFields()) {
                    this.clearAdjacentField(adjField);
                }
            }
        }
    }
    
    // Generates and returns an ArrayList of random non-repeating integers.
    private ArrayList<Integer> getRandomNonRepeatingIntegers(int size, int min, int max, int excludeX, int excludeY) {
        ArrayList<Integer> numbers = new ArrayList<Integer>();
        Random random = new Random();

        while (numbers.size() < size) {
            int randomNumber = random.nextInt((max - min) + 1) + min;
            if (!numbers.contains(randomNumber) && (randomNumber/numCols != excludeX || randomNumber%numCols != excludeY)) {
                numbers.add(randomNumber);
            }
        }
        return numbers;
    }

    // Returns true if a specified coordinate (x, y) on the board can be indexed, and false otherwise.
    protected boolean isValidField(int x, int y) {
        if ((x < 0 || x >= this.numRows) || (y < 0 || y >= this.numCols)) {
            return false;
        }
        else {
            return true;
        }
    }

    protected abstract void initializeAdjacentFields(int x, int y);
    
}
