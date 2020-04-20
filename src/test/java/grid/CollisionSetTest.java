package grid;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import grid.bubble.Bubble;
import grid.bubble.GridBubble;
import grid.bubble.ShotBubble;
import java.awt.Color;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CollisionSetTest {
    private static Bubble bubble;
    private static ShotBubble shotBubble;
    private static int side;
    private static Coordinate coordinateInGrid;
    private static double distance;

    @BeforeEach
    void setupTestEnvironment() {
        bubble = new GridBubble();
        shotBubble = new ShotBubble();
        side = 3;
        coordinateInGrid = new Coordinate(3, 2);
        distance = 3;
    }

    @Test
    void testConstructor() {
        CollisionSet collisionSet = new CollisionSet(shotBubble, bubble, side, coordinateInGrid,
                distance);
        assertEquals(shotBubble, collisionSet.getShotBubble());
        assertEquals(bubble, collisionSet.getBubble());
        assertEquals(side, collisionSet.getSide());
    }

    @Test
    void testSetShotBubble() {
        CollisionSet collisionSet = new CollisionSet(shotBubble, bubble, side, coordinateInGrid,
                distance);
        assertEquals(shotBubble, collisionSet.getShotBubble());
        ShotBubble newShotBubble = new ShotBubble(Color.BLACK, 4, new Coordinate(0,5));
        collisionSet.setShotBubble(newShotBubble);
        assertEquals(newShotBubble, collisionSet.getShotBubble());
    }

    @Test
    void testSetBubble() {
        CollisionSet collisionSet = new CollisionSet(shotBubble, bubble, side, coordinateInGrid,
                distance);
        assertEquals(bubble, collisionSet.getBubble());
        Bubble newBubble = new GridBubble(Color.BLACK, 3, new Coordinate(7,2));
        collisionSet.setBubble(newBubble);
        assertEquals(newBubble, collisionSet.getBubble());
    }

    @Test
    void testSetSide() {
        CollisionSet collisionSet = new CollisionSet(shotBubble, bubble, side, coordinateInGrid,
                distance);
        assertEquals(side, collisionSet.getSide());
        int newSide = 2;
        collisionSet.setSide(newSide);
        assertEquals(newSide, collisionSet.getSide());
    }

    @Test
    void testSetCoordinateInGrid() {
        CollisionSet collisionSet = new CollisionSet(shotBubble, bubble, side, coordinateInGrid,
                distance);
        assertEquals(coordinateInGrid, collisionSet.getCoordinateInGrid());
        Coordinate newCoordinateInGrid = new Coordinate(8, 1);
        collisionSet.setCoordinateInGrid(newCoordinateInGrid);
        assertEquals(newCoordinateInGrid, collisionSet.getCoordinateInGrid());
    }

    @Test
    void testSetDistance() {
        CollisionSet collisionSet = new CollisionSet(shotBubble, bubble, side, coordinateInGrid,
                distance);
        assertEquals(distance, collisionSet.getDistance());
        collisionSet.setDistance(5.6);
        assertEquals(5.6, collisionSet.getDistance());
    }

    @Test
    void testEquals() {
        CollisionSet collisionSet = new CollisionSet(shotBubble, bubble, side, coordinateInGrid,
                distance);
        CollisionSet collisionSet2 = new CollisionSet(shotBubble, bubble, side, coordinateInGrid,
                distance);
        assertEquals(collisionSet, collisionSet2);
    }

    @Test
    void testNotEquals() {
        ShotBubble newShotBubble = new ShotBubble(Color.BLACK, 4, new Coordinate(0,5));
        Bubble newBubble = new GridBubble(Color.BLACK, 3, new Coordinate(7,2));
        int newSide = 2;
        Coordinate newCoordinateInGrid = new Coordinate(8, 1);

        CollisionSet collisionSet = new CollisionSet(shotBubble, bubble, side, coordinateInGrid,
                distance);
        CollisionSet collisionSet2 = new CollisionSet(newShotBubble, newBubble, newSide,
                newCoordinateInGrid, 7.6);

        assertNotEquals(collisionSet, collisionSet2);
    }

    @Test
    void testEqualsSame() {
        CollisionSet collisionSet = new CollisionSet(shotBubble, bubble, side, coordinateInGrid,
                distance);

        assertEquals(collisionSet, collisionSet);
    }

    @Test
    void testEqualsNull() {
        CollisionSet collisionSet = new CollisionSet(shotBubble, bubble, side, coordinateInGrid,
                distance);

        assertNotEquals(collisionSet, null);
    }

    @Test
    void testEqualsDifferentClass() {
        CollisionSet collisionSet = new CollisionSet(shotBubble, bubble, side, coordinateInGrid,
                distance);

        assertNotEquals(collisionSet, bubble);
    }

    @Test
    void testHashEquals() {
        CollisionSet collisionSet = new CollisionSet(shotBubble, bubble, side, coordinateInGrid,
                distance);
        int hash = collisionSet.hashCode();

        assertEquals(collisionSet.hashCode(), hash);
    }

    @Test
    void testHashNotEquals() {
        ShotBubble newShotBubble = new ShotBubble(Color.BLACK, 4, new Coordinate(0,5));
        Bubble newBubble = new GridBubble(Color.BLACK, 3, new Coordinate(7,2));
        int newSide = 2;
        Coordinate newCoordinateInGrid = new Coordinate(8, 1);

        CollisionSet collisionSet = new CollisionSet(shotBubble, bubble, side, coordinateInGrid,
                distance);
        CollisionSet collisionSet2 = new CollisionSet(newShotBubble, newBubble, newSide,
                newCoordinateInGrid, 3.4);

        int hash = collisionSet.hashCode();
        int hash2 = collisionSet2.hashCode();

        assertNotEquals(hash, hash2);
    }

    @Test
    void testShotBubbleNotEquals() {
        CollisionSet collisionSet = new CollisionSet(shotBubble, bubble, side, coordinateInGrid,
                distance);
        ShotBubble newShotBubble = new ShotBubble(Color.BLACK, 4, new Coordinate(0,5));
        CollisionSet collisionSet2 = new CollisionSet(newShotBubble, bubble, side,
                coordinateInGrid, distance);
        assertNotEquals(collisionSet, collisionSet2);
    }

    @Test
    void testBubbleNotEquals() {
        CollisionSet collisionSet = new CollisionSet(shotBubble, bubble, side, coordinateInGrid,
                distance);
        Bubble newBubble = new GridBubble(Color.BLACK, 3, new Coordinate(7,2));
        CollisionSet collisionSet2 = new CollisionSet(shotBubble, newBubble, side,
                coordinateInGrid, distance);
        assertNotEquals(collisionSet, collisionSet2);
    }

    @Test
    void testCoordinateNotEquals() {
        CollisionSet collisionSet = new CollisionSet(shotBubble, bubble, side, coordinateInGrid,
                distance);
        Coordinate newCoordinateInGrid = new Coordinate(8, 1);
        CollisionSet collisionSet2 = new CollisionSet(shotBubble, bubble, side,
                newCoordinateInGrid, distance);
        assertNotEquals(collisionSet, collisionSet2);
    }

}
