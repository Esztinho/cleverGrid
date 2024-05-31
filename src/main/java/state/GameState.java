package state;
import puzzle.State;
import puzzle.solver.BreadthFirstSearch;

import java.util.HashSet;
import java.util.Set;

public class GameState implements State<Integer>{
    private final int[][] board;
    private int figureRow;
    private int figureCol;
    private boolean nextMoveIsTwo;

    public GameState(int[][] board, int figureRow, int figureCol, boolean nextMoveIsTwo) {
        this.board = board;
        this.figureRow = figureRow;
        this.figureCol = figureCol;
        this.nextMoveIsTwo = nextMoveIsTwo;
    }
    @Override
    public boolean isSolved() {
        return figureRow == board.length - 1 && figureCol == board[0].length - 1;
    }

    @Override
    public boolean isLegalMove(Integer integer) {
        int moveLength = integer;
        return isValidMove(figureRow + moveLength, figureCol) ||
                isValidMove(figureRow - moveLength, figureCol) ||
                isValidMove(figureRow, figureCol + moveLength) ||
                isValidMove(figureRow, figureCol - moveLength);
    }

    @Override
    public void makeMove(Integer integer) {
        int moveLength = integer;
        if (isValidMove(figureRow + moveLength, figureCol)) {
            figureRow += moveLength;
        } else if (isValidMove(figureRow - moveLength, figureCol)) {
            figureRow -= moveLength;
        } else if (isValidMove(figureRow, figureCol + moveLength)) {
            figureCol += moveLength;
        } else if (isValidMove(figureRow, figureCol - moveLength)) {
            figureCol -= moveLength;
        }
        // Toggle move length
        nextMoveIsTwo = !nextMoveIsTwo;
    }


    @Override
    public Set getLegalMoves() {
        Set<Integer> legalMoves = new HashSet<>();
        int moveLength = nextMoveIsTwo ? 2 : 3;
        if (isLegalMove(moveLength)) {
            legalMoves.add(moveLength);
        }
        return legalMoves;
    }

    @Override
    public State clone() {
        return new GameState(board, figureRow, figureCol, nextMoveIsTwo);
    }
    private boolean isValidMove(int row, int col) {
        return row >= 0 && row < board.length && col >= 0 && col < board[0].length && board[row][col] != -1;
    }
}
