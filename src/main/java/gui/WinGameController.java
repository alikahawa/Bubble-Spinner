package gui;
import gui.SceneLoader;

import game.ScoreCalculator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class WinGameController {
    @FXML
    private transient Text score;


    private transient ScoreCalculator scoreCalculator;

    @FXML
    public void initialize() {
        // Initialize scoreCalculator when needed
        if (scoreCalculator == null && Main.player != null) {
            scoreCalculator = new ScoreCalculator(Main.player, 0);
        }
        if (scoreCalculator != null) {
            score.setText("Your score: " + scoreCalculator.getScore());
        }
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

    public void setScoreCalculator(int level, ScoreCalculator scoreCalculator) {
        this.scoreCalculator = scoreCalculator;
        initialize();
    }

}
