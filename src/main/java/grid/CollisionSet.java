package grid;

import grid.bubble.Bubble;
import grid.bubble.ShotBubble;

import java.util.Objects;

/**
 * This class contains the logic behind the collision.
 *
 * @author Team SEM-13.
 */
public class CollisionSet {
    private ShotBubble shotBubble;
    private Bubble bubble;
    private int side;
    private Coordinate coordinateInGrid;
    private double distance;

    /**
     * The constructor. Constructs a CollisionSet.
     *
     * @param shotBubble The shot bubble.
     * @param bubble     The bubble on the grid that the shot bubble collides with.
     * @param side       The side the shot bubble is on.
     */
    public CollisionSet(ShotBubble shotBubble, Bubble bubble, int side,
                        Coordinate coordinateInGrid, double distance) {
        this.shotBubble = shotBubble;
        this.bubble = bubble;
        this.side = side;
        this.coordinateInGrid = coordinateInGrid;
        this.distance = distance;
    }

    /**
     * A getter for the shot bubble.
     *
     * @return the shot bubble.
     */
    public ShotBubble getShotBubble() {
        return shotBubble;
    }

    /**
     * A setter for the shot bubble.
     *
     * @param shotBubble which will be set as a new shot bubble.
     */
    public void setShotBubble(ShotBubble shotBubble) {
        this.shotBubble = shotBubble;
    }

    /**
     * A getter for the bubble.
     *
     * @return a bubble.
     */
    public Bubble getBubble() {
        return bubble;
    }

    /**
     * Setter for a new bubble.
     *
     * @param bubble which will be set asa the new bubble.
     */
    public void setBubble(Bubble bubble) {
        this.bubble = bubble;
    }

    /**
     * Get the side of the stage.
     *
     * @return the side.
     */
    public int getSide() {
        return side;
    }

    /**
     * Set a new side.
     *
     * @param side which will be set as the new side.
     */
    public void setSide(int side) {
        this.side = side;
    }

    /**
     * A getter for the coordinators of the bubble in the grid.
     *
     * @return the coordinators of the bubble in the grid.
     */
    public Coordinate getCoordinateInGrid() {
        return coordinateInGrid;
    }

    /**
     * Set new coordinators of the bubble in the grid.
     *
     * @param coordinateInGrid which will be set for the bubble.
     */
    public void setCoordinateInGrid(Coordinate coordinateInGrid) {
        this.coordinateInGrid = coordinateInGrid;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    /**
     * An equal method to compare bubbles.
     *
     * @param o is the object to compare with.
     * @return whether they are the same objects or not.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CollisionSet that = (CollisionSet) o;
        return side == that.side
                && Objects.equals(shotBubble, that.shotBubble)
                && Objects.equals(bubble, that.bubble)
                && Objects.equals(coordinateInGrid, that.coordinateInGrid)
                && Objects.equals(this.distance, ((CollisionSet) o).distance);
    }

    /**
     * A hash method for the elements of the bubble.
     *
     * @return the hash values of the bubble.
     */
    @Override
    public int hashCode() {
        return Objects.hash(shotBubble, bubble, side, coordinateInGrid);
    }
}
