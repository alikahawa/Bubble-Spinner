package auth;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import grid.Coordinate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


/**
 * This class is a test class for the player class and its properties.
 *
 * @author Team 13
 */
public class PlayerTest {
    private transient Player player1;
    private transient Player player2;
    private transient Player player3;
    private transient String blackmirror;
    private transient String password;
    private transient String nickname;

    @BeforeEach
    void setupTestEnvironment() {
        password = "1234567";
        blackmirror = "blackmirror";
        nickname = "bmirror";
        player1 = new Player(blackmirror, password, nickname);
        player2 = new Player("thor", "7654321", "Thor");
        player3 = new Player("loki", "7891011", "Loki");



    }

    @Test
    void testConstructor() {
        assertNotNull(player1);
    }

    @Test
    void testGetUsername() {
        assertEquals(player1.getUsername(), blackmirror);
    }

    @Test
    void testGetPassword() {
        assertEquals(player1.getPassword(), password);
    }

    @Test
    void testGetNickName() {
        assertEquals(player1.getNickname(), nickname);
    }

    @Test
    void testSetUsername() {
        player1.setUsername("mirror");
        assertEquals(player1.getUsername(), "mirror");
    }

    @Test
    void testSetNickname() {
        player1.setNickname("mi");
        assertEquals(player1.getNickname(), "mi");
    }

    @Test
    void testSetPassword() {
        player1.setPassword("123456");
        assertEquals(player1.getPassword(), "123456");
    }

    @Test
    void testEqualsNegative() {
        assertFalse(player1.equals(player3));
    }

    @Test
    void testEqualsSelfPositive() {
        assertTrue(player1.equals(player1));
    }

    @Test
    void testEqualsNegative1() {
        assertFalse(player1.equals(player2));
    }

    @Test
    void testEqualsSelfPositive1() {
        assertTrue(player2.equals(player2));
    }

    @Test
    void testEqualsNull() {
        assertNotEquals(player1, null);
    }

    @Test
    void testEqualsDifferentClass() {
        assertNotEquals(player1, new Coordinate(1, 2) {
        });
    }

    @Test
    void testDifferentPassword() {
        assertNotEquals(player1, new Player(blackmirror, "123456", nickname));
    }

    @Test
    void testDifferentNickname() {
        assertNotEquals(player1, new Player(blackmirror, password, "mirror"));
    }

    @Test
    void testHashSame() {
        int hash = player1.hashCode();
        assertEquals(hash, player1.hashCode());
    }

    @Test
    void testHashNotSame() {
        assertNotEquals(player1.hashCode(), player2.hashCode());
    }

    @Test
    void testToString() {
        String string = "Player username: " + blackmirror + "\n"
                + "Player password: " + password
                + "\n" + "Player nickname: " + nickname;

        assertEquals(string, player1.toString());
    }

    @Test
    void testEqualsSameValues() {
        Player other = new Player(blackmirror, password, nickname);
        Player same = new Player(blackmirror, password, nickname);
        assertEquals(same,other);
    }
}
