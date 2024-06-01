package model;

import puzzle.State;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.StringJoiner;

public class GameState implements State<Direction> {

    public static final int BOARD_SIZE = 8;
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

    @Override
    public boolean isSolved() {
        int goalRow = 7;
        int goalCol = 7;
        return figureRow == goalRow && figureCol == goalCol;
    }

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
        int result = Objects.hash(figureRow, figureCol, nextMoveIsTwoSteps);
        result = 31 * result + Arrays.deepHashCode(board);
        return result;
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
