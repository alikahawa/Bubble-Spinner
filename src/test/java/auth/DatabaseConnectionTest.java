package auth;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class DatabaseConnectionTest {

    @Test
    void testIncorrectName() {
        assertFalse(DatabaseConnection.authentication("wrongname","123456"));
    }

    @Test
    void testIncorrectpassword() {
        assertFalse(DatabaseConnection.authentication("testname","1234"));
    }

    @Test
    void testCorrectInfo() {
        assertTrue(DatabaseConnection.authentication("testname","123456"));
    }

    @Test
    void testScoreBoardReturnsAnything() {
        assertDoesNotThrow(DatabaseConnection::retrieveScoreBoard);
    }

    @Test
    void testScoreCanBeWritten() {
        assertDoesNotThrow(() -> DatabaseConnection.writeScore("bob",0));
    }



}
