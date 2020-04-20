package grid.bubble;

import static org.junit.jupiter.api.Assertions.assertEquals;

import grid.Coordinate;
import java.awt.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class ShotBubbleFactoryTest {

    private transient ShotBubbleFactory shotBubbleFactory;

    @BeforeEach
    void prepare() {
        shotBubbleFactory = new ShotBubbleFactory();
    }

    @Test
    void createBubbleTest() {
        ShotBubble shotBubble = new ShotBubble(Color.RED,10,new Coordinate(0,0));
        ShotBubble shotBubble1 = shotBubbleFactory.create(Color.RED,10,new Coordinate(0,0));
        assertEquals(shotBubble,shotBubble1);
    }

    @Test
    void createRedBubbleTest() {
        ShotBubble shotBubble = new ShotBubble(Color.RED,10,new Coordinate(0,0));
        ShotBubble shotBubble1 = shotBubbleFactory.createRedBubble(10,new Coordinate(0,0));
        assertEquals(shotBubble,shotBubble1);
    }

    @Test
    void createBlueBubbleTest() {
        ShotBubble shotBubble = new ShotBubble(Color.BLUE,10,new Coordinate(0,0));
        ShotBubble shotBubble1 = shotBubbleFactory.createBlueBubble(10,new Coordinate(0,0));
        assertEquals(shotBubble,shotBubble1);
    }

    @Test
    void createGreenBubbleTest() {
        ShotBubble shotBubble = new ShotBubble(Color.GREEN,10,new Coordinate(0,0));
        ShotBubble shotBubble1 = shotBubbleFactory.createGreenBubble(10,new Coordinate(0,0));
        assertEquals(shotBubble,shotBubble1);
    }
}
