package grid.bubble;

import static org.junit.jupiter.api.Assertions.assertEquals;

import grid.Coordinate;
import java.awt.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



public class GridBubbleFactoryTest {


    private transient GridBubbleFactory gridBubbleFactory;

    @BeforeEach
    void prepare() {
        gridBubbleFactory = new GridBubbleFactory();
    }

    @Test
    void createBubbleTest() {
        GridBubble gridBubble = new GridBubble(Color.RED,10,new Coordinate(0,0));
        GridBubble gridBubble1 = gridBubbleFactory.create(Color.RED,10,new Coordinate(0,0));
        assertEquals(gridBubble,gridBubble1);
    }

    @Test
    void createRedBubbleTest() {
        GridBubble gridBubble = new GridBubble(Color.RED,10,new Coordinate(0,0));
        GridBubble gridBubble1 = gridBubbleFactory.createRedBubble(10,new Coordinate(0,0));
        assertEquals(gridBubble,gridBubble1);
    }

    @Test
    void createBlueBubbleTest() {
        GridBubble gridBubble = new GridBubble(Color.BLUE,10,new Coordinate(0,0));
        GridBubble gridBubble1 = gridBubbleFactory.createBlueBubble(10,new Coordinate(0,0));
        assertEquals(gridBubble,gridBubble1);
    }

    @Test
    void createGreenBubbleTest() {
        GridBubble gridBubble = new GridBubble(Color.GREEN,10,new Coordinate(0,0));
        GridBubble gridBubble1 = gridBubbleFactory.createGreenBubble(10,new Coordinate(0,0));
        assertEquals(gridBubble,gridBubble1);
    }
}
