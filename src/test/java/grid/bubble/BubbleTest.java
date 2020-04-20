package grid.bubble;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import grid.Coordinate;
import grid.Grid;

import java.awt.Color;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public abstract class BubbleTest {

    static Coordinate location;
    transient BubbleFactory bubbleFactory;

    @BeforeEach abstract void setupTestEnvironment();

    @Test
    void testEmptyConstructor() {
        Bubble bubble = bubbleFactory.create();
        int implicitSize = 10;
        assertEquals(Color.GRAY, bubble.getColor());
        assertEquals(implicitSize, bubble.getSize());
    }

    @Test
    void testNonEmptyConstructor() {
        int size = 10;
        Bubble bubble = bubbleFactory.createRedBubble(size,new Coordinate(0,0));
        assertEquals(Color.RED, bubble.getColor());
        assertEquals(bubble.getSize(),size);
        assertEquals(bubble.getLocation(),new Coordinate(0,0));
    }

    @Test
    void testSetColor() {
        Bubble bubble = bubbleFactory.createRedBubble(2,new Coordinate(0,0));
        assertEquals(Color.RED, bubble.getColor());
        bubble.setColor(Color.BLUE);
        assertEquals(Color.BLUE, bubble.getColor());
    }

    @Test
    void testSetSizePositive() {
        Bubble bubble = bubbleFactory.createRedBubble(10,location);
        assertEquals(10, bubble.getSize());
        bubble.setSize(7);
        assertEquals(7, bubble.getSize());
    }

    @Test
    void testSetSizeNegative() {
        Bubble bubble = bubbleFactory.createRedBubble(10,location);
        assertEquals(10, bubble.getSize());
        assertThrows(IllegalArgumentException.class, () -> {
            bubble.setSize(-10);
        });
    }

    @Test
    void testEqualsSelf() {
        Bubble bub1  = bubbleFactory.createRedBubble(Grid.DEFAULT_BUBBLE_SIZE,location);
        assertEquals(bub1,bub1);
    }

    @Test
    void testEqualsother() {
        Bubble bub1 =  bubbleFactory.createRedBubble(Grid.DEFAULT_BUBBLE_SIZE,location);
        Bubble bub2 =  bubbleFactory.createRedBubble(Grid.DEFAULT_BUBBLE_SIZE,location);
        assertEquals(bub1,bub2);
    }

    @Test
    void testNotEqualsother() {
        Bubble bub1 =  bubbleFactory.createRedBubble(Grid.DEFAULT_BUBBLE_SIZE,location);
        Bubble bub2 =  bubbleFactory.createBlueBubble(Grid.DEFAULT_BUBBLE_SIZE,location);
        assertNotEquals(bub1,bub2);
    }

    @Test
    void testNotEqualsDifferentObject() {
        Bubble bub1 =  bubbleFactory.createRedBubble(Grid.DEFAULT_BUBBLE_SIZE,location);
        String bub2 = "";
        assertNotEquals(bub1,bub2);
    }

    @Test
    void testSetLocation() {
        Bubble bubble =  bubbleFactory.createRedBubble(3, location);
        assertEquals(location, bubble.getLocation());
        bubble.setLocation(new Coordinate(1,1));
        assertEquals(new Coordinate(1, 1), bubble.getLocation());
    }

}
