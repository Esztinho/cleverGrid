package solver;
import puzzle.solver.BreadthFirstSearch;
import puzzle.solver.Node;
import state.GameState;

import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        int[][] initialBoard = {
                {0, 0, 0, 0, 1, 0, 0, 0},
                {0, 0, 1, 0, 0, 0, 1, 0},
                {0, 0, -1, 0, 0, 0, 0, -1},
                {0, 0, 1, 0, 1, 0, 0, 0},
                {1, -1, 0, 1, 0, 0, 0, 1},
                {0, 0, 0, 1, 0, -1, 1, 0},
                {0, 0, 1, 0, 0, 0, 0, 1},
                {1, 0, 0, -1, 0, 0, 0, 0}
        };
        GameState initialState = new GameState(initialBoard, 0, 0, true);
        BreadthFirstSearch<Integer> solver = new BreadthFirstSearch<>();
        Optional<Node<Integer>> solution = solver.solve(initialState);

        if (solution != null) {
            System.out.println("Találtunk megoldást!");
        } else {
            System.out.println("Nincs megoldás!");
        }
    }

}
