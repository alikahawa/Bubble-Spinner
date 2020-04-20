package gui;

import auth.DatabaseConnection;
import game.ScoreEntry;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;


public class ScoreboardController {

    @FXML
    transient TableView score;


    /**
     *  Method that is called when the screen is loaded.
     *  This will populate the table with scores.
     */
    @FXML
    @SuppressWarnings("PMD.CloseResource")
    public void initialize() {
        System.out.println("trying to fill");
        ResultSet scores = DatabaseConnection.retrieveScoreBoard();
        TableColumn nameCol = (TableColumn)score.getColumns().get(0);
        nameCol.setText("name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("userName"));
        TableColumn scoreCol = (TableColumn)score.getColumns().get(1);
        scoreCol.setText("score achieved");
        scoreCol.setCellValueFactory(new PropertyValueFactory<>("score"));

        try {
            while (scores.next()) {
                System.out.println("hi");
                String username = scores.getString(1);
                int scoresInt = scores.getInt(2);
                ScoreEntry entry = new ScoreEntry(username,scoresInt);
                score.getItems().add(entry);
            }
        } catch (SQLException e) {
            System.out.println("ISSUES LOADING SCORES");
        }
        try {
            scores.close();
        } catch (SQLException e) {
            System.out.println("COULD NOT CLOSE RESULTS");
        }

    }

    @FXML public void handleMenuButton() {
        SceneLoader.loadScene("/mainScreen.fxml");
    }


}
