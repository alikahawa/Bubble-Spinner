package gui;

import game.ScoreCalculator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class WinController {
    @FXML
    private transient Button next;
    @FXML
    private transient Button menu;
    @FXML
    private transient Text score;

    private transient int level;
    private transient ScoreCalculator scoreCalculator = new ScoreCalculator(Main.player, 0);

    @FXML
    public void initialize() {
        score.setText("Your score: " + scoreCalculator.getScore());
    }

    /**
     * Handler when clicked on next level button.
     *
     * @param event information about the button that called the method.
     */
    @FXML
    protected void handleNextButtonAction(ActionEvent event) {
        SceneLoader.loadSceneGame("/gameScreen.fxml", level, this.scoreCalculator);
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

    protected void setLevelAndCalculator(int level,ScoreCalculator scoreCalculator) {
        this.level = level + 1;
        this.scoreCalculator = scoreCalculator;
        initialize();
    }
}
