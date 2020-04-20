package gui;

import game.ScoreCalculator;
import grid.Grid;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

public class SceneLoader {
    /**
     * Load the scene based on the location.
     *
     * @param location String which represents the location of the fxml.
     */
    public static void loadScene(String location) {
        try {
            FXMLLoader loader = new FXMLLoader(Grid.class.getResource(location));
            AnchorPane root = (AnchorPane) loader.load();
            Scene scene = new Scene(root);
            Main.stage.setScene(scene);
            Main.stage.show();
        } catch (IOException e) {
            System.out.println("Could not load scene" + e.getMessage());
        }
    }

    /**
     * Load the scene based on the location.
     *
     * @param location String which represents the location of the fxml.
     * @param level Int that represents the level.
     * @param scoreCalculator The scoreCalculator.
     */
    public static void loadSceneGame(String location, int level, ScoreCalculator scoreCalculator) {
        try {
            FXMLLoader loader = new FXMLLoader(Grid.class.getResource(location));
            AnchorPane root = (AnchorPane) loader.load();

            if (loader.getController().getClass() == gui.GameController.class) {
                GameController controller = loader.getController();
                controller.setLevelScoreCalculatorAndInitialize(level, scoreCalculator);
            } else if (loader.getController().getClass() == gui.WinController.class) {
                WinController controller = loader.getController();
                controller.setLevelAndCalculator(level, scoreCalculator);
            } else if (loader.getController().getClass() == gui.LoseGameController.class) {
                LoseGameController controller = loader.getController();
                controller.setScoreboardController(scoreCalculator);
            }
            Scene scene = new Scene(root);
            Main.stage.setScene(scene);
            Main.stage.show();
        } catch (IOException e) {
            System.out.println("Could not load scene" + e.getMessage());
        }
    }
}
