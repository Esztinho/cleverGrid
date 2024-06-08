package solver;
import puzzle.solver.BreadthFirstSearch;
import model.Direction;
import model.GameState;

public class Main {

    public static void main(String[] args) {

        var bfs = new BreadthFirstSearch<Direction>();
        bfs.solveAndPrintSolution(new GameState());
    }


}