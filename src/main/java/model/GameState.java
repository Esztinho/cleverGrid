package model;

import java.util.Arrays;

public class GameState {
    private int[][] board;
    private int figureRow;
    private int figureCol;
    private boolean nextMoveIsTwoSteps;

    public GameState(int[][] initialBoard, int startRow, int startCol, boolean startWithTwoSteps) {
        this.board = Arrays.stream(initialBoard).map(int[]::clone).toArray(int[][]::new);
        this.figureRow = startRow;
        this.figureCol = startCol;
        this.nextMoveIsTwoSteps = startWithTwoSteps;
    }

    public int getFigureRow() {
        return figureRow;
    }

    public int getFigureCol() {
        return figureCol;
    }

    public void setFigureRow(int newRow) {
        this.figureRow = newRow;
    }

    public void setFigureCol(int newCol) {
        this.figureCol = newCol;
    }

    public int[][] getBoard() {
        return board;
    }

    public boolean isNextMoveTwoSteps() {
        return nextMoveIsTwoSteps;
    }

    public void toggleMoveLength() {
        this.nextMoveIsTwoSteps = !this.nextMoveIsTwoSteps;
    }

    public boolean isValidMove(int newRow, int newCol) {
        // Ellenőrzi, hogy a lépés érvényes-e
        int moveLength = nextMoveIsTwoSteps ? 2 : 3;
        if (newRow >= 0 && newRow < 8 && newCol >= 0 && newCol < 8 && board[newRow][newCol] != -1) {
            int rowDiff = Math.abs(newRow - figureRow);
            int colDiff = Math.abs(newCol - figureCol);
            return (rowDiff == moveLength && colDiff == 0) || (colDiff == moveLength && rowDiff == 0);
        }
        return false;
    }

    public void makeMove(int newRow, int newCol) {
        if (isValidMove(newRow, newCol)) {
            setFigureRow(newRow);
            setFigureCol(newCol);
            if (board[newRow][newCol] == 1) {
                toggleMoveLength();
            }
        }
    }
}
