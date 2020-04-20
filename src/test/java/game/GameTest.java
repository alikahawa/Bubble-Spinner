package game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import grid.Coordinate;
import grid.Grid;
import grid.GridCreator;
import grid.bubble.ShotBubble;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class GameTest {
    transient Game game;
    transient int xbound;
    transient int ybound;
    transient GridCreator gridCreator;

    @BeforeEach
    void prepare() {
        xbound = 1;
        ybound = 1;
        game = new Game();
    }

    @Test
    void testNotShootingWhilePaused() {
        game.shoot(1,1);
        assertNull(game.getShotBubble());
    }

    @Test
    void testShootingWhilePlaying() {
        game.play();
        game.shoot(1,0);
        assertNotNull(game.getShotBubble());
    }

    @Test
    void testPlay() {
        assertFalse(game.isPlaying());
        game.play();
        assertTrue(game.isPlaying());
    }

    @Test
    void testPause() {
        game.play();
        assertTrue(game.isPlaying());
        game.pause();
        assertFalse(game.isPlaying());
    }

    @Test
    void testGetXBound() {
        assertEquals(game.getXbound(),0);
    }

    @Test
    void testGetYBound() {
        assertEquals(game.getYbound(),0);
    }

    @Test
    void testSetXBound() {
        game.setXbound(2);
        assertEquals(game.getXbound(),2);
    }

    @Test
    void testSetYBound() {
        game.setYbound(2);
        assertEquals(game.getYbound(),2);
    }

    @Test
    void testGetBounces() {
        assertEquals(game.getBounces(),0);
    }

    @Test
    void testAlternateConstructor() {
        Grid grid = GridCreator.makeGridFromFile("/levels/rgbtest.txt");
        game = new Game(grid,xbound,ybound);
        assertEquals(game.getGrid(),grid);
        assertEquals(game.getXbound(),xbound);
        assertEquals(game.getYbound(),ybound);

    }

    @Test
    void testAddObserver() {
        TestObserver testObserver = new TestObserver();
        assertTrue(game.addObserver(testObserver));
        game.play();
        game.shoot(1,1);
        assertTrue(testObserver.called);
        assertTrue(testObserver.type.equals(UpdateType.SHOT));
    }

    @Test
    void testRemoveObserver() {
        TestObserver testObserver = new TestObserver();
        game.addObserver(testObserver);
        assertTrue(game.removeObserver(testObserver));
    }

    @Test
    void testRemoveNotAddedObserver() {
        TestObserver testObserver = new TestObserver();
        assertFalse(game.removeObserver(testObserver));
    }

    @Test
    void testAddDoubleObserver() {
        TestObserver testObserver = new TestObserver();
        assertTrue(game.addObserver(testObserver));
        assertFalse(game.addObserver(testObserver));
    }

    @Test
    void testQuit() {
        game.play();
        assertTrue(game.isPlaying());
        game.quit();
        assertFalse(game.isPlaying());
    }

    @Test
    void testGetCannonPosition() {
        Coordinate coor = game.getCannonPosition();
        assertEquals(coor,new Coordinate(0, 0));
    }

    @Test
    void testSetCannonPosition() {
        Coordinate coor = new Coordinate(1, 1);
        game.setCannonPosition(coor);
        assertEquals(game.getCannonPosition(),coor);
    }

    @Test
    void testCenterGrid() {
        game.getGrid().setCenter(new Coordinate(0,1));
        game.getGrid().centerGrid(100,100);
        assertEquals(game.getGrid().getPosition(), new Coordinate(50,33));
    }

    @Test
    void testSetGrid() {
        Grid grid = GridCreator.makeGridFromFile("/levels/rgbtest.txt");
        game.setGrid(grid);
        assertEquals(grid,game.getGrid());
    }

    @Test
    void testBounce() {
        game.setXbound(100);
        game.setYbound(100);
        game.setCannonPosition(new Coordinate(-10, -10));
        TestObserver testObserver = new TestObserver();
        game.play();
        game.shoot(0,-1);
        game.pause();
        game.setBounces(0);
        game.addObserver(testObserver);
        game.moveBubble();
        assertEquals(game.getBounces(),1);
        assertEquals(UpdateType.BOUNCE,testObserver.type);

    }

    @Test
    void testNoBounce() {
        game.setXbound(100);
        game.setYbound(100);
        game.setCannonPosition(new Coordinate(50, 50));
        TestObserver testObserver = new TestObserver();
        game.play();
        game.shoot(0,-1);
        game.pause();
        game.setBounces(0);
        game.addObserver(testObserver);
        game.moveBubble();
        assertEquals(game.getBounces(),0);
        assertEquals(UpdateType.MOVE,testObserver.type);

    }

    @Test
    void testThreeBounce() {
        game.setXbound(100);
        game.setYbound(100);
        game.setCannonPosition(new Coordinate(-10, -10));
        TestObserver testObserver = new TestObserver();
        game.play();
        game.shoot(0,-1);
        game.pause();
        game.setBounces(0);
        game.addObserver(testObserver);
        game.moveBubble();
        assertEquals(game.getBounces(),1);
        assertEquals(UpdateType.BOUNCE,testObserver.type);
        game.moveBubble();
        assertEquals(game.getBounces(),2);
        game.moveBubble();
        assertEquals(game.getBounces(),0);

    }


    @Test
    void testDoubleShot() {
        game.play();
        game.shoot(1,0);
        game.pause();
        game.setBounces(0);
        ShotBubble bubble = game.getShotBubble();
        game.shoot(1,0);
        assertEquals(game.getShotBubble(),bubble);
    }


    //using a seperate clas rather than mocking because not yet able to figure out how to
    //handle mocking of an interface.
    private class TestObserver implements Observer {

        transient boolean called;
        transient UpdateType type;

        /**
         *  Set the boolean to false when starting.
         */
        TestObserver() {
            called = false;
        }

        /**
         * Set the boolean to true when the method is called.
         * @param type random input type we dont care about
         */
        public void update(UpdateType type) {
            called = true;
            if (this.type == null) {
                this.type = type;
            }
        }
    }




}

