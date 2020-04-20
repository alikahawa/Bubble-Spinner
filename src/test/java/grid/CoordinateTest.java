package grid;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

public class CoordinateTest {

    @Test
    void testCoordinateConstructor() {
        int x = 1;
        int y = 2;
        Coordinate co = new Coordinate(x,y);
        assertEquals(x,co.getXcoord());
        assertEquals(y,co.getYcoord());

    }

    @Test
    void testSetXcoord() {
        int x = 1;
        int y = 2;
        Coordinate co = new Coordinate(x,y);
        assertEquals(x,co.getXcoord());
        co.setXcoord(6);
        assertEquals(6, co.getXcoord());
    }

    @Test
    void testSetYcoord() {
        int x = 1;
        int y = 2;
        Coordinate co = new Coordinate(x,y);
        assertEquals(y,co.getYcoord());
        co.setYcoord(6);
        assertEquals(6, co.getYcoord());
    }

    @Test
    void testHashEquals() {
        int x = 1;
        int y = 2;
        Coordinate co = new Coordinate(x, y);
        int hash = co.hashCode();
        assertEquals(co.hashCode(), hash);
    }

    @Test
    void testHashNotEquals() {
        int x1 = 1;
        int x2 = 9;
        int y = 2;
        Coordinate co1 = new Coordinate(x1, y);
        Coordinate co2 = new Coordinate(x2, y);
        int hash1 = co1.hashCode();
        int hash2 = co2.hashCode();
        assertNotEquals(hash1, hash2);
    }

    @Test
    void testEqualsSameObject() {
        Coordinate coordinate = new Coordinate(1, 1);

        assertEquals(coordinate, coordinate);
    }

    @Test
    void testEqualsNull() {
        Coordinate coordinate = new Coordinate(1, 1);

        assertNotEquals(coordinate, null);
    }

    @Test
    void testMinus() {
        Coordinate coordinate1 = new Coordinate(1, 2);
        Coordinate coordinate2 = new Coordinate(3, 4);

        assertEquals(new Coordinate(2, 2), coordinate2.minus(coordinate1));
    }

    @Test
    void testPlus() {
        Coordinate coordinate1 = new Coordinate(1, 2);
        Coordinate coordinate2 = new Coordinate(3, 4);

        assertEquals(new Coordinate(4, 6), coordinate2.plus(coordinate1));
    }

    @Test
    void testDistance() {
        Coordinate coordinate1 = new Coordinate(1, 2);
        Coordinate coordinate2 = new Coordinate(3, 4);

        assertEquals(2.8284, coordinate2.distance(coordinate1), 0.1);
    }

    @Test
    void testRotateClockwiseNull() {
        Coordinate coordinate = new Coordinate(9,2);
        Coordinate expected = new Coordinate(9,2);

        coordinate.rotateClockwise(null, 9);

        assertEquals(expected, coordinate);
    }

    @Test
    void testRotateClockwise() {
        Coordinate coordinate = new Coordinate(9,2);
        Coordinate expected = new Coordinate(3,-7);
        Coordinate center = new Coordinate(1,1);

        coordinate.rotateClockwise(center, 30);
        assertEquals(expected, coordinate);
    }

    @Test
    void testRotateCounterClockwiseNull() {
        Coordinate coordinate = new Coordinate(9,2);
        Coordinate expected = new Coordinate(9,2);

        coordinate.rotateCounterClockwise(null, 9);

        assertEquals(expected, coordinate);
    }

    @Test
    void testRotateCounterClockwise() {
        Coordinate coordinate = new Coordinate(9,2);
        Coordinate expected = new Coordinate(1,9);
        Coordinate center = new Coordinate(1,1);

        coordinate.rotateCounterClockwise(center, 30);

        assertEquals(expected, coordinate);
    }

    @Test
    void testRotateClockwiseCounterClockwise() {
        Coordinate coordinate = new Coordinate(9,2);
        Coordinate expected = new Coordinate(9,2);
        Coordinate center = new Coordinate(1,1);

        coordinate.rotateClockwise(center, 30);
        coordinate.rotateCounterClockwise(center, 30);

        assertEquals(expected, coordinate);
    }

    @Test
    void testRotateCounterClockwiseClockwise() {
        Coordinate coordinate = new Coordinate(9,2);
        Coordinate expected = new Coordinate(9,2);
        Coordinate center = new Coordinate(1,1);

        coordinate.rotateCounterClockwise(center, 30);
        coordinate.rotateClockwise(center, 30);

        assertEquals(expected, coordinate);
    }


}
