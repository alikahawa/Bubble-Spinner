package game;

import static org.junit.jupiter.api.Assertions.assertEquals;

import grid.Coordinate;
import grid.bubble.ShotBubble;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



public class CannonTest {

    private transient Cannon cannon;

    @BeforeEach
    void prepare() {
        cannon = new Cannon();
    }

    @Test
    void testBasicConstructor() {
        assertEquals(cannon.getCoordinate(),new Coordinate(0,0));
    }


    @Test
    void testBasicShot() {
        ShotBubble testBubble = cannon.shoot(1,0);
        assertEquals(testBubble.getVelX(),9);
        assertEquals(testBubble.getVelY(),0);
    }

    @Test
    void testShotUp() {
        ShotBubble testBubble = cannon.shoot(0,1);
        assertEquals(testBubble.getVelY(),9);
    }

    @Test
    void testSetCoordinate() {
        Coordinate testCoordinate = new Coordinate(1,1);
        cannon.setCoordinate(testCoordinate);
        assertEquals(cannon.getCoordinate(),testCoordinate);
    }

}
