package grid;

import grid.bubble.Bubble;
import grid.bubble.ShotBubble;

import java.util.ArrayList;
import java.util.List;

public class Collision {

    /**
     * Calculates if there is a collision, and if there is it returns a CollisionSet.
     *
     * @param shotBubble The shot bubble.
     * @return The CollisionSet for the shot bubble.
     */
    //Suppressed because it gives a false DU positive on line 396.
    @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
    public static CollisionSet collision(ShotBubble shotBubble, Grid grid, int xbound, int ybound) {

        List<CollisionSet> collisionSetList = new ArrayList<>();

        Bubble[][] bubbleGrid = grid.getBubbleGrid();

        //For each bubble in the grid, check if it collides with the shot bubble.
        for (int i = 0; i < bubbleGrid.length; i++) {
            for (int j = 0; j < bubbleGrid[i].length; j++) {
                Bubble bubble = grid.getBubble(new Coordinate(i, j));

                if (bubble == null) {
                    continue;
                }

                Coordinate bubbleInWorld = Coordinate.positionBubbleWorld(new Coordinate(i, j),
                        grid);

                if (bubbleInWorld.getXcoord() <= 0
                        || bubbleInWorld.getXcoord() >= (xbound * 2)) {
                    return new CollisionSet(null, bubble, 7, null, 0);
                }

                if (bubbleInWorld.getYcoord() <= 0
                        || bubbleInWorld.getYcoord() >= (ybound)) {
                    return new CollisionSet(null, bubble, 7, null, 0);
                }

                if (shotBubble != null) {

                    double distance = bubbleInWorld.distance(shotBubble);

                    if (distance <= ((double) ((grid.getBubbleSize() + shotBubble.getSize() / 2)
                            + 0.1))) {
                        int side = calculateSide(shotBubble, bubbleInWorld, grid);
                        collisionSetList.add(new CollisionSet(shotBubble, bubble, side,
                                new Coordinate(i, j), distance));
                    }
                }
            }
        }

        if (collisionSetList.isEmpty()) {
            return null;
        }

        CollisionSet firstHit = collisionSetList.get(0);

        //Check which of the collisions is the closest.
        for (int i = 0; i < collisionSetList.size(); i++) {
            if (collisionSetList.get(i).getDistance() < firstHit.getDistance()) {
                firstHit = collisionSetList.get(i);
            }
        }

        return firstHit;
    }

    /**
     * Returns what side of the ball on the grid the shot ball is on. Numbers 1-6. Upper left is 1,
     * from there it continues clockwise.
     *
     * @param shotBubble the shotbubble to find the side of.
     * @param gridBubbleCoordinate the coordinate of the gridbubble to compare to.
     * @return the integer indicating the side where it should be placed.
     */
    public static int calculateSide(ShotBubble shotBubble, Coordinate gridBubbleCoordinate,
                                 Grid grid) {
        Coordinate shotbubbleLocation = shotBubble.getLocation();

        Coordinate counterClockwiseShotBubble = new Coordinate(shotbubbleLocation.getXcoord(),
                shotbubbleLocation.getYcoord());
        counterClockwiseShotBubble.rotateCounterClockwise(grid.getCenterInWorld(),
                grid.getRotation());

        gridBubbleCoordinate.rotateCounterClockwise(grid.getCenterInWorld(), grid.getRotation());

        float shotX = counterClockwiseShotBubble.getXcoord();
        float shotY = counterClockwiseShotBubble.getYcoord();

        int gridX = gridBubbleCoordinate.getXcoord();
        int gridY = gridBubbleCoordinate.getYcoord();

        float centeredX = shotX - gridX;
        float centeredY = shotY - gridY;

        if (shotX <= gridX) {
            return calculateSideLeft(centeredY, centeredX);
        } else {
            return calculateSideRight(centeredY, centeredX);
        }
    }

    private static int calculateSideLeft(float centeredY, float centeredX) {
        if (centeredY >= centeredX && centeredY <= -centeredX) {
            return 6;
        }
        if (centeredY < centeredX) {
            return 1;
        } else {
            return 5;
        }
    }

    private static int calculateSideRight(float centeredY, float centeredX) {
        if (centeredY <= centeredX && centeredY >= -centeredX) {
            return 3;
        }
        if (centeredY < -centeredX) {
            return 2;
        } else {
            return 4;
        }
    }
}
