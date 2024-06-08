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

}
