package grid;

import grid.bubble.ShotBubble;

import java.util.Objects;

/**
 * This class represent the coordinates which will be used to get positions of bubbles.
 * The coordinates are to be find on the grid.
 *
 * @author Team SEM-13.
 */
public class Coordinate {

    private int xcoord;
    private int ycoord;

    /**
     * Coordinate constructor.
     *
     * @param xcoord the X coordinate.
     * @param ycoord the Y coordinate.
     */
    public Coordinate(int xcoord, int ycoord) {
        this.xcoord = xcoord;
        this.ycoord = ycoord;
    }

    /**
     * Getter of the X coordinate.
     *
     * @return the X coordinate.
     */
    public int getXcoord() {
        return xcoord;
    }

    /**
     * Setter of the X coordinate.
     *
     * @param xcoord the X coordinate to be set.
     */
    public void setXcoord(int xcoord) {
        this.xcoord = xcoord;
    }

    /**
     * Getter for the Y coordinate.
     *
     * @return the Y coordinate.
     */
    public int getYcoord() {
        return ycoord;
    }

    /**
     * A setter for the Y coordinate.
     *
     * @param ycoord the Y coordinate which will be set.
     */
    public void setYcoord(int ycoord) {
        this.ycoord = ycoord;
    }

    /**
     * An equal method which will be comparing the coordinates.
     *
     * @param o the object to be compared with.
     * @return whether the two objects the same or not.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        Coordinate that = (Coordinate) o;
        return xcoord == that.xcoord
                && ycoord == that.ycoord;
    }

    /**
     * A hash method to hash the coordinates.
     *
     * @return the hashed values of the coordinates.
     */
    @Override
    public int hashCode() {
        return Objects.hash(xcoord, ycoord);
    }

    public Coordinate minus(Coordinate other) {
        return new Coordinate(this.xcoord - other.getXcoord(), this.ycoord - other.getYcoord());
    }

    public Coordinate plus(Coordinate other) {
        return new Coordinate(this.xcoord + other.getXcoord(), this.ycoord + other.getYcoord());
    }

    /**
     * Calculates the distance between this Coordinate and another Coordinate.
     * @param other The other Coordinate.
     * @return the distance.
     */
    public double distance(Coordinate other) {
        int x = other.getXcoord() - this.xcoord;
        int y = other.getYcoord() - this.ycoord;
        return Math.sqrt((x * x) + (y * y));
    }

    /**
     *  Calculates the distance between this Coordinate and a ShotBubble.
     * @param bubble The shot bubble.
     * @return the distance.
     */
    public double distance(ShotBubble bubble) {
        float x = bubble.getXpos() - this.xcoord;
        float y = bubble.getYpos() - this.ycoord;
        return Math.sqrt((x * x) + (y * y));
    }


    /** Rotates this coordinate clockwise around another (center) coordinate.
     * And redefines it's position.
     *
     * @param other The coordinate to rotate around.
     * @param angle The angle to rotate by.
     */
    public void rotateClockwise(Coordinate other, double angle) {
        if (other == null) {
            return;
        }

        double diffX = xcoord - other.xcoord;
        double diffY = ycoord - other.ycoord;


        double newx = Math.cos(angle) * diffX - Math.sin(angle) * diffY;
        double newy = Math.sin(angle) * diffX + Math.cos(angle) * diffY;

        xcoord = (int) Math.round(newx + other.xcoord);
        ycoord = (int) Math.round(newy + other.ycoord);

    }

    /** Rotates this coordinate counter clockwise around another (center) coordinate.
     * And redefines it's position.
     *
     * @param other The coordinate to rotate around.
     * @param angle The angle to rotate by.
     */
    public void rotateCounterClockwise(Coordinate other, double angle) {
        if (other == null) {
            return;
        }

        double diffX = xcoord - other.xcoord;
        double diffY = ycoord - other.ycoord;

        double newx = Math.cos(angle) * diffX + Math.sin(angle) * diffY;
        double newy = - Math.sin(angle) * diffX + Math.cos(angle) * diffY;

        xcoord = (int) Math.round(newx + other.xcoord);
        ycoord = (int) Math.round(newy + other.ycoord);

    }

    /**
     * Translates coordinates on the grid into coordinates in the world.
     *
     * @param coordinate The coordinate in the grid.
     * @param grid The grid the bubble is on.
     * @return The coordinate in the world.
     */
    public static final Coordinate positionBubbleWorld(Coordinate coordinate, Grid grid) {
        Coordinate position = grid.getPosition();
        int bubbleSize = grid.getBubbleSize();
        Coordinate centerInWorld = grid.getCenterInWorld();
        double rotation = grid.getRotation();

        // For now the visualisation of the grid is that the even lines do not move and odd lines
        // move 0.5 bubble size to the right.
        int x = position.getXcoord();
        int y = position.getYcoord();

        int halfBubble = bubbleSize / 2;

        if (coordinate.getXcoord() > 0) {
            x += coordinate.getXcoord() * grid.getGap();
        }

        if (coordinate.getYcoord() % 2 == 0) {
            x += coordinate.getXcoord() * bubbleSize
                    + halfBubble;
            y += coordinate.getYcoord() * bubbleSize
                    + halfBubble;
        } else {
            x += coordinate.getXcoord() * bubbleSize
                    + (grid.getGap() / 2) + bubbleSize;
            y += coordinate.getYcoord() * bubbleSize
                    + halfBubble;
        }
        Coordinate coor = new Coordinate(x - bubbleSize, y - bubbleSize);
        coor.rotateClockwise(centerInWorld, rotation);
        return coor;
    }
}
