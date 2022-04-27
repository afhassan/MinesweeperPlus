package GUI;

import Implementation.BoardInterface;
import Implementation.HexagonBoard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class HexagonBoardPanel extends JPanel {
    private final int NUM_IMAGES = 14;
    private final int CELL_SIZE = 32;
    private final int N_MINES;
    private final int N_ROWS;
    private final int N_COLS;
    private final int BOARD_WIDTH;
    private final int BOARD_HEIGHT;

    private Image[] imgs;
    private BoardInterface board;
    private CountersPanel countersPanel;
    private boolean firstClick = true;
    private boolean newGameClick = false;

    public HexagonBoardPanel(int n_mines, int n_rows, int n_cols, CountersPanel countersPanel) {
        this.N_MINES = n_mines;
        this.N_ROWS = n_rows;
        this.N_COLS = n_cols;
        this.BOARD_WIDTH = (N_COLS+1) * CELL_SIZE;
        this.BOARD_HEIGHT = ((N_ROWS) * (CELL_SIZE)) - ((N_ROWS-2) * (CELL_SIZE/4));

        imgs = new Image[NUM_IMAGES];
        for (int i = 0; i < NUM_IMAGES; i++) {
            if (i > 6 && i < 9) {continue;}
            var path = "resources/" + i + "hex.png";
            imgs[i] = (new ImageIcon(path)).getImage();
        }

        setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
        addMouseListener(new HexagonBoardMouseListener());
        initModel(N_ROWS, N_COLS, N_MINES);
        this.countersPanel = countersPanel;
        this.countersPanel.updateFlags(board.getNumFlagsRemaining());
        countersPanel.updateStatusFace(0);
    }

    public void initModel(int numRows, int numCols, int numMines) {
        this.board = new HexagonBoard(numRows, numCols, numMines);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (board.isGameOver()) {
            countersPanel.stopTimer();
        }
        for (int i = 0; i < N_ROWS; i++) {
            for (int j = 0; j < N_COLS; j++) {
                int temp = 9;
                boolean flagged = board.getIsFlagged(i,j);
                boolean cleared = board.getIsCleared(i,j);
                boolean hasMine = board.getHasMine(i,j);
                int numOfAdjMines = board.getNumAdjacentMines(i,j);

                if (!cleared && flagged && !board.isGameOver()) {
                    temp = 10;
                }
                else if (cleared && !hasMine) {
                    temp = numOfAdjMines;
                }
                else if (flagged && !hasMine && board.isGameOver()) {
                    temp = 10;
                }
                else if (cleared && hasMine && board.isGameOver()) {
                    temp = 11;
                }
                else if (!flagged && hasMine && board.isGameOver() && board.isSolved()){
                    temp = 10;
                }
                else if (!flagged && hasMine && board.isGameOver() && !board.isSolved()){
                    temp = 12;
                }
                else if (flagged && hasMine && board.isGameOver()) {
                    temp = 13;
                }
                else {
                    temp = 9;
                }
                if (i%2 == 1) {
                    g.drawImage(imgs[temp], (j * CELL_SIZE + 2),
                            (i * CELL_SIZE - (i * CELL_SIZE/4)), this);
                }
                else {
                    g.drawImage(imgs[temp], (j * CELL_SIZE + CELL_SIZE/2 + 2),
                            (i * CELL_SIZE - (i * CELL_SIZE/4)), this);
                }

            }
        }
    }

    private class HexagonBoardMouseListener extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent e) {

            int x = e.getX();
            int y = e.getY();

            int cRow = (int) (y / 24.0);
            int cCol;

            boolean rowIsOdd = cRow % 2 == 1;

            if (rowIsOdd)
                cCol = (int) (x / 32.0);
            else {
                cCol = (int) ((x - 16.0) / 32.0);
                if (x < 16) {
                    cCol = -1;
                }
            }
            double relY = y - (cRow * 24.0);
            double relX;

            if (rowIsOdd)
                relX = x - (cCol * 32.0);
            else
                relX = (x - (cCol * 32.0)) - 16.0;

            if (relY < ((-8.0/16.0) * relX) + 8.0) // left edge
            {
                cRow--;
                if (rowIsOdd)
                    cCol--;
            }
            else if (relY < ((8.0/16.0) * relX) - 8.0) // right edge
            {
                cRow--;
                if (!rowIsOdd)
                    cCol++;
            }
            if (cCol >= 0 && cCol < N_COLS && cRow >= 0 && cRow < N_ROWS) {
                if (!newGameClick){
                    if (e.getButton() == MouseEvent.BUTTON3) {
                        board.toggleFlagForField(cRow, cCol);
                        countersPanel.updateFlags(board.getNumFlagsRemaining());
                    } else {
                        if (firstClick) {
                            countersPanel.startTimer();
                        }
                        countersPanel.updateStatusFace(1);
                        board.clearField(cRow, cCol, firstClick);
                        firstClick = false;
                    }
                } else {
                    initModel(N_ROWS, N_COLS, N_MINES);
                    countersPanel.updateFlags(board.getNumFlagsRemaining());
                    countersPanel.updateStatusFace(0);
                    countersPanel.resetTimer();
                    newGameClick = false;
                    firstClick = true;
                }

                if (board.isGameOver()) {
                    if (board.isSolved()) {
                        countersPanel.checkForNewBestSolveTime();
                    }
                    newGameClick = true;
                }
                repaint();
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            countersPanel.updateStatusFace(0);
            if (board.isGameOver()) {
                if (board.isSolved()) {
                    countersPanel.updateStatusFace(2);
                } else {
                    countersPanel.updateStatusFace(3);
                }
            }
        }

    }

}
