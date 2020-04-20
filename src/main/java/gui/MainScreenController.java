package gui;

import game.ScoreCalculator;
import grid.bubble.Bubble;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

/**
 * This class contains the controllers of the main page screen,
 * All handlers are set here and linked to the fxml file.
 *
 * @author Team SEM-13.
 */
public class MainScreenController {
    @FXML
    private transient Bubble tutorial;
    @FXML
    private transient Button profilePage;
    @FXML
    private transient Button exit;
    @FXML
    private transient Button play;

    /**
     * Handler when clicked on exit button.
     *
     * @param event information about the button that called the method.
     */
    @FXML
    protected void handleExitButtonAction(ActionEvent event) {
        ((Stage) exit.getScene().getWindow()).close();
    }

    /**
     * Handler when clicked on profile page button.
     *
     * @param event information about the button that called the method.
     */
    @FXML
    protected void handleProfileButtonAction(ActionEvent event) {
        SceneLoader.loadScene("/simpleProfile.fxml");
    }

    /**
     * Handler when clicked on tutorial button.
     *
     * @param event information about the button that called the method.
     */
    @FXML
    protected void handleTutorialButtonAction(ActionEvent event) {
        SceneLoader.loadScene("/tutorialScreen.fxml");
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

    /**
     * Handler when clicked on play button.
     *
     * @param event information about the button that called the method.
     */
    @FXML
    protected void handlePlayButtonAction(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Level difficulty");
        alert.setHeaderText("What difficulty level do you want to play?");
        alert.setContentText("Choose your option.");

        ButtonType easy = new ButtonType("Easy");
        ButtonType normal = new ButtonType("Normal");
        ButtonType hard = new ButtonType("Hard");
        ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(easy, normal, hard, cancel);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == easy) {
            SceneLoader.loadSceneGame("/gameScreen.fxml", 0, new ScoreCalculator(Main.player, 0));
        } else if (result.isPresent() && result.get() == normal) {
            SceneLoader.loadSceneGame("/gameScreen.fxml", 3, new ScoreCalculator(Main.player, 0));
        } else if (result.isPresent() && result.get() == hard) {
            SceneLoader.loadSceneGame("/gameScreen.fxml", 6, new ScoreCalculator(Main.player, 0));
        } else {
            alert.close();
        }
    }
    /*
    We will be adding the text here when getting to this screen.
    Get the data from database and then writ it to the text flow.

    // add text to textflow
    Text text_1 = nickName of the player;
    Text text_2 = name of the player;
    Text text_3 = score of the player;

    text_flow.getChildren().add(text_1);
    text_flow.getChildren().add(text_2);

     // set line spacing
     text_flow.setLineSpacing(20.0f);

    // set alignment of vbox
    vbox.setAlignment(Pos.CENTER);

    */
}
