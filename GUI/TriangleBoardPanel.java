package GUI;

import Implementation.BoardInterface;
import Implementation.HexagonBoard;
import Implementation.TriangleBoard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TriangleBoardPanel extends JPanel{
    private final int NUM_IMAGES = 14;
    private final int CELL_SIZE = 32;
    private final int N_MINES;
    private final int N_ROWS;
    private final int N_COLS;
    private final int BOARD_WIDTH;
    private final int BOARD_HEIGHT;

    private Image[] imgsDL = new Image[NUM_IMAGES]; //Down Left Images
    private Image[] imgsDR = new Image[NUM_IMAGES]; //Down Right Images
    private Image[] imgsUL = new Image[NUM_IMAGES]; //Up Left Images
    private Image[] imgsUR = new Image[NUM_IMAGES]; //Up Right Images
    private BoardInterface board;
    private CountersPanel countersPanel;
    private boolean firstClick = true;
    private boolean newGameClick = false;

    public TriangleBoardPanel(int n_mines, int n_rows, int n_cols, CountersPanel countersPanel) {
        this.N_MINES = n_mines*2;
        this.N_ROWS = n_rows;
        this.N_COLS = n_cols*2;
        this.BOARD_WIDTH = (N_COLS/2) * CELL_SIZE + 1;
        this.BOARD_HEIGHT = N_ROWS * CELL_SIZE + 1;

        for (int i = 0; i < NUM_IMAGES; i++) {
            if (i > 3 && i < 9) {continue;}
            var pathDL = "resources/" + i + "tri-dl.png";
            imgsDL[i] = (new ImageIcon(pathDL)).getImage();
            var pathDR = "resources/" + i + "tri-dr.png";
            imgsDR[i] = (new ImageIcon(pathDR)).getImage();
            var pathUL = "resources/" + i + "tri-ul.png";
            imgsUL[i] = (new ImageIcon(pathUL)).getImage();
            var pathUR = "resources/" + i + "tri-ur.png";
            imgsUR[i] = (new ImageIcon(pathUR)).getImage();
        }

        setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
        addMouseListener(new TriangleBoardPanel.TriangleBoardMouseListener());
        initModel(N_ROWS, N_COLS, N_MINES);
        this.countersPanel = countersPanel;
        this.countersPanel.updateFlags(board.getNumFlagsRemaining());
        countersPanel.updateStatusFace(0);
    }

    public void initModel(int numRows, int numCols, int numMines) {
        this.board = new TriangleBoard(numRows, numCols, numMines);
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
                if (i%2 == 0) {
                    if (j%4 == 0) {
                        g.drawImage(imgsUL[temp], ((j / 2) * CELL_SIZE), ((i ) * CELL_SIZE), this);
                    }
                    else if (j%4 == 1) {
                        g.drawImage(imgsDR[temp], ((j / 2) * CELL_SIZE), ((i) * CELL_SIZE), this);
                    }
                    else if (j%4 == 2) {
                        g.drawImage(imgsDL[temp], ((j / 2) * CELL_SIZE), ((i) * CELL_SIZE), this);
                    }
                    else if (j%4 == 3) {
                        g.drawImage(imgsUR[temp], ((j / 2) * CELL_SIZE), ((i) * CELL_SIZE), this);
                    }
                }
                else if (i%2 == 1) {
                    if (j%4 == 0) {
                        g.drawImage(imgsDL[temp], ((j / 2) * CELL_SIZE), ((i) * CELL_SIZE), this);
                    }
                    else if (j%4 == 1) {
                        g.drawImage(imgsUR[temp], ((j / 2) * CELL_SIZE), ((i) * CELL_SIZE), this);
                    }
                    else if (j%4 == 2) {
                        g.drawImage(imgsUL[temp], ((j / 2) * CELL_SIZE), ((i) * CELL_SIZE), this);
                    }
                    else if (j%4 == 3) {
                        g.drawImage(imgsDR[temp], ((j / 2) * CELL_SIZE), ((i) * CELL_SIZE), this);
                    }
                }

            }
        }
    }

    private class TriangleBoardMouseListener extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent e) {

            int x = e.getX();
            int y = e.getY();

            int cCol = (int) (x / 32.0);
            int cRow = (int) (y / 32.0);


            boolean rowIsOdd = cRow % 2 == 1;
            boolean colIsOdd = cCol % 2 == 1;

            double relY = y - (cRow * 32.0);
            double relX = x - (cCol * 32.0);

            if (rowIsOdd) {
                if (colIsOdd) {
                    //Odd Row Odd Col
                    if (relY < ((-1.0 * relX) + 32.0)) {
                        //Left triangle
                        cCol = cCol*2;
                    } else {
                        //Right triangle
                        cCol = cCol*2 + 1;
                    }
                }
                else {
                    //Odd Row Even Col
                    if (relY < ((1.0 * relX) + 0.0)) {
                        //Right triangle
                        cCol = cCol*2 + 1;
                    } else {
                        //Left triangle
                        cCol = cCol*2;
                    }
                }
            }
            else {
                if (colIsOdd) {
                    //Even Row Odd Col
                    if (relY < ((1.0 * relX) + 0.0)) {
                        //Right triangle
                        cCol = cCol*2 + 1;
                    } else {
                        //Left triangle
                        cCol = cCol*2;
                    }
                }
                else {
                    //Even Row Even Col
                    if (relY < ((-1.0 * relX) + 32.0)) {
                        //Left triangle
                        cCol = cCol*2;
                    } else {
                        //Right triangle
                        cCol = cCol*2 + 1;
                    }
                }
            }

            if (cCol >= 0 && cCol < N_COLS && cRow >= 0 && cRow < N_ROWS) {
                if (!newGameClick) {
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
