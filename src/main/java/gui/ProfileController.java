package gui;

import auth.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

/**
 * This class contains the controllers of the profile screen,
 * All handlers are set here and linked to the fxml file.
 *
 * @author Team SEM-13.
 */
public class ProfileController {

    @FXML
    private transient Text username;

    @FXML
    private transient Text nickname;

    @FXML
    private transient Text score;

    @FXML
    private transient Button exit;


    /**
     * Method that gets called as the ProfileScreen is initialized.
     */
    @FXML
    public void initialize() {
        username.setText("Username: " + Main.player.getUsername());
        nickname.setText("Nickname: " + Main.player.getNickname());
        score.setText("Top Score: "
                + DatabaseConnection.retrieveMaxScoreOfPlayer(Main.player.getUsername()));
    }


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
     * Handler when clicked on back button.
     *
     * @param event information about the button that called the method.
     */
    @FXML
    protected void handleBackButtonAction(ActionEvent event) {
        SceneLoader.loadScene("/mainScreen.fxml");
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
