package gui;

import auth.DatabaseConnection;
import auth.Player;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;


/**
 * This class contains the controllers of the start screen,
 * All handlers are set here and linked to the fxml file.
 *
 * @author Team SEM-13.
 */
public class StartController {
    @FXML
    private transient PasswordField password;
    @FXML
    private transient TextField username;
    @FXML
    private transient Button logIn;
    @FXML
    private transient Button exit;

    /**
     *  Method that handles input when login button is pressed.
     * @param event information about the button that called this method.
     */
    @FXML
    @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
    protected void handleLoginButtonAction(Event event) {
        if (event.getEventType().toString().equals("MOUSE_CLICKED"))  {
            return;
        }
        if (username.getText().isEmpty()) {
            Window window = logIn.getScene().getWindow();
            alertPopUp(Alert.AlertType.ERROR, window, "Form Error!",
                    "Please enter your username");
            return;
        }
        if (password.getText().isEmpty()) {
            Window window = logIn.getScene().getWindow();
            alertPopUp(Alert.AlertType.ERROR, window, "Form Error!",
                    "Please enter a password");
            return;
        }
        if (DatabaseConnection.authentication(username.getText(),password.getText())) {
            System.out.println("SUCCESS");

            Player player = new Player(username.getText(),password.getText(),username.getText());
            Main.player = player;
            SceneLoader.loadScene("/mainScreen.fxml");
            return;
        } else {
            Window window = logIn.getScene().getWindow();
            alertPopUp(Alert.AlertType.ERROR, window, "Form Error!",
                    "Wrong Credentials");
            return;
        }




    }


    /**
     * Handler when clicked on exit button.
     *
     * @param event information about the button that called the method.
     */
    @FXML
    protected void handleExitButtonAction(ActionEvent event) {
        ((Stage)exit.getScene().getWindow()).close();
    }

    /**
     * Alert When not inserting information i.e. username or password.
     *
     * @param type the type of alert to show.
     * @param window what window to show the alert in.
     * @param name the name of the alert.
     * @param message the message shown by the alert.
     */
    public static void alertPopUp(Alert.AlertType type, Window window, String name,
                                  String message) {
        Alert alert = new Alert(type);
        alert.setTitle(name);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(window);
        alert.show();
    }
}
