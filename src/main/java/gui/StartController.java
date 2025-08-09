package gui;
import gui.SceneLoader;

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
    private transient PasswordField passwordField;
    @FXML
    private transient TextField usernameField;
    @FXML
    private transient Button loginButton;
    @FXML
    private transient Button exitButton;

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
        if (usernameField.getText().isEmpty()) {
            Window window = loginButton.getScene().getWindow();
            alertPopUp(Alert.AlertType.ERROR, window, "Form Error!",
                    "Please enter your username");
            return;
        }
        if (passwordField.getText().isEmpty()) {
            Window window = loginButton.getScene().getWindow();
            alertPopUp(Alert.AlertType.ERROR, window, "Form Error!",
                    "Please enter a password");
            return;
        }
        
        try {
            if (DatabaseConnection.authentication(usernameField.getText(), passwordField.getText())) {
                System.out.println("SUCCESS");

                Player player = new Player(usernameField.getText(), passwordField.getText(), usernameField.getText());
                Main.player = player;
                SceneLoader.loadScene("/mainScreenModern.fxml");
                return;
            } else {
                Window window = loginButton.getScene().getWindow();
                alertPopUp(Alert.AlertType.ERROR, window, "Login Error!",
                        "Invalid username or password. Please check your credentials and try again.");
                return;
            }
        } catch (Exception e) {
            // Database connection failed - offer fallback options
            Window window = loginButton.getScene().getWindow();
            alertPopUp(Alert.AlertType.WARNING, window, "Connection Error!",
                    "Cannot connect to database. Please check your internet connection or play as guest.");
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
        ((Stage)exitButton.getScene().getWindow()).close();
    }

    /**
     * Handler for creating a new account.
     *
     * @param event information about the button that called the method.
     */
    @FXML
    protected void handleCreateAccountAction(ActionEvent event) {
        SceneLoader.loadScene("/registrationScreen.fxml");
    }

    /**
     * Handler for guest login.
     *
     * @param event information about the button that called the method.
     */
    @FXML
    protected void handleGuestLoginAction(ActionEvent event) {
        // Create a guest player
        Player guestPlayer = new Player("Guest", "", "Guest");
        Main.player = guestPlayer;
        SceneLoader.loadScene("/mainScreenModern.fxml");
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
