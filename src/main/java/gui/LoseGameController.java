package gui;

import game.ScoreCalculator;
import grid.Grid;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoseGameController {

    @FXML
    private transient Text score;

    private transient ScoreCalculator calculator = new ScoreCalculator(Main.player, 0);


    @FXML
    public void initialize() {
        score.setText("Your score: " + calculator.getScore());
    }

    /**
     * Handler when clicked on menu button.
     *
     * @param event information about the button that called the method.
     */
    @FXML
    protected void handleMenuButtonAction(ActionEvent event) {
        SceneLoader.loadScene("/mainScreen.fxml");
    }

    /**
     * Handler when clicked on scoreboard button.
     *
     * @param event information about the button that called the method.
     */
    @FXML
    protected void handleScoreboardButtonAction(ActionEvent event) {
        SceneLoader.loadScene("/scoreboardScreen.fxml");
    }

    public void setScoreboardController(ScoreCalculator calculator) {
        this.calculator = calculator;
        initialize();
    }

}
