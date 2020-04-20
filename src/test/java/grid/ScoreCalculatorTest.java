package grid;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import auth.Player;
import game.ScoreCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


/**
 * This class is a test class for the functionality of scoreCalculator.
 *
 * @author Team SEM-13
 */
public class ScoreCalculatorTest {

    private static Player player;
    private static Player player1;
    private static ScoreCalculator scoreCalculator;

    @BeforeEach
    void setupTestEnvironment() {
        player = new Player("blackmirror", "1234567", "bmirror");
        player1 = new Player("br", "1232337", "br");
        scoreCalculator = new ScoreCalculator(player, 0);
    }

    @Test
    void testConstructor() {

        assertNotNull(scoreCalculator);
    }

    @Test
    void testGetPlayer() {
        assertEquals(player, scoreCalculator.getPlayer());
    }

    @Test
    void testGetScore() {
        assertEquals(0, scoreCalculator.getScore());
    }

    @Test
    void testSetPlayer() {
        scoreCalculator.setPlayer(player1);
        assertEquals(player1, scoreCalculator.getPlayer());
    }

    @Test
    void testSetPlayer1() {
        scoreCalculator.setPlayer(player1);
        assertNotEquals(player, scoreCalculator.getPlayer());
    }

    @Test
    void testGetScore1() {
        scoreCalculator.updateScore(3);
        assertEquals(9, scoreCalculator.getScore());
    }

    @Test
    void testGetScore2() {
        scoreCalculator.updateScore(10);
        assertEquals(40, scoreCalculator.getScore());
    }

}
