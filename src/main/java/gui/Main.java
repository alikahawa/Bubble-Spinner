package gui;

import auth.Player;
import game.ScoreCalculator;
import grid.Grid;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


/**
 * This is the main class and the starting point of the game.
 *
 * @author Team SEM-13.
 */
public class Main extends Application {

    public static Stage stage;
    public static Player player;

    /**
     * The method which start the game.
     *
     * @param primaryStage is the stage which should start.
     */
    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        SceneLoader.loadScene("/startScreen.fxml");
    }

    /**
     * The main method which starts the whole game.
     *
     * @param args are the arguments of the main method.
     */
    public static void main(String[] args) {
        launch(args);
    }
}


