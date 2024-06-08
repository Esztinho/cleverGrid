package game;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import model.Direction;
import model.GameState;
import org.tinylog.Logger;

public class GameController {
    @FXML
    private GridPane grid;

    private GameState gameState;


    private void moveFigure(Direction direction) {
        Logger.info("moveFigure");
        if (gameState.isLegalMove(direction)) {
            gameState.makeMove(direction);
            
            if (gameState.isSolved()) {
                Logger.info("Puzzle solved!");
            }
        } else {
            Logger.warn("Invalid move:({}, {})", direction.getRowChange(), direction.getColChange());
        }
    }





}