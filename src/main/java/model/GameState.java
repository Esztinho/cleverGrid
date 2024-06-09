package model;

import javafx.beans.property.ReadOnlyObjectWrapper;
import puzzle.State;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.StringJoiner;

/**
 * Represents the state of the game.
 */
public class GameState implements State<Direction> {
    private static final int[][] DEFAULT_BOARD = {
            {0, 0, 0, 0, 1, 0, 0, 0},
            {0, 0, 1, 0, 0, 0, 1, 0},
            {0, 0, -1, 0, 0, 0, 0, -1},
            {0, 0, 1, 0, 1, 0, 0, 0},
            {1, -1, 0, 1, 0, 0, 0, 1},
            {0, 0, 0, 1, 0, -1, 1, 0},
            {0, 0, 1, 0, 0, 0, 0, 1},
            {1, 0, 0, -1, 0, 0, 0, 0}
    };

    /**
     * The size of the board.
     */
    public static final int BOARD_SIZE = 8;
    private int[][] board;
    private int figureRow;
    private int figureCol;
    private boolean nextMoveIsTwoSteps;

    /**
     * Creates a {@code GameState} object that corresponds to the original
     * initial state of the game.
     */
    public GameState() {
        this(DEFAULT_BOARD, 0, 0, true);
    }
    public GameState(int[][] initialBoard, int row, int col, boolean nextMoveIsTwo) {
        checkParameters(initialBoard, row, col);
        this.board = Arrays.stream(initialBoard).map(int[]::clone).toArray(int[][]::new);

        this.figureRow = row;
        this.figureCol = col;

        this.nextMoveIsTwoSteps = nextMoveIsTwo;
    }

    private void checkParameters(int[][] initialBoard, int row, int col) {
        for (int[] rowArray : initialBoard) {
            for (int cell : rowArray) {
                if (cell != -1 && cell != 0 && cell != 1) {
                    throw new IllegalArgumentException("Invalid cell value");
                }
            }
        }
        if (row < 0 || row >= 8 || col < 0 || col >= 8) {
            throw new IllegalArgumentException("Invalid initial figure position");
        }
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

    /**
     * {@return whether the puzzle is solved}
     */
    @Override
    public boolean isSolved() {
        int goalRow = 7;
        int goalCol = 7;
        return figureRow == goalRow && figureCol == goalCol;
    }

    /**
     * Checks if moving the figure in the specified direction is legal
     *
     * @param direction a direction in which the figure is to be moved
     * @return true if the move is legal, false otherwise
     */
    @Override
    public boolean isLegalMove(Direction direction) {
        int moveLength = nextMoveIsTwoSteps ? 2 : 3;
        int newRow = figureRow + direction.getRowChange() * moveLength;
        int newCol = figureCol + direction.getColChange() * moveLength;

        if (newRow >= 0 && newRow < BOARD_SIZE && newCol >= 0 && newCol < BOARD_SIZE && board[newRow][newCol] != -1) {
            int stepRow = direction.getRowChange();
            int stepCol = direction.getColChange();

            for (int i = 1; i <= moveLength; i++) {
                int intermediateRow = figureRow + stepRow * i;
                int intermediateCol = figureCol + stepCol * i;
                if (board[intermediateRow][intermediateCol] == -1) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * Moves the figure in the specified direction if the move is legal
     *
     * @param direction a direction in which the figure is to be moved
     */
    @Override
    public void makeMove(Direction direction) {
        if (isLegalMove(direction)) {
            int moveLength = nextMoveIsTwoSteps ? 2 : 3;
            switch (direction) {
                case UP -> setFigureRow(figureRow - moveLength);
                case RIGHT -> setFigureCol(figureCol + moveLength);
                case DOWN -> setFigureRow(figureRow + moveLength);
                case LEFT -> setFigureCol(figureCol - moveLength);
            }

            if (board[figureRow][figureCol] == 1) {
                toggleMoveLength();
            }
        }
    }

    /**
     * {@return the set of directions to which the player can be moved}
     */
    @Override
    public Set<Direction> getLegalMoves() {
        Set<Direction> legalMoves = new HashSet<>();
        for (Direction direction : Direction.values()) {
            if (isLegalMove(direction)) {
                legalMoves.add(direction);
            }
        }
        return legalMoves;
    }

    @Override
    public GameState clone() {
        int[][] clonedBoard = Arrays.stream(this.board).map(int[]::clone).toArray(int[][]::new);
        return new GameState(clonedBoard, this.figureRow, this.figureCol, this.nextMoveIsTwoSteps);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GameState)) return false;
        GameState gameState = (GameState) o;
        return figureRow == gameState.figureRow &&
                figureCol == gameState.figureCol &&
                nextMoveIsTwoSteps == gameState.nextMoveIsTwoSteps &&
                Arrays.deepEquals(board, gameState.board);
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public String toString() {
        return "GameState{" +
                "figureRow=" + figureRow +
                ", figureCol=" + figureCol +
                ", nextMoveIsTwoSteps=" + nextMoveIsTwoSteps +
                '}';
    }
}
