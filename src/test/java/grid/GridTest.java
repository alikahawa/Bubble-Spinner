package grid;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import grid.bubble.Bubble;
import grid.bubble.GridBubble;
import grid.bubble.ShotBubble;

import java.awt.Color;
import java.security.InvalidParameterException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GridTest {

    static Grid grid;
    static Coordinate coordinate;
    static int width;
    static int height;
    static int bubbleSize;
    static int gap;
    static String fileCollision = "/levels/testCollision.txt";
    static String fileStick = "/levels/testStick.txt";
    static String fileStickEven = "/levels/testStickEven.txt";

    @BeforeEach
    void setupTestEnvironment() {
        coordinate = new Coordinate(0, 0);
        width = 1;
        height = 1;
        gap = 2;
        grid = new Grid(coordinate, width, height);
        bubbleSize = 20;
        grid.setBubbleSize(10);
    }

    @Test
    void testGridConstructor() {
        assertEquals(grid.getWidth(), width);
        assertEquals(grid.getHeight(), height);
        assertEquals(grid.getPosition(), coordinate);
        assertEquals(bubbleSize, Grid.DEFAULT_BUBBLE_SIZE);
        assertEquals(grid.getGap(), Grid.DEFAULT_GAP);
    }


    @Test
    void testGridAltnernateConstructor() {
        Grid grid2 = new Grid(coordinate, width, height, Grid.DEFAULT_BUBBLE_SIZE,
                Grid.DEFAULT_GAP);
        assertEquals(grid2.getWidth(), width);
        assertEquals(grid2.getHeight(), height);
        assertEquals(grid2.getPosition(), coordinate);
        assertEquals(grid2.getBubbleSize(), Grid.DEFAULT_BUBBLE_SIZE);
        assertEquals(grid2.getGap(), Grid.DEFAULT_GAP);
    }

    @Test
    void testPutGetBubble() {
        Bubble testBubble = new GridBubble();
        grid = new Grid(coordinate, 2, 2);
        grid.putBubble(testBubble, false);
        assertEquals(1, grid.getBubbleCount());
        assertEquals(grid.getBubble(new Coordinate(0,0)),testBubble);

    }

    //    @Test
    //    void testDoublePut() {
    //        Bubble testBubble = new Bubble();
    //        grid.putBubble(testBubble, false);
    //        assertThrows(IllegalArgumentException.class,
    //            () ->  {
    //                grid.putBubble(testBubble, false);
    //            });
    //    }

    @Test
    void testOutsideXRangePut() {
        Bubble testBubble = new GridBubble(new Coordinate(5,0));
        assertThrows(IllegalArgumentException.class,
            () -> {
                grid.putBubble(testBubble, false);
            });
    }

    @Test
    void testOutsideYRangePut() {
        Bubble testBubble = new GridBubble(new Coordinate(0, 5));
        assertThrows(IllegalArgumentException.class,
            () -> {
                grid.putBubble(testBubble, false);
            });
    }

    @Test
    void testOutsideDoubleRangePut() {
        Bubble testBubble = new GridBubble(new Coordinate(5,5));
        assertThrows(IllegalArgumentException.class,
            () -> {
                grid.putBubble(testBubble, false);
            });
    }

    @Test
    void testNullGridGetBubble() {
        grid = new Grid();
        assertEquals(null, grid.getBubble(new Coordinate(0, 0)));
    }

    @Test
    void testNullGridGetWidthHeight() {
        grid = new Grid();
        assertEquals(-1, grid.getHeight());
        assertEquals(-1, grid.getWidth());
    }

    @Test
    void testSetPosition() {
        assertEquals(coordinate, grid.getPosition());
        Coordinate newPosition = new Coordinate(1, 1);
        grid.setPosition(newPosition);
        assertEquals(newPosition, grid.getPosition());
    }

    @Test
    void testSetGap() {
        assertEquals(grid.getGap(), Grid.DEFAULT_GAP);
        grid.setGap(4);
        assertEquals(grid.getGap(), 4);
    }

    @Test
    void testGetBubbleGrid() {
        Bubble testBubble = new GridBubble();
        grid.putBubble(testBubble,false);
        Bubble[][] otherGrid = grid.getBubbleGrid();
        assertEquals(grid.getBubble(coordinate), otherGrid[0][0]);
    }

    @Test
    void testPutBubbleGrid() {
        Bubble testBubble = new GridBubble();
        grid.putBubble(testBubble,false);
        Bubble[][] newGrid = new Bubble[9][4];
        grid.setBubbleGrid(newGrid);
        assertEquals(newGrid, grid.getBubbleGrid());
    }

    @Test
    void testGridAlternateConstructor() {
        Grid grid2 = new Grid(coordinate,width,height,Grid.DEFAULT_BUBBLE_SIZE,Grid.DEFAULT_GAP);
        assertEquals(grid2.getWidth(),width);
        assertEquals(grid2.getHeight(),height);
        assertEquals(grid2.getPosition(),coordinate);
        assertEquals(grid2.getBubbleSize(),Grid.DEFAULT_BUBBLE_SIZE);
        assertEquals(grid2.getGap(),Grid.DEFAULT_GAP);
    }


    //TODO: fix test.
    @Test
    void testPositionGridToWorldEven() {
        Grid grid = GridCreator.makeGridFromFile(fileCollision);
        grid.setBubbleSize(10);

        grid.setPosition(new Coordinate(5, 5));

        Coordinate positionWorld = Coordinate.positionBubbleWorld(new Coordinate(3, 2), grid);

        Coordinate positionWorld2 = Coordinate.positionBubbleWorld(new Coordinate(2,1), grid);

        assertEquals(12.0, positionWorld2.distance(positionWorld), 0.5);

        // assertEquals(46, positionWorld.getXcoord());
        // assertEquals(30, positionWorld.getYcoord());
    }

    //TODO: fix test.
    @Test
    void testPositionGridToWorldOdd() {
        Grid grid = GridCreator.makeGridFromFile(fileCollision);
        grid.setBubbleSize(10);

        grid.setPosition(new Coordinate(5, 5));

        //Coordinate positionWorld = grid.positionBubbleWorld(new Coordinate(1, 1));

        //assertEquals(28, positionWorld.getXcoord());
        //assertEquals(20, positionWorld.getYcoord());
    }

    @Test
    void testCalculateSide1() {
        Grid grid = new Grid(new Coordinate(0,0), 3, 3);

        ShotBubble shotBubble = new ShotBubble(Color.BLUE, 20,
                new Coordinate(22, 5));

        int side = Collision.calculateSide(shotBubble,
                Coordinate.positionBubbleWorld(new Coordinate(1,
                1), grid), grid);

        assertEquals(1, side);
    }

    @Test
    void testCalculateSide2() {
        Grid grid = new Grid(new Coordinate(0,0), 3, 3);

        ShotBubble shotBubble = new ShotBubble(Color.BLUE, 20,
                new Coordinate(26, 5));

        int side = Collision.calculateSide(shotBubble,
                Coordinate.positionBubbleWorld(new Coordinate(1,
                1), grid), grid);

        assertEquals(2, side);
    }

    @Test
    void testCalculateSide3() {
        Grid grid = new Grid(new Coordinate(0,0), 3, 3);

        ShotBubble shotBubble = new ShotBubble(Color.BLUE, 20,
                new Coordinate(26, 10));

        int side = Collision.calculateSide(shotBubble,
                Coordinate.positionBubbleWorld(new Coordinate(1,
                1), grid), grid);

        assertEquals(3, side);
    }

    @Test
    void testCalculateSide4() {
        Grid grid = new Grid(new Coordinate(0,0), 3, 3);

        ShotBubble shotBubble = new ShotBubble(Color.BLUE, 20,
                new Coordinate(26, 15));

        int side = Collision.calculateSide(shotBubble,
                Coordinate.positionBubbleWorld(new Coordinate(1,
                1), grid), grid);

        assertEquals(4, side);
    }

    @Test
    void testCalculateSide5() {
        Grid grid = new Grid(new Coordinate(0,0), 3, 3);

        ShotBubble shotBubble = new ShotBubble(Color.BLUE, 20,
                new Coordinate(19, 15));

        int side = Collision.calculateSide(shotBubble,
                Coordinate.positionBubbleWorld(new Coordinate(1,
                1), grid), grid);

        assertEquals(5, side);
    }

    @Test
    void testCalculateSide6() {
        Grid grid = new Grid(new Coordinate(0,0), 3, 3);

        ShotBubble shotBubble = new ShotBubble(Color.BLUE, 20,
                new Coordinate(19, 10));

        int side = Collision.calculateSide(shotBubble,
                Coordinate.positionBubbleWorld(new Coordinate(1,
                1), grid), grid);

        assertEquals(6, side);
    }

    @Test
    void testCollision() {
        Grid grid = GridCreator.makeGridFromFile(fileCollision);
        grid.setBubbleSize(20);

        grid.setPosition(new Coordinate(20, 20));

        ShotBubble shotBubble = new ShotBubble(Color.BLACK, 10, new Coordinate(55, 34));
        CollisionSet collisionSet = Collision.collision(shotBubble, grid, 40000000,40000000);
        assertEquals(6, collisionSet.getSide());
    }

    @Test
    void testCollisionNull() {
        Grid grid = GridCreator.makeGridFromFile(fileCollision);

        grid.setPosition(new Coordinate(20,20));

        ShotBubble shotBubble = new ShotBubble(Color.BLACK, 10, new Coordinate(400, 400));
        CollisionSet collisionSet = Collision.collision(shotBubble, grid,4000,4000);

        assertNull(collisionSet);
    }

    @Test
    void testStickInGridSide1Odd() {
        Grid grid = GridCreator.makeGridFromFile(fileStick);
        grid.setBubbleSize(10);

        ShotBubble shotBubble = new ShotBubble();
        shotBubble.setColor(Color.CYAN);
        Bubble bubble = new GridBubble();
        CollisionSet collisionSet = new CollisionSet(shotBubble, bubble, 1, new Coordinate(1, 1),
                32);

        Bubble comparision = new GridBubble(Color.CYAN, 10, new Coordinate(1, 0));

        GridCreator.stickInGrid(collisionSet, grid);

        assertEquals(comparision, grid.getBubble(new Coordinate(1, 0)));
    }

    @Test
    void testStickInGridSide2Odd() {
        Grid grid = GridCreator.makeGridFromFile(fileStick);
        grid.setBubbleSize(10);

        ShotBubble shotBubble = new ShotBubble();
        shotBubble.setColor(Color.CYAN);
        Bubble bubble = new GridBubble();
        CollisionSet collisionSet = new CollisionSet(shotBubble, bubble, 2, new Coordinate(1, 1),
                32);

        Bubble comparision = new GridBubble(Color.CYAN, 10, new Coordinate(2, 0));

        GridCreator.stickInGrid(collisionSet, grid);

        assertEquals(comparision, grid.getBubble(new Coordinate(2, 0)));
    }

    @Test
    void testStickInGridSide3Odd() {
        Grid grid = GridCreator.makeGridFromFile(fileStick);
        grid.setBubbleSize(10);

        ShotBubble shotBubble = new ShotBubble();
        shotBubble.setColor(Color.CYAN);
        Bubble bubble = new GridBubble();
        CollisionSet collisionSet = new CollisionSet(shotBubble, bubble, 3, new Coordinate(1, 1),
                32);

        Bubble comparision = new GridBubble(Color.CYAN, 10, new Coordinate(2, 1));

        GridCreator.stickInGrid(collisionSet, grid);

        assertEquals(comparision, grid.getBubble(new Coordinate(2, 1)));
    }

    @Test
    void testStickInGridSide4Odd() {
        Grid grid = GridCreator.makeGridFromFile(fileStick);
        grid.setBubbleSize(10);

        ShotBubble shotBubble = new ShotBubble();
        shotBubble.setColor(Color.CYAN);
        Bubble bubble = new GridBubble();
        CollisionSet collisionSet = new CollisionSet(shotBubble, bubble, 4, new Coordinate(1, 1),
                32);

        Bubble comparision = new GridBubble(Color.CYAN, 10, new Coordinate(2, 2));

        GridCreator.stickInGrid(collisionSet, grid);

        assertEquals(comparision, grid.getBubble(new Coordinate(2, 2)));
    }

    @Test
    void testStickInGridSide5Odd() {
        Grid grid = GridCreator.makeGridFromFile(fileStick);
        grid.setBubbleSize(10);

        ShotBubble shotBubble = new ShotBubble();
        shotBubble.setColor(Color.CYAN);
        Bubble bubble = new GridBubble();
        CollisionSet collisionSet = new CollisionSet(shotBubble, bubble, 5, new Coordinate(1, 1),
                32);

        Bubble comparision = new GridBubble(Color.CYAN, 10, new Coordinate(1, 2));

        GridCreator.stickInGrid(collisionSet, grid);

        assertEquals(comparision, grid.getBubble(new Coordinate(1, 2)));
    }

    @Test
    void testStickInGridSide6Odd() {
        Grid grid = GridCreator.makeGridFromFile(fileStick);
        grid.setBubbleSize(10);

        ShotBubble shotBubble = new ShotBubble();
        shotBubble.setColor(Color.CYAN);
        Bubble bubble = new GridBubble();
        CollisionSet collisionSet = new CollisionSet(shotBubble, bubble, 6, new Coordinate(1, 1),
                32);

        Bubble comparision = new GridBubble(Color.CYAN, 10, new Coordinate(0, 1));

        GridCreator.stickInGrid(collisionSet, grid);

        assertEquals(comparision, grid.getBubble(new Coordinate(0, 1)));
    }

    @Test
    void tetStickInGridSide10Odd() {
        Grid grid = GridCreator.makeGridFromFile(fileStick);
        grid.setBubbleSize(10);

        ShotBubble shotBubble = new ShotBubble();
        shotBubble.setColor(Color.CYAN);
        Bubble bubble = new GridBubble();
        CollisionSet collisionSet = new CollisionSet(shotBubble, bubble, 10, new Coordinate(1, 1),
                32);

        assertThrows(IllegalArgumentException.class,
            () -> {
                GridCreator.stickInGrid(collisionSet, grid);
            });
    }

    @Test
    void testStickInGrid1Even() {
        Grid grid = GridCreator.makeGridFromFile(fileStickEven);
        grid.setBubbleSize(10);

        ShotBubble shotBubble = new ShotBubble();
        shotBubble.setColor(Color.CYAN);
        Bubble bubble = new GridBubble();
        CollisionSet collisionSet = new CollisionSet(shotBubble, bubble, 1, new Coordinate(2, 2),
                32);

        Bubble comparision = new GridBubble(Color.CYAN, 10, new Coordinate(1, 1));

        GridCreator.stickInGrid(collisionSet, grid);

        assertEquals(comparision, grid.getBubble(new Coordinate(1, 1)));
    }

    @Test
    void testStickInGrid2Even() {
        Grid grid = GridCreator.makeGridFromFile(fileStickEven);
        grid.setBubbleSize(10);

        ShotBubble shotBubble = new ShotBubble();
        shotBubble.setColor(Color.CYAN);
        Bubble bubble = new GridBubble();
        CollisionSet collisionSet = new CollisionSet(shotBubble, bubble, 2, new Coordinate(2, 2),
                32);

        Bubble comparision = new GridBubble(Color.CYAN, 10, new Coordinate(2, 1));

        GridCreator.stickInGrid(collisionSet, grid);

        assertEquals(comparision, grid.getBubble(new Coordinate(2, 1)));
    }

    @Test
    void testStickInGrid3Even() {
        Grid grid = GridCreator.makeGridFromFile(fileStickEven);
        grid.setBubbleSize(10);

        ShotBubble shotBubble = new ShotBubble();
        shotBubble.setColor(Color.CYAN);
        Bubble bubble = new GridBubble();
        CollisionSet collisionSet = new CollisionSet(shotBubble, bubble, 3, new Coordinate(2, 2),
                32);

        Bubble comparision = new GridBubble(Color.CYAN, 10, new Coordinate(3, 2));

        GridCreator.stickInGrid(collisionSet, grid);

        assertEquals(comparision, grid.getBubble(new Coordinate(3, 2)));
    }

    @Test
    void testStickInGrid4Even() {
        Grid grid = GridCreator.makeGridFromFile(fileStickEven);
        grid.setBubbleSize(10);

        ShotBubble shotBubble = new ShotBubble();
        shotBubble.setColor(Color.CYAN);
        Bubble bubble = new GridBubble();
        CollisionSet collisionSet = new CollisionSet(shotBubble, bubble, 4, new Coordinate(2, 2),
                32);

        Bubble comparision = new GridBubble(Color.CYAN, 10, new Coordinate(2, 3));

        GridCreator.stickInGrid(collisionSet, grid);

        assertEquals(comparision, grid.getBubble(new Coordinate(2, 3)));
    }

    @Test
    void testStickInGrid5Even() {
        Grid grid = GridCreator.makeGridFromFile(fileStickEven);
        grid.setBubbleSize(10);

        ShotBubble shotBubble = new ShotBubble();
        shotBubble.setColor(Color.CYAN);
        Bubble bubble = new GridBubble();
        CollisionSet collisionSet = new CollisionSet(shotBubble, bubble, 5, new Coordinate(2, 2),
                32);

        Bubble comparision = new GridBubble(Color.CYAN, 10, new Coordinate(1, 3));

        GridCreator.stickInGrid(collisionSet, grid);

        assertEquals(comparision, grid.getBubble(new Coordinate(1, 3)));
    }

    @Test
    void testStickInGrid6Even() {
        Grid grid = GridCreator.makeGridFromFile(fileStickEven);
        grid.setBubbleSize(10);

        ShotBubble shotBubble = new ShotBubble();
        shotBubble.setColor(Color.CYAN);
        Bubble bubble = new GridBubble();
        CollisionSet collisionSet = new CollisionSet(shotBubble, bubble, 6, new Coordinate(2, 2),
                32);

        Bubble comparision = new GridBubble(Color.CYAN, 10, new Coordinate(1, 2));

        GridCreator.stickInGrid(collisionSet, grid);

        assertEquals(comparision, grid.getBubble(new Coordinate(1, 2)));
    }

    @Test
    void tetStickInGridSide10Even() {
        Grid grid = GridCreator.makeGridFromFile(fileStickEven);
        grid.setBubbleSize(10);

        ShotBubble shotBubble = new ShotBubble();
        shotBubble.setColor(Color.CYAN);
        Bubble bubble = new GridBubble();
        CollisionSet collisionSet = new CollisionSet(shotBubble, bubble, 10, new Coordinate(2, 2),
                32);

        assertThrows(IllegalArgumentException.class,
            () -> {
                GridCreator.stickInGrid(collisionSet, grid);
            });
    }

    @Test
    void testPutBubbleNoChange() {
        String file = "/levels/bubbleRemoval/redTopMiddleEmpty.txt";
        String compareFile = "/levels/bubbleRemoval/redTopMiddleGreen.txt";
        Grid grid = GridCreator.makeGridFromFile(file);
        Grid comparison = GridCreator.makeGridFromFile(compareFile);
        Bubble greenBubble = new GridBubble(Color.GREEN,Grid.DEFAULT_BUBBLE_SIZE,
                new Coordinate(1,0));
        grid.putBubble(greenBubble,true);
        System.out.println(grid.getWidth());
        assertEquals(grid,comparison);
    }

    @Test
    void testPutBubbleChange() {
        String file = "/levels/bubbleRemoval/redTopMiddleEmpty.txt";
        String compareFile = "/levels/bubbleRemoval/empty3x3.txt";
        Grid grid = GridCreator.makeGridFromFile(file);
        Grid comparison = GridCreator.makeGridFromFile(compareFile);
        Bubble redbubble = new GridBubble(Color.RED,Grid.DEFAULT_BUBBLE_SIZE, new Coordinate(1,0));
        grid.putBubble(redbubble,true);
        assertEquals(comparison, grid);
    }

    //supressed because we don't believe it makes sense
    //to make this a constant field just because the file is used in several tests.
    @SuppressWarnings("PMD.AvoidDuplicateLiterals")
    @Test
    void testPutBubbleRemoveDisconnected() {
        String file = "/levels/bubbleRemoval/removeDisconnectedBubbleStart.txt";
        String compareFile = "/levels/bubbleRemoval/removeDisconnectedBubbleEnd.txt";
        Grid grid = GridCreator.makeGridFromFile(file);
        Grid comparison = GridCreator.makeGridFromFile(compareFile);
        Bubble redbubble = new GridBubble(Color.RED,Grid.DEFAULT_BUBBLE_SIZE, new Coordinate(3,1));
        grid.putBubble(redbubble,true);
        System.out.println(grid.getBubbleCount());
        System.out.println(comparison.getBubbleCount());
        assertEquals(grid,comparison);
        assertEquals(comparison.getBubbleCount(), grid.getBubbleCount());
    }

    @Test
    void testGetBubbleOutOfXRange() {
        assertThrows(IllegalArgumentException.class,
            () -> {
                grid.getBubble(new Coordinate(1,0));
            });
    }

    @Test
    void testGetBubbleOutOfYRange() {
        assertThrows(IllegalArgumentException.class,
            () -> {
                grid.getBubble(new Coordinate(0,1));
            });
    }

    @Test
    void testGridNotEqualNull() {
        assertNotEquals(grid,null);
    }

    @Test
    void testGridNotEqualString() {
        assertNotEquals(grid,"Hello");
    }

    @Test
    void testGridNotEqualSameSize() {
        String file = "/levels/bubbleRemoval/removeDisconnectedBubbleStart.txt";
        String compareFile = "/levels/bubbleRemoval/removeDisconnectedBubbleEnd.txt";
        Grid grid = GridCreator.makeGridFromFile(file);
        Grid comparison = GridCreator.makeGridFromFile(compareFile);
        assertNotEquals(grid,comparison);
    }

    @Test
    void testGridNotEqualDifferentSizeX() {
        String file = "/levels/bubbleRemoval/removeDisconnectedBubbleStart.txt";
        String compareFile = "/levels/1x3empty.txt";
        Grid grid = GridCreator.makeGridFromFile(file);
        Grid comparison = GridCreator.makeGridFromFile(compareFile);
        assertNotEquals(grid,comparison);
    }

    @Test
    void testGridNotEqualDifferentSizeY() {
        String file = "/levels/bubbleRemoval/removeDisconnectedBubbleStart.txt";
        String compareFile = "/levels/3x1empty.txt";
        Grid grid = GridCreator.makeGridFromFile(file);
        Grid comparison = GridCreator.makeGridFromFile(compareFile);
        assertNotEquals(grid,comparison);
    }

    @Test
    void testInvalidInputCharacter() {
        String file = "/levels/invalidcharacter.txt";
        assertThrows(InvalidParameterException.class,
            () -> {
                GridCreator.makeGridFromFile(file);
            });
    }

    @Test
    void testGridNotEqualNullBubbles() {
        String file = "/levels/bubbleRemoval/redTopMiddleGreen.txt";
        String compareFile = "/levels/bubbleRemoval/redTopMiddleEmpty.txt";
        Grid grid = GridCreator.makeGridFromFile(file);
        Grid comparison = GridCreator.makeGridFromFile(compareFile);
        assertNotEquals(grid,comparison);
    }

    @Test
    void testSetBubbleSize() {
        int size = 5;
        grid.setBubbleSize(size);
        assertEquals(grid.getBubbleSize(),size);
    }

    @Test
    void testHashNull() {
        Grid otherGrid = new Grid(coordinate, width, height);
        int hash = otherGrid.hashCode();

        assertEquals(hash, grid.hashCode());
    }

    @Test
    void testHashNotNull() {
        grid = new Grid(coordinate, 2, 3);
        grid.putBubble(new GridBubble(Color.gray, 10, new Coordinate(1,1)), true);
        Grid otherGrid = new Grid(coordinate, 2, 3);
        otherGrid.putBubble(new GridBubble(Color.gray, 10, new Coordinate(1,1)), true);
        int hash = otherGrid.hashCode();

        assertEquals(hash, grid.hashCode());
    }

    @Test
    void testEqualsNullBubble() {
        Grid grid2 = GridCreator.makeGridFromFile("/levels/nullBubbletest.txt");
        Grid grid1 = GridCreator.makeGridFromFile("/levels/3x3Empty.txt");

        assertNotEquals(grid1, grid2);
    }

    @Test
    void testSetBubbleCount() {
        assertEquals(0, grid.getBubbleCount());
        grid.setBubbleCount(3);
        assertEquals(3, grid.getBubbleCount());
    }

    @Test
    void testGridRotate() {
        Grid grid = GridCreator.makeGridFromFile("/levels/nullBubbletest.txt");
        grid.setSpeed(0.1);
        assertEquals(grid.getSpeed(),0.1);
        grid.rotate();
        assertEquals(grid.getRotation(),0.1);
        assertEquals(grid.getSpeed(),0.0995);
    }

    @Test
    void testGridRotateotherDirection() {
        Grid grid = GridCreator.makeGridFromFile("/levels/nullBubbletest.txt");
        grid.setSpeed(-0.1);
        assertEquals(grid.getSpeed(),-0.1);
        grid.rotate();
        assertEquals(grid.getRotation(),-0.1);
        assertEquals(grid.getSpeed(),-0.0995);
    }

}

