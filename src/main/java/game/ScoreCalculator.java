package game;

import auth.Player;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * This class will calculate the score instantly after shooting.
 * Furthermore more it will provide the last score after finishing the game.
 *
 * @author Team SEM-13
 */
public class ScoreCalculator {
    private Player player;
    private int score;

    /**
     * A scoreSystem constructor.
     *
     * @param player the current player.
     * @param score  the score of the current player.
     */
    public ScoreCalculator(Player player, int score) {
        this.player = player;
        this.score = 0;
    }

    /**
     * Set a new player to this score.
     *
     * @param player to be set as the new owner of this score.
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Set new score to the current player.
     *
     * @param score the new score to be set.
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Get the owner of this score.
     *
     * @return the player who owns this score.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Get the current score of the current player.
     *
     * @return the score of this player at this moment.
     */
    public int getScore() {
        return score;
    }

    /**
     * This method will update the score of the current player.
     * The method should be called after a collision has been detected.
     * If no collision happens, the score shouldn't change.
     *
     * @param numberOfBubbles is the number of bubbles to be removed from the grid.
     */
    public void updateScore(int numberOfBubbles) {
        int bonus = 3;
        this.score = this.score + bonus * numberOfBubbles;
        if (numberOfBubbles > bonus) {
            this.score = this.score + numberOfBubbles;
        }
    }


    /**
     * Return the score as string.
     *
     * @return the score of the given player with its name.
     */
    @Override
    public String toString() {
        return "The score of " + player
                + "is " + score;
    }
}
