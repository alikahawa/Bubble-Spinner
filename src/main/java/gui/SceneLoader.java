package gui;

import game.ScoreCalculator;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

/**
 * Modern Scene Loader utility for Bubble-Spinner Game.
 * 
 * <p>This class handles loading and transitioning between different game screens
 * with modern styling and proper error handling.</p>
 * 
 * <p>Features:</p>
 * <ul>
 *   <li>Automatic CSS theme application</li>
 *   <li>Improved error handling and logging</li>
 *   <li>Support for multiple FXML layouts</li>
 *   <li>Professional scene transitions</li>
 * </ul>
 *
 * @author Team SEM-13 (Original), Ali Kahawati (Modernization - 2025)
 * @version 2.0
 * @since 1.0
 */
public class SceneLoader {
    
    private static final Logger LOGGER = Logger.getLogger(SceneLoader.class.getName());
    
    /**
     * Path to the modern CSS theme file.
     */
    private static final String MODERN_THEME_CSS = "/styles/modern-theme.css";
    
    /**
     * Load the scene based on the location with modern styling applied.
     *
     * @param location String which represents the location of the fxml file
     */
    public static void loadScene(String location) {
        try {
            LOGGER.info("Loading scene: " + location);
            
            FXMLLoader loader = new FXMLLoader(SceneLoader.class.getResource(location));
            Parent root = loader.load();
            
            Scene scene = new Scene(root);
            
            // Apply modern theme styling
            applyModernTheme(scene);
            
            // Set the scene and display
            Main.stage.setScene(scene);
            
            // Ensure stage is visible
            if (!Main.stage.isShowing()) {
                Main.stage.show();
            }
            
            LOGGER.info("Scene loaded successfully: " + location);
            
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Could not load scene: " + location, e);
            handleSceneLoadError(location, e);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Unexpected error loading scene: " + location, e);
            handleSceneLoadError(location, e);
        }
    }

    /**
     * Load the scene based on the location for game-specific screens.
     *
     * @param location        String which represents the location of the fxml file
     * @param level          Int that represents the current game level
     * @param scoreCalculator The scoreCalculator instance for the game
     */
    public static void loadSceneGame(String location, int level, 
            ScoreCalculator scoreCalculator) {
        try {
            LOGGER.info("Loading game scene: " + location + " (Level: " + level + ")");
            
            FXMLLoader loader = new FXMLLoader(SceneLoader.class.getResource(location));
            Parent root = loader.load();

            // Configure controller based on the loaded scene type
            configureGameController(loader, level, scoreCalculator);
            
            Scene scene = new Scene(root);
            
            // Apply modern theme styling
            applyModernTheme(scene);
            
            // Set the scene and display
            Main.stage.setScene(scene);
            
            // Ensure stage is visible
            if (!Main.stage.isShowing()) {
                Main.stage.show();
            }
            
            LOGGER.info("Game scene loaded successfully: " + location);
            
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Could not load game scene: " + location, e);
            handleSceneLoadError(location, e);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Unexpected error loading game scene: " + location, e);
            handleSceneLoadError(location, e);
        }
    }
    
    /**
     * Applies the modern theme CSS to the given scene.
     * 
     * @param scene The scene to apply styling to
     */
    private static void applyModernTheme(Scene scene) {
        try {
            String cssPath = SceneLoader.class.getResource(MODERN_THEME_CSS).toExternalForm();
            scene.getStylesheets().add(cssPath);
            LOGGER.fine("Applied modern theme CSS successfully");
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Could not load modern theme CSS", e);
            // Continue without CSS - application should still function
        }
    }
    
    /**
     * Configure GameController with level and score calculator.
     * 
     * @param loader          The FXMLLoader that loaded the scene
     * @param level          The game level
     * @param scoreCalculator The score calculator instance
     */
    private static void configureGameController(FXMLLoader loader, int level, 
            ScoreCalculator scoreCalculator) {
        Object controller = loader.getController();
        
        if (controller instanceof GameController) {
            GameController gameController = (GameController) controller;
            gameController.setLevelScoreCalculatorAndInitialize(level, 
                    scoreCalculator);
            LOGGER.info("Configured GameController for level " + level);
            
        } else if (controller instanceof WinController) {
            WinController winController = (WinController) controller;
            winController.setLevelAndCalculator(level, scoreCalculator);
            LOGGER.info("Configured WinController");
            
        } else if (controller instanceof LoseGameController) {
            LoseGameController loseController = (LoseGameController) controller;
            loseController.setScoreboardController(scoreCalculator);
            LOGGER.info("Configured LoseGameController");
            
        } else if (controller instanceof WinGameController) {
            WinGameController winGameController = (WinGameController) controller;
            winGameController.setScoreCalculator(level, scoreCalculator);
            LOGGER.info("Configured WinGameController");
        }
    }
    
    /**
     * Handles errors that occur during scene loading.
     * 
     * @param location The location that failed to load
     * @param error    The error that occurred
     */
    private static void handleSceneLoadError(String location, Exception error) {
        // For now, we'll try to fallback to a default scene or show an error
        LOGGER.severe("Failed to load scene: " + location + " - " + error.getMessage());
        
        // In a production app, you might want to show an error dialog
        // or attempt to load a fallback scene
        try {
            // Try to load a basic error scene or fallback
            if (!location.equals("/startScreen.fxml")) {
                LOGGER.info("Attempting to load fallback start screen");
                loadScene("/startScreen.fxml");
            }
        } catch (Exception fallbackError) {
            LOGGER.log(Level.SEVERE, "Even fallback scene failed to load", fallbackError);
            // At this point, the application is in a critical state
            System.exit(1);
        }
    }
}
