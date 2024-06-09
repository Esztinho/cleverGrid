package game;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import model.Direction;
import model.GameState;
import org.tinylog.Logger;

public class GameController {
    @FXML
    private GridPane grid;

    private GameState gameState;

    private Boolean gameFinished;

    @FXML
    private void initialize() {
        // Késleltetett eseménykezelő beállítása
        Platform.runLater(() -> grid.getScene().setOnKeyPressed(this::handleKeyPress));

        restartGame();
    }

    private void restartGame() {
        gameState = new GameState();
        int c=(gameState.getFigureCol());
        int r=(gameState.getFigureRow());
        clearAndPopulateGrid();
        placeFigureAt(c, r);
        gameFinished=false;
    }

    private void placeFigureAt(int row, int col) {
        var node = grid.getChildren().get(row * 8 + col);
        if (node instanceof StackPane) {
            StackPane square = (StackPane) node;
            square.getChildren().clear();
            if (gameState.getFigureRow() == row && gameState.getFigureCol() == col) {
                var imageView = new ImageView(new Image("puzzlegame/npc.png"));
                imageView.setFitWidth(57.6);
                imageView.setFitHeight(57.6);
                imageView.setPreserveRatio(true);
                square.getChildren().add(imageView);}
        }
    }

    @FXML
    private void handleKeyPress(KeyEvent keyEvent) {
        if (gameFinished) {
            return; // Do nothing if the game is finished
        }
        switch (keyEvent.getCode()) {
            case UP:
                moveFigure(Direction.UP);
                break;
            case RIGHT:
                moveFigure(Direction.RIGHT);
                break;
            case DOWN:
                moveFigure(Direction.DOWN);
                break;
            case LEFT:
                moveFigure(Direction.LEFT);
                break;
            default:
                break;
        }
        if (gameState.isSolved()) {
            gameFinished=true;
        }
    }


    private void moveFigure(Direction direction) {
        Logger.info("moveFigure");
        if (gameState.isLegalMove(direction)) {
            gameState.makeMove(direction);

            updateBoard();
            if (gameState.isSolved()) {
                Logger.info("Puzzle solved!");
                showSolvedAlert();
            }
        } else {
            Logger.warn("Invalid move:({}, {})", direction.getRowChange(), direction.getColChange());
        }
    }

    private void showSolvedAlert() {
        var alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Game Over");
        alert.setContentText("Congratulations, you have solved the puzzle!");
        alert.showAndWait();
        restartGame();
    }

    private void clearAndPopulateGrid() {
        grid.getChildren().clear();
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                var square = createSquare(row, col);
                grid.add(square, col, row);
            }
        }
        updateBoard();
    }


    private StackPane createSquare(int row, int col) {
        var square = new StackPane();
        square.getStyleClass().add("square");
        return square;
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
                        square.getStyleClass().add("black");
                    } else if (gameState.getBoard()[row][col] == 1) {
                        square.getStyleClass().add("spec");
                    } else {
                        square.setStyle("");
                    }
                }
            }
        }
    }





}