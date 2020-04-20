package grid;

import grid.bubble.Bubble;
import grid.bubble.GridBubble;
import grid.bubble.GridBubbleFactory;
import grid.bubble.ShotBubble;

import java.io.InputStream;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

/**
 * This class represent the grid, where all the elements of game will take place.
 *
 * @author Team SEM-13.
 */
public class Grid {

    public static final int DEFAULT_BUBBLE_SIZE = 20;
    public static final int DEFAULT_GAP = 2;
    private static final int CONNECTED_BUBBLES = 3;
    private static final Bubble NULL_BUBBLE = null;
    private Bubble[][] bubbleGrid;
    private int bubbleSize;
    private Coordinate position;
    private int gap;
    private int bubbleCount = 0;
    private Coordinate centerInWorld;
    private double rotation;
    private double speed;
    private static transient GridBubbleFactory gridBubbleFactory = new GridBubbleFactory();
    private transient Coordinate center;


    /**
     * Constructor for the bubbleGrid.
     *
     * @param position   the center of the bubbleGrid on the board (pixel coordinates from
     *                   bottom left?)
     * @param width      the width of the bubbleGrid.
     * @param height     the height of the bubbleGrid.
     * @param bubbleSize The diameter of the bubble in pixels.
     * @param gap        The gap between bubbles in pixels.
     */
    public Grid(Coordinate position, int width, int height, int bubbleSize, int gap) {
        System.out.println("width:" + width);
        System.out.println("height:" + height);
        this.bubbleGrid = new Bubble[width][height];
        this.bubbleSize = bubbleSize;
        this.gap = gap;
        this.position = position;
        this.center = new Coordinate(width / 2, height / 2);
        this.centerInWorld = Coordinate.positionBubbleWorld(center, this);
        this.rotation = 0;
        this.speed = 0;
        bubbleGrid[center.getXcoord()] [center.getYcoord()] =
                gridBubbleFactory.createCenterBubble(DEFAULT_BUBBLE_SIZE,center);
    }

    /**
     * Constructor that assumes some default parameters.
     *
     * @param position the center of the bubbleGrid on the board (pixel coordinates from
     *                 bottom left?)
     * @param width    the width of the bubbleGrid.
     * @param height   the height of the bubbleGrid.
     */
    public Grid(Coordinate position, int width, int height) {
        this.bubbleGrid = new Bubble[width][height];
        this.bubbleSize = DEFAULT_BUBBLE_SIZE;
        this.gap = DEFAULT_GAP;
        this.position = position;
        this.center = new Coordinate(width / 2, height / 2);
        this.centerInWorld = Coordinate.positionBubbleWorld(center, this);
        this.rotation = 0;
        this.speed = 0;
        bubbleGrid[center.getXcoord()] [center.getYcoord()] =
                gridBubbleFactory.createCenterBubble(DEFAULT_BUBBLE_SIZE,center);
    }

    public Grid() {

    }

    /**
     * Gets the width of the bubbleGrid.
     *
     * @return the width of the bubbleGrid if the bubbleGrid is not null, otherwise returns -1.
     */
    public int getWidth() {
        if (bubbleGrid != null) {
            return bubbleGrid.length;
        }
        return -1;
    }


    /** Get the rotational speed of the grid in radians per frame.
     *
     * @return the rotational speed of the grid.
     */
    public double getSpeed() {
        return speed;
    }

    /** Set the rotational speed (radians per frame) of the grid.
     *
     * @param speed the speed to set.
     */
    public void setSpeed(double speed) {
        this.speed = speed;
    }


    /**
     *  Gets the current rotation rotation of the grid.
     *
     * @return the current rotation as a double.
     */
    public double getRotation() {
        return rotation;
    }

    /**
     * Sets the rotation of the grid.
     *
     * @param rotation the rotation to be set.
     */
    public void setRotation(double rotation) {
        this.rotation = rotation;
    }

    public Coordinate getCenterInWorld() {
        return centerInWorld;
    }

    public void setCenterInWorld(Coordinate centerInWorld) {
        this.centerInWorld = centerInWorld;
    }

    /**
     * Gets the height of the bubbleGrid.
     *
     * @return the height of the bubbleGrid if the bubbleGrid is not null, otherwise returns -1.
     */
    public int getHeight() {
        if (bubbleGrid != null) {
            return bubbleGrid[0].length;
        }
        return -1;
    }

    /**
     * Getter of the bubble size.
     *
     * @return the size of the bubble.
     */
    public int getBubbleSize() {
        return bubbleSize;
    }

    /**
     * Setter of the bubble size.
     *
     * @param size the new size to be set.
     */
    public void setBubbleSize(int size) {
        this.bubbleSize = size;
    }

    /**
     * Getter of the position of a bubble on the grid.
     *
     * @return the coordinates of that bubble.
     */
    public Coordinate getPosition() {
        return position;
    }

    /**
     * Setter for a new position.
     *
     * @param position the new position to be set.
     */
    public void setPosition(Coordinate position) {
        this.position = position;
    }

    /**
     * Get the grid.
     *
     * @return the grid.
     */
    public Bubble[][] getBubbleGrid() {
        return this.bubbleGrid;
    }

    /**
     * Setter for a new grid.
     *
     * @param bubbleGrid to be set.
     */
    public void setBubbleGrid(Bubble[][] bubbleGrid) {
        this.bubbleGrid = bubbleGrid;
    }

    /**
     * Get the gap in the grid between bubbles.
     *
     * @return the gap between bubbles.
     */
    public int getGap() {
        return gap;
    }

    /**
     * Setter of the gap on the grid.
     *
     * @param gap to be set as the new gap.
     */
    public void setGap(int gap) {
        this.gap = gap;
    }

    public Coordinate getCenter() {
        return center;
    }

    public void setCenter(Coordinate center) {
        this.center = center;
    }

    /**
     * Getter of the bubble count.
     *
     * @return the bubble count.
     */
    public int getBubbleCount() {
        return bubbleCount;
    }

    /**
     * Setter of the bubbleCount.
     *
     * @param bubbleCount The new bubble count.
     */
    public void setBubbleCount(int bubbleCount) {
        this.bubbleCount = bubbleCount;
    }

    /**
     * Puts a bubble on the bubbleGrid.
     *
     * @param bubble the bubble to be put on the bubbleGrid.
     * @param update indicates whether the grid should be updated
     *               (removal of bubbles) based on adding this bubble).
     */
    public int putBubble(Bubble bubble, boolean update) {
        if (bubble.getLocation().equals(center)) {
            return 0;
        }
        if (bubble.getLocation().getXcoord() >= bubbleGrid.length
                || bubble.getLocation().getYcoord() >= bubbleGrid[0].length) {
            System.out.println("Needs fixing, bubble is outside of grid!");
            throw new IllegalArgumentException();
            //|| bubbleGrid[x][y] != null
            //DO NOTHING FOR NOW
            //TODO: Fix bubble collision to not return out of bounds, or if out of bounds game over?
            //TODO: Also make sure that bubbles arent put in positions where there already are.
        }
        if (bubbleGrid[bubble.getLocation().getXcoord()]
                [bubble.getLocation().getYcoord()] == null) {
            bubbleCount++;
        }
        bubbleGrid[bubble.getLocation().getXcoord()][bubble.getLocation().getYcoord()] = bubble;
        if (update) {
            return cleanGrid(bubble.getLocation().getXcoord(), bubble.getLocation().getYcoord());
        }
        return 0;

    }

    /**
     * Clean the grid from all elements on it.
     *
     * @param x start x position.
     * @param y start y position.
     */
    private int cleanGrid(int x, int y) {
        ArrayList<Bubble> connectedbubbles = getConnected(x, y, true);
        System.out.println(connectedbubbles.size());
        if (connectedbubbles.size() < CONNECTED_BUBBLES) {
            return 0;
        }
        for (int i = 0; i < connectedbubbles.size(); i++) {
            Coordinate coordinate = connectedbubbles.get(i).getLocation();
            if (bubbleGrid[coordinate.getXcoord()][coordinate.getYcoord()] != NULL_BUBBLE) {
                bubbleCount--;
            }
            bubbleGrid[coordinate.getXcoord()][coordinate.getYcoord()] = NULL_BUBBLE;
        }
        //TODO: when the center is implemented this needs to be called from the center of the grid
        // (rotation point, gray bubble, w/e) rather than 0,0
        int removed = removeDetached(center);
        return connectedbubbles.size() + removed;


    }

    /**
     * Remove bubbles detached to each other if they have the same color.
     *
     * @param start position.
     */
    @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
    private int removeDetached(Coordinate start) {
        int count = 0;
        ArrayList<Bubble> connected = getConnected(start.getXcoord(), start.getYcoord(), false);
        for (int i = 0; i < getHeight(); i++) {
            for (int j = 0; j < getWidth(); j++) {
                if (!connected.contains(bubbleGrid[j][i])) {
                    if (bubbleGrid[j][i] != NULL_BUBBLE) {
                        bubbleCount--;
                    }
                    count++;
                    bubbleGrid[j][i] = NULL_BUBBLE;
                }
            }

        }
        return count;

    }

    /** Attempts to rotate the grid based on speed.
     *
     * @return returns if the grid actually rotated (speed  > 0).
     */
    public boolean rotate() {
        if (speed > 0) {
            rotation += speed;
            speed -= 0.0005;
            //speed = Math.max(speed,0);
            return true;
        } else if (speed < 0) {
            rotation += speed;
            speed += 0.0005;
            //speed = Math.min(speed,0);
            return true;
        }
        return false;


    }


    /**
     * Get all connected bubble.
     *
     * @param x         start at x coordinate.
     * @param y         start at y coordinate.
     * @param sameColor whether they have the same color or not.
     * @return a list of the connected bubbles.
     */
    private ArrayList<Bubble> getConnected(int x, int y, boolean sameColor) {
        Bubble start = bubbleGrid[x][y];
        ArrayList<Bubble> linked = new ArrayList<>();
        Queue<Bubble> bubbleQueue = new LinkedList<>();
        bubbleQueue.add(start);
        linked.add(start);
        while (!bubbleQueue.isEmpty()) {

            Bubble bub = bubbleQueue.remove();
            if (bub == null) {
                continue;
            }
            ArrayList<Bubble> neighbours = getNeighbours(bub.getLocation());
            //System.out.println(bub.getColor());
            for (int i = 0; i < neighbours.size(); i++) {
                Bubble b = neighbours.get(i);
                if (b != null && !linked.contains(b)) {
                    if (b.getColor().equals(start.getColor()) || !sameColor) {
                        bubbleQueue.add(b);
                        linked.add(b);
                    }
                }
            }
        }
        return linked;
    }

    /**
     * Get neighbours of the bubble at that position.
     *
     * @param coord where the bubble is located.
     * @return a list of neighbours of that bubble.
     */
    private ArrayList<Bubble> getNeighbours(Coordinate coord) {
        int x = coord.getXcoord();
        int y = coord.getYcoord();
        ArrayList<Bubble> neighbours = new ArrayList<>();
        if (x > 0) {
            neighbours.add(bubbleGrid[x - 1][y]);
        }
        if (x < getWidth() - 1) {
            neighbours.add(bubbleGrid[x + 1][y]);
        }
        if (y % 2 == 0) {
            getNeighboursEven(neighbours,x, y);
        } else {
            getNeighboursOdd(neighbours,x, y);
        }
        //System.out.println("Bubble at " + coord.getXcoord() + ","
        //        + coord.getYcoord() + " has " + neighbours.size() + " neighbous");
        return neighbours;
    }

    private void getNeighboursEven(List<Bubble> neighbours, int x, int y) {
        if (y > 0) {
            neighbours.add(bubbleGrid[x][y - 1]);
            if (x > 0) {
                neighbours.add(bubbleGrid[x - 1][y - 1]);
            }
        }
        if (y < getHeight() - 1) {
            neighbours.add(bubbleGrid[x][y + 1]);
            if (x > 0) {
                neighbours.add(bubbleGrid[x - 1][y + 1]);
            }
        }
    }

    private void getNeighboursOdd(List<Bubble> neighbours, int x, int y) {
        if (y > 0) {
            neighbours.add(bubbleGrid[x][y - 1]);
            if (x < getWidth() - 1) {
                neighbours.add(bubbleGrid[x + 1][y - 1]);

            }
        }
        if (y < getHeight() - 1) {
            neighbours.add(bubbleGrid[x][y + 1]);
            if (x < getWidth() - 1) {
                neighbours.add(bubbleGrid[x + 1][y + 1]);
            }
        }
    }

    /**
     * Gets the bubble at the given coordinates.
     *
     * @param coordinate The coordinates where the bubble is to be put.
     * @return The bubble that is at the coordinates.
     */
    public Bubble getBubble(Coordinate coordinate) {
        if (bubbleGrid == null) {
            return null;
        }
        if (coordinate.getXcoord() >= bubbleGrid.length || coordinate.getYcoord()
                >= bubbleGrid[0].length) {
            throw new IllegalArgumentException();
        }
        return bubbleGrid[coordinate.getXcoord()][coordinate.getYcoord()];
    }

    /**
     * Translates coordinates on the grid into coordinates in the world.
     *
     * @param coordinate The coordinate in the grid.
     * @return The coordinate in the world.
     */
    public final Coordinate positionBubbleWorld(Coordinate coordinate) {
        // For now the visualisation of the grid is that the even lines do not move and odd lines
        // move 0.5 bubble size to the right.
        int x = position.getXcoord();
        int y = position.getYcoord();

        int halfBubble = bubbleSize / 2;

        if (coordinate.getXcoord() > 0) {
            x += coordinate.getXcoord() * gap;
        }

        if (coordinate.getYcoord() % 2 == 0) {
            x += coordinate.getXcoord() * bubbleSize
                    + halfBubble;
            y += coordinate.getYcoord() * bubbleSize
                    + halfBubble;
        } else {
            x += coordinate.getXcoord() * bubbleSize
                    + (gap / 2) + bubbleSize;
            y += coordinate.getYcoord() * bubbleSize
                    + halfBubble;
        }
        Coordinate coor = new Coordinate(x - bubbleSize, y - bubbleSize);
        coor.rotateClockwise(centerInWorld, rotation);
        return coor;
    }

    /** Centers the grid of bubbles based on the screen dimensions.
     *
     * @param width the width of the screen.
     * @param height the height of the screen.
     */
    public void centerGrid(double width, double height) {
        double middleX = width / 2;
        double middleY = height / 3;
        float gridMiddleX = this.getWidth() / 2f;
        float gridMiddleY = this.getHeight() / 2f;

        //Setting the coordinate of the grid such that the middle bubble on the grid is on the
        // middle of the screen.

        int bubbleSize = this.getBubbleSize();
        int gap = this.getGap();

        float newY = (float) middleY - bubbleSize * gridMiddleY;
        float newX = (float) middleX - bubbleSize * gridMiddleX;

        if (gridMiddleY % 2 == 0) {
            newX -= gap * (gridMiddleX - 1);
        } else {
            newX += bubbleSize / 2;
            newX -= gap * (gridMiddleX - 1);
            newX += gap / 2;
        }

        Coordinate centeredGridPosition = new Coordinate(Math.round(newX),
                Math.round(newY));

        this.setPosition(centeredGridPosition);
        this.setCenterInWorld(this.positionBubbleWorld(this.getCenter()));
    }

    /**
     * An equal method to compare objects to the grid.
     *
     * @param other to be compared.
     * @return the result of comparision.
     */
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Grid)) {
            return false;
        }
        Grid g = (Grid) other;
        if (!g.getPosition().equals(this.getPosition()) || g.getWidth() != this.getWidth()
                || g.getHeight() != this.getHeight() || g.getBubbleSize() != this.getBubbleSize()
                || g.getGap() != this.getGap()) {
            return false;
        }
        for (int j = 0; j < getWidth(); j++) {
            if (!Arrays.equals(bubbleGrid[j],g.getBubbleGrid()[j])) {
                return false;
            }
        }
        return true;
    }

    /**
     * A hash method to hash the values of attributes of the grid.
     *
     * @return the hash values.
     */
    @Override
    public int hashCode() {
        int hash = 0;
        for (int i = 0; i < getHeight(); i++) {
            for (int j = 0; j < getWidth(); j++) {
                if (bubbleGrid[j][i] != null) {
                    hash += bubbleGrid[j][i].hashCode();
                }
            }
        }
        hash /= (getHeight() * getWidth());
        return hash;
    }

}
