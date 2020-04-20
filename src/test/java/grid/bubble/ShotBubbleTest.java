package grid.bubble;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import grid.Coordinate;

import java.awt.Color;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



public class ShotBubbleTest extends BubbleTest {


    @BeforeEach
    void setupTestEnvironment() {
        bubbleFactory = new ShotBubbleFactory();
        location = new Coordinate(0,0);
    }

    @Test
    void testEmptyConstructor() {
        ShotBubble shotBubble = new ShotBubble();
        assertEquals(Color.GRAY, shotBubble.getColor());
        assertEquals(10, shotBubble.getSize());
        assertEquals(0f, shotBubble.getVelX());
        assertEquals(0f, shotBubble.getVelY());
    }

    @Test
    void testNonEmptyConstructor() {
        ShotBubble bubble = new ShotBubble(Color.BLACK, 12, new Coordinate(8, 3));
        assertEquals(Color.BLACK, bubble.getColor());
        assertEquals(12, bubble.getSize());
        assertEquals(new Coordinate(8, 3), bubble.getLocation());
        assertEquals(0f, bubble.getVelX());
        assertEquals(0f, bubble.getVelY());
    }

    @Test
    void testSetVelX() {
        ShotBubble bubble = new ShotBubble(Color.BLACK, 12, new Coordinate(8, 3));
        assertEquals(0f, bubble.getVelX());
        bubble.setVelX(4);
        assertEquals(4f, bubble.getVelX());
    }

    @Test
    void testSetVelY() {
        ShotBubble bubble = new ShotBubble(Color.BLACK, 12, new Coordinate(8, 3));
        assertEquals(0f,  bubble.getVelY());
        bubble.setVelY(6);
        assertEquals(6f, bubble.getVelY());
    }

    @Test
    void testEquals() {
        ShotBubble bubble1 = new ShotBubble(Color.BLACK, 12,
                new Coordinate(8, 3));
        ShotBubble bubble2 = new ShotBubble(Color.BLACK, 12,
                new Coordinate(8, 3));

        assertEquals(bubble1, bubble2);
    }

    @Test
    void testNotEquals() {
        ShotBubble bubble1 = new ShotBubble(Color.BLACK, 12,
                new Coordinate(8, 3));
        ShotBubble bubble2 = new ShotBubble(Color.BLACK, 12,
                new Coordinate(4, 3));

        assertNotEquals(bubble1, bubble2);
    }

    @Test
    void testHashEquals() {
        ShotBubble bubble = new ShotBubble(Color.BLACK, 12, new Coordinate(8, 3));
        int hash = bubble.hashCode();
        assertEquals(bubble.hashCode(), hash);
    }

    @Test
    void testHashNotEquals() {
        ShotBubble bubble1 = new ShotBubble(Color.BLACK, 12,
                new Coordinate(8, 3));
        ShotBubble bubble2 = new ShotBubble(Color.RED, 12,
                new Coordinate(4, 3));

        assertNotEquals(bubble1.hashCode(), bubble2.hashCode());
    }

    @Test
    void testEqualsNull() {
        ShotBubble shotBubble = new ShotBubble();

        assertNotEquals(null, shotBubble);
    }

    @Test
    void testEqualsDifferentClass() {
        String hello = "hello";

        ShotBubble shotBubble = new ShotBubble();

        assertNotEquals(shotBubble, hello);
    }

    @Test
    void testSetXPos() {
        ShotBubble bubble = new ShotBubble(Color.BLACK, 12, new Coordinate(8, 3));
        assertEquals(8, bubble.getXpos());
        bubble.setXpos(7.3f);
        assertEquals(7.3f, bubble.getXpos());
    }

    @Test
    void testSetYPos() {
        ShotBubble bubble = new ShotBubble(Color.BLACK, 12, new Coordinate(8, 3));
        assertEquals(3, bubble.getYpos());
        bubble.setYpos(7.3f);
        assertEquals(7.3f, bubble.getYpos());
    }

    @Test
    void testBounceRight() {
        ShotBubble bubble = new ShotBubble(Color.BLACK, 2, new Coordinate(5, 5));
        bubble.setVelX(1);
        assertTrue(bubble.move(2,20));
    }

    @Test
    void testBounceLeft() {
        ShotBubble bubble = new ShotBubble(Color.BLACK, 2, new Coordinate(0, 5));
        //bubble.setVelX(-1);
        assertTrue(bubble.move(8,8));
    }

    @Test
    void testBounceTop() {
        ShotBubble bubble = new ShotBubble(Color.BLACK, 2, new Coordinate(5, 5));
        //bubble.setVelX(-1);
        assertTrue(bubble.move(8,5));
    }

    @Test
    void testBounceBottom() {
        ShotBubble bubble = new ShotBubble(Color.BLACK, 2, new Coordinate(5, 0));
        //bubble.setVelX(-1);
        assertTrue(bubble.move(8,5));
    }

    @Test
    void testDontBounce() {
        ShotBubble bubble = new ShotBubble(Color.BLACK, 2, new Coordinate(5, 5));
        //bubble.setVelX(-1);
        assertFalse(bubble.move(10,10));
    }

    @Test
    void testSetSizeFalse() {
        ShotBubble bubble = new ShotBubble(Color.BLACK, 2, new Coordinate(5, 5));
        assertThrows(IllegalArgumentException.class,() -> bubble.setSize(-1));
        assertEquals(2, bubble.getSize());

    }

    @Test
    void testSetSizeTrue() {
        ShotBubble bubble = new ShotBubble(Color.BLACK, 2, new Coordinate(5, 5));
        assertDoesNotThrow(() -> bubble.setSize(1));
        assertEquals(bubble.getSize(),1);

    }

    @Test
    void testNotEqualOther() {
        ShotBubble bubble = new ShotBubble(Color.BLACK, 2, new Coordinate(5, 5));
        ShotBubble other = new ShotBubble(Color.BLACK, 2, new Coordinate(5, 5));
        other.setVelX(1);
        assertNotEquals(bubble,other);

    }


    @Test
    void testNotEqualSize() {
        ShotBubble bubble = new ShotBubble(Color.BLACK, 2, new Coordinate(5, 5));
        ShotBubble other = new ShotBubble(Color.BLACK, 1, new Coordinate(5, 5));
        assertNotEquals(bubble,other);

    }

    @Test
    void testNotEqualColor() {
        ShotBubble bubble = new ShotBubble(Color.BLACK, 2, new Coordinate(5, 5));
        ShotBubble other = new ShotBubble(Color.RED, 2, new Coordinate(5, 5));
        assertNotEquals(bubble,other);

    }

    @Test
    void testNotEqualString() {
        ShotBubble bubble = new ShotBubble(Color.BLACK, 2, new Coordinate(5, 5));
        String string = "bubble";
        assertNotEquals(bubble,string);

    }
}
