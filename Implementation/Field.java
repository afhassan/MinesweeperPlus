package Implementation;

import java.util.ArrayList;

// A class representing a field on a Minesweeper board.
public class Field implements FieldInterface {

    private boolean hasMine, isFlagged, isCleared;
    private ArrayList<Field> adjacentFields = new ArrayList<>();
    private int numAdjacentMines;

    // Create a new Field object.
    public Field() {
        this.hasMine = false;
        this.isFlagged = false;
        this.isCleared = false;
    }

    public void setHasMine() {
        this.hasMine = true;
    }

    public boolean hasMine() {
        return this.hasMine;
    }

    public void calculateNumAdjacentMines() {
        int mineCount = 0;

        for (int i = 0; i < this.adjacentFields.size(); ++i) {
            if (this.adjacentFields.get(i).hasMine()) {
                mineCount += 1;
            }
        }

        this.numAdjacentMines = mineCount;
    }

    public int getNumAdjacentMines() {
        return this.numAdjacentMines;
    }

    public void setIsCleared() {
        this.isCleared = true;
    }

    public boolean isCleared() {
        return this.isCleared;
    }

    public void toggleIsFlagged() {
        this.isFlagged = !this.isFlagged;
    }

    public boolean isFlagged() {
        return this.isFlagged;
    }

    public void appendToAdjacentFields(Field field) {
        this.adjacentFields.add(field);
    }

    public ArrayList<Field> getAdjacentFields() {
        return this.adjacentFields;
    }

}
