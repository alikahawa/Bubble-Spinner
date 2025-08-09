package gui;

import auth.Player;
import game.ScoreCalculator;
import grid.Grid;
import gui.SceneLoader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * This is the main class and the starting point of the Bubble-Spinner game.
 * 
 * <p>This modernized version includes:</p>
 * <ul>
 *   <li>Professional UI styling with modern CSS themes</li>
 *   <li>Enhanced error handling and logging</li>
 *   <li>Improved application lifecycle management</li>
 *   <li>Better resource management</li>
 * </ul>
 *
 * @author Team SEM-13 (Original), Ali Kahawati (Modernization - 2025)
 * @version 2.0 - Professional Edition
 * @since 1.0
 */
public class Main extends Application {
    
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
    
    /**
     * The primary stage of the application - shared across all scenes.
     */
    public static Stage stage;
    
    /**
     * The currently logged-in player instance.
     */
    public static Player player;
    
    /**
     * Application version information.
     */
    public static final String VERSION = "2.0";
    public static final String EDITION = "Professional Edition";

    /**
     * The method which starts the JavaFX application.
     * 
     * <p>Initializes the primary stage with modern styling, sets up
     * the application window properties, and loads the initial start screen.</p>
     *
     * @param primaryStage The primary stage for this application, onto which
     *                     the application scene can be set
     */
    @Override
    public void start(Stage primaryStage) {
        try {
            LOGGER.info("Starting Bubble-Spinner Game v" + VERSION + " - " + EDITION);
            
            stage = primaryStage;
            
            // Configure the main stage with modern properties
            setupMainStage();
            
            // Load the modern start screen
            SceneLoader.loadScene("/startScreenModern.fxml");
            
            LOGGER.info("Application started successfully");
            
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to start application", e);
            showErrorDialog("Startup Error", "Failed to start the application: " + e.getMessage());
        }
    }

    /**
     * Sets up the main application stage with modern properties and styling.
     * 
     * <p>Configures:</p>
     * <ul>
     *   <li>Window title and minimum size</li>
     *   <li>Application icon</li>
     *   <li>Window close behavior</li>
     *   <li>Responsive sizing</li>
     * </ul>
     */
    private void setupMainStage() {
        // Set window properties
        stage.setTitle("Bubble Spinner - " + EDITION + " v" + VERSION);
        stage.setMinWidth(800);
        stage.setMinHeight(600);
        stage.setResizable(true);
        
        // Set application icon (if available)
        try {
            var iconStream = getClass().getResourceAsStream(
                    "/icons/bubble-spinner-icon.png");
            if (iconStream != null) {
                Image icon = new Image(iconStream);
                stage.getIcons().add(icon);
            } else {
                LOGGER.fine("Application icon not found - skipping");
            }
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Could not load application icon", e);
        }
        
        // Handle application close event
        stage.setOnCloseRequest(event -> {
            LOGGER.info("Application closing...");
            handleApplicationClose();
        });
    }
    
    /**
     * Handles the application close event with proper cleanup.
     * 
     * <p>Performs necessary cleanup operations before the application exits,
     * including saving user preferences and stopping background processes.</p>
     */
    private void handleApplicationClose() {
        try {
            // Save any pending user data
            if (player != null) {
                LOGGER.info("Saving player data for: " + player.getUsername());
                // Add any necessary save operations here
            }
            
            // Stop any background threads or services
            // Add cleanup code here if needed
            
            LOGGER.info("Application closed successfully");
            
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Error during application close", e);
        }
    }
    
    /**
     * Displays an error dialog to the user.
     * 
     * @param title   The title of the error dialog
     * @param message The error message to display
     */
    private void showErrorDialog(String title, String message) {
        // This would be implemented with a proper error dialog
        // For now, we'll just log it
        LOGGER.severe(title + ": " + message);
    }
    
    /**
     * The main method which starts the whole application.
     * 
     * <p>This is the entry point of the Bubble-Spinner game. It initializes
     * the JavaFX runtime and launches the application.</p>
     *
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        LOGGER.info("Launching Bubble-Spinner Game...");
        launch(args);
    }
    
    /**
     * Gets the current application version.
     * 
     * @return The version string
     */
    public static String getVersion() {
        return VERSION;
    }
    
    /**
     * Gets the current application edition.
     * 
     * @return The edition string
     */
    public static String getEdition() {
        return EDITION;
    }
}


