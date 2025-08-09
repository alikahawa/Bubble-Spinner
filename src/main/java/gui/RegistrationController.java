package gui;

import auth.DatabaseConnection;
import auth.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 * Controller for the user registration screen.
 * Handles user account creation with validation and database integration.
 *
 * @author Ali Kahawati (Enhancement Update)
 * @version 2.0
 */
public class RegistrationController {
    
    @FXML
    private TextField usernameField;
    
    @FXML
    private TextField nicknameField;
    
    @FXML
    private PasswordField passwordField;
    
    @FXML
    private PasswordField confirmPasswordField;
    
    @FXML
    private Button registerButton;
    
    @FXML
    private Button closeButton;
    
    /**
     * Handles the registration button action.
     * Validates input fields and attempts to create a new user account.
     *
     * @param event The action event triggered by clicking the register button.
     */
    @FXML
    protected void handleRegisterButtonAction(ActionEvent event) {
        String username = usernameField.getText().trim();
        String nickname = nicknameField.getText().trim();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        
        // Validate input fields
        if (username.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Form Error!", 
                     "Please enter a username");
            return;
        }
        
        if (username.length() < 3) {
            showAlert(Alert.AlertType.ERROR, "Form Error!", 
                     "Username must be at least 3 characters long");
            return;
        }
        
        if (nickname.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Form Error!", 
                     "Please enter a display name");
            return;
        }
        
        if (password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Form Error!", 
                     "Please enter a password");
            return;
        }
        
        if (password.length() < 4) {
            showAlert(Alert.AlertType.ERROR, "Form Error!", 
                     "Password must be at least 4 characters long");
            return;
        }
        
        if (!password.equals(confirmPassword)) {
            showAlert(Alert.AlertType.ERROR, "Form Error!", 
                     "Passwords do not match");
            return;
        }
        
        // Attempt to register the user
        try {
            boolean success = DatabaseConnection.registerUser(username, password, nickname);
            
            if (success) {
                showAlert(Alert.AlertType.INFORMATION, "Success!", 
                         "Account created successfully! You can now login with your credentials.");
                
                // Go back to login screen
                handleBackToLoginAction(event);
                
            } else {
                showAlert(Alert.AlertType.ERROR, "Registration Failed!", 
                         "Username already exists or database error occurred. Please try a different username.");
            }
            
        } catch (Exception e) {
            // Fallback: Create a local account (for development/offline use)
            showAlert(Alert.AlertType.WARNING, "Offline Mode", 
                     "Database not available. Creating account for this session only.");
            
            // Create a temporary player for this session
            Player player = new Player(username, password, nickname);
            Main.player = player;
            SceneLoader.loadScene("/mainScreenModern.fxml");
        }
    }
    
    /**
     * Handles the back to login button action.
     * Returns to the login/start screen.
     *
     * @param event The action event triggered by clicking the back button.
     */
    @FXML
    protected void handleBackToLoginAction(ActionEvent event) {
        SceneLoader.loadScene("/startScreenModern.fxml");
    }
    
    /**
     * Handles the close button action.
     * Closes the application window.
     *
     * @param event The action event triggered by clicking the close button.
     */
    @FXML
    protected void handleCloseButtonAction(ActionEvent event) {
        ((Stage) closeButton.getScene().getWindow()).close();
    }
    
    /**
     * Shows an alert dialog with the specified type, title, and message.
     *
     * @param type    The type of alert to show.
     * @param title   The title of the alert dialog.
     * @param message The message to display in the alert.
     */
    private void showAlert(Alert.AlertType type, String title, String message) {
        Window window = registerButton.getScene().getWindow();
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(window);
        alert.showAndWait();
    }
}
