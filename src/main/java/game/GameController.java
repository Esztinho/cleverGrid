package game;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
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

            updateBoard();
            if (gameState.isSolved()) {
                Logger.info("Puzzle solved!");
            }
        } else {
            Logger.warn("Invalid move:({}, {})", direction.getRowChange(), direction.getColChange());
        }
    }

    private void updateBoard() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                var node = grid.getChildren().get(row * 8 + col);
                if (node instanceof StackPane) {
                    StackPane square = (StackPane) node;
                    square.getChildren().clear();
                    if (gameState.getFigureRow() == row && gameState.getFigureCol() == col) {
                        var imageView = new ImageView(new Image("puzzlegame/npc.png"));
                        imageView.setFitWidth(square.getWidth());
                        imageView.setFitHeight(square.getHeight());
                        imageView.setPreserveRatio(true);
                        square.getChildren().add(imageView);
                    } else if (gameState.getBoard()[row][col] == -1) {
                        square.setStyle("-fx-background-color: black;"); // Fekete mezők
                    } else if (gameState.getBoard()[row][col] == 1) {
                        square.setStyle("-fx-background-color: gray;");
                    } else {
                        square.setStyle("");
                    }
                }
            }
        }
    }





}