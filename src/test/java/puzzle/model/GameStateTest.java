package puzzle.model;

import model.Direction;
import model.GameState;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameStateTest {
    GameState state0 = new GameState();

    int[][] initialBoard1 = {
            {0, 0, 0, 0, 1, 0, 0, 0},
            {0, 0, 1, 0, 0, 0, 1, 0},
            {0, 0, -1, 0, 0, 0, 0, -1},
            {0, 0, 1, 0, 1, 0, 0, 0},
            {1, -1, 0, 1, 0, 0, 0, 1},
            {0, 0, 0, 1, 0, -1, 1, 0},
            {0, 0, 1, 0, 0, 0, 0, 1},
            {1, 0, 0, -1, 0, 0, 0, 0}
    };;
    GameState state1 = new GameState(initialBoard1, 7, 7, true);  // a goal state

    int[][] initialBoard2 = {
            {0, 0, 1, -1, 1, 0, 0, 0},
            {0, 0, 1, 0, 0, 0, 1, 0},
            {0, 0, -1, 0, 0, 0, 0, -1},
            {0, 0, 1, 0, 1, 0, 0, 0},
            {1, -1, 0, 1, 0, 0, 0, 1},
            {0, 0, 0, 1, 0, -1, -1, 0},
            {0, 0, 1, 0, 0, 0, 1, 1},
            {1, 0, 0, -1, 0, -1, 0, 0}
    };;
    GameState state2 = new GameState(initialBoard2, 5, 0, false);

    int[][] initialBoard_3 = {
            {0, 0, 1, -1, 1, 0, 0, 0},
            {0, 0, 1, 0, 0, 0, 1, 0},
            {0, 0, -1, 0, 0, 0, 0, -1},
            {0, 0, 1, 0, 1, 0, -1, 0},
            {1, -1, 0, 1, 0, 0, -1, 1},
            {0, 0, 0, 1, 0, -1, -1, 0},
            {0, 0, 1, -1, -1, 0, 0, 1},
            {1, 0, 0, -1, 0, -1, 0, 0}
    };
    GameState state_3 = new GameState(initialBoard_3, 6, 6, false);  //no legal moves


    @Test
    void constructor(){
        int[][] initialBoard3 = { {1, 1, -1}, {0, 0, 0}, {1, 1, 0} };
        GameState state = new GameState(initialBoard3, 0, 0, true);

        assertArrayEquals(initialBoard3, state.getBoard());
        assertEquals(0, state.getFigureRow());
        assertEquals(0, state.getFigureCol());
        assertTrue(state.isNextMoveTwoSteps());

    }

    @Test
    void constructor_shouldThrowIllegalArgumentException() {
        int[][] initialBoard4 = { {1, 1, -1}, {0, 0, 0}, {1, 1, 0} };
        assertThrows(IllegalArgumentException.class, () -> new GameState(initialBoard4, -1, -1, true));

        int[][] initialBoard5 = { {-5, 1, -1}, {0, 0, 0}, {1, 1, 0} };
        assertThrows(IllegalArgumentException.class, () -> new GameState(initialBoard5, -1, 20, false));

    }

    @Test
    void isSolved() {
        assertFalse(state0.isSolved());
        assertTrue(state1.isSolved());
        assertFalse(state2.isSolved());
        assertFalse(state_3.isSolved());
    }

    @Test
    void isLegalMove_state0() {
        assertFalse(state0.isLegalMove(Direction.UP));
        assertTrue(state0.isLegalMove(Direction.RIGHT));
        assertTrue(state0.isLegalMove(Direction.DOWN));
        assertFalse(state0.isLegalMove(Direction.LEFT));
    }

    @Test
    void isLegalMove_state1() {
        assertTrue(state1.isLegalMove(Direction.UP));
        assertFalse(state1.isLegalMove(Direction.RIGHT));
        assertFalse(state1.isLegalMove(Direction.DOWN));
        assertTrue(state1.isLegalMove(Direction.LEFT));
    }

    @Test
    void isLegalMove_state2() {
        assertTrue(state2.isLegalMove(Direction.UP));
        assertTrue(state2.isLegalMove(Direction.RIGHT));
        assertFalse(state2.isLegalMove(Direction.DOWN));
        assertFalse(state2.isLegalMove(Direction.LEFT));
    }

    @Test
    void isLegalMove_state3() {
        assertFalse(state_3.isLegalMove(Direction.UP));
        assertFalse(state_3.isLegalMove(Direction.RIGHT));
        assertFalse(state_3.isLegalMove(Direction.DOWN));
        assertFalse(state_3.isLegalMove(Direction.LEFT));
    }

    @Test
    void makeMove_down_state0() {
        var stateBeforeMove = state0.clone();
        state0.makeMove(Direction.DOWN);
        assertEquals(2, state0.getFigureRow());
        assertEquals(0, state0.getFigureCol());
    }

    @Test
    void makeMove_right_state0() {
        var stateBeforeMove = state0.clone();
        state0.makeMove(Direction.RIGHT);
        assertEquals(0, state0.getFigureRow());
        assertEquals(2, state0.getFigureCol());
    }

    @Test
    void makeMove_left_state1() {
        var stateBeforeMove = state1.clone();
        state1.makeMove(Direction.LEFT);
        assertEquals(7, state1.getFigureRow());
        assertEquals(5, state1.getFigureCol());
    }

    @Test
    void makeMove_up_state1() {
        var stateBeforeMove = state1.clone();
        state1.makeMove(Direction.UP);
        assertEquals(5, state1.getFigureRow());
        assertEquals(7, state1.getFigureCol());
    }


}
