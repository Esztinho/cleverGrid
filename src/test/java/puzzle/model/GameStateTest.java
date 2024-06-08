package puzzle.model;

import model.GameState;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameStateTest {
    GameState state0 = new GameState();

    int[][] initialBoard1 = { {0, 0, 0}, {0, 1, 0}, {0, 0, 0} };
    GameState state1 = new GameState(initialBoard1, 0, 0, true);

    int[][] initialBoard2 = { {0, 0, -1}, {-1, 0, 0}, {1, 1, 0} };
    GameState state2 = new GameState(initialBoard2, 1, 1, false);

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

}
