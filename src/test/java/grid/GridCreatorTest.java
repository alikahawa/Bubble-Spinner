package grid;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import grid.bubble.Bubble;
import grid.bubble.GridBubble;
import java.awt.Color;
import java.security.InvalidParameterException;
import org.junit.jupiter.api.Test;

public class GridCreatorTest {

    static String file;

    @Test
    void testMakeGridFromFile() {
        file = "/levels/test.txt";
        Grid grid2 = GridCreator.makeGridFromFile(file);
        Bubble redBubble = new GridBubble(Color.RED,Grid.DEFAULT_BUBBLE_SIZE, new Coordinate(0,0));
        assertEquals(grid2.getBubble(new Coordinate(0,0)),redBubble);
    }

    @Test
    void testDifferentColorsFromFile() {
        file = "/levels/rgbtest.txt";
        Grid grid = GridCreator.makeGridFromFile(file);
        assertEquals(grid.getBubble(new Coordinate(0, 0)).getColor(), Color.RED);
        assertEquals(grid.getBubble(new Coordinate(1, 0)).getColor(), Color.GRAY);
        assertEquals(grid.getBubble(new Coordinate(2, 0)).getColor(), Color.BLUE);
    }

    @Test
    void testNullBubbleFromFile() {
        file = "/levels/testnull.txt";
        Grid grid = GridCreator.makeGridFromFile(file);
        Bubble redBubble = new GridBubble(Color.RED,Grid.DEFAULT_BUBBLE_SIZE, new Coordinate(2,0));
        assertEquals(grid.getBubble(new Coordinate(2,0)),redBubble);
        assertEquals(Color.GRAY, grid.getBubble(new Coordinate(1,0)).getColor());
    }

    @Test
    void testMissingFile() {
        file = "/levels/nofile.txt";
        assertThrows(InvalidParameterException.class, () -> {
            GridCreator.makeGridFromFile(file);
        });
    }
}
