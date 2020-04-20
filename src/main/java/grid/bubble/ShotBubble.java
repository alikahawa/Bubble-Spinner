package grid.bubble;

import grid.Coordinate;
import grid.Grid;

import java.awt.Color;
import java.util.Objects;

/**
 * This class represent the shot bubble object.
 * The shot bubble does have x, y coordinate which changes immediately after shooting.
 *
 * @author Team SEM-13.
 */
public class ShotBubble implements Bubble {

    private Color color;
    private int size;
    private Coordinate location;
    private float xpos;
    private float ypos;
    private float velX;
    private float velY;

    /**
     * The constructor for a ShotBubble.
     *
     * @param color    The color of the shot bubble.
     * @param size     The size of the shot bubble.
     * @param location The current location of the shot bubble in the world.
     */
    public ShotBubble(Color color, int size, Coordinate location) {
        this.color = color;
        this.size = size;
        this.location = location;
        this.xpos = location.getXcoord();
        this.ypos = location.getYcoord();
        this.velX = 0;
        this.velY = 0;
    }

    /**
     * The empty constructor to make a default ShotBubble.
     */
    public ShotBubble() {
        color = Color.GRAY;
        size = 10;
        this.location = new Coordinate(0, 0);
        velX = 0;
        velY = 0;
    }

    /**
     * Getter of the X coordinate.
     *
     * @return the X value.
     */
    public float getVelX() {
        return velX;
    }

    /**
     * Setter of the X value of the shot bubble.
     *
     * @param velX the new value to be set.
     */
    public void setVelX(float velX) {
        this.velX = velX;
    }

    /**
     * Getter of the Y coordinate of the shot bubble.
     *
     * @return the Y value.
     */
    public float getVelY() {
        return velY;
    }

    /**
     * \
     * Setter of the Y value of the shot bubble.
     *
     * @param velY the new coordinate of the shot bubble.
     */
    public void setVelY(float velY) {
        this.velY = velY;
    }

    public float getXpos() {
        return xpos;
    }

    public void setXpos(float xpos) {
        this.xpos = xpos;
    }

    public float getYpos() {
        return ypos;
    }

    public void setYpos(float ypos) {
        this.ypos = ypos;
    }

    /**
     * Moves the bubble one step in the direction of its velocity.
     */
    public boolean move(int xbound, int ybound) {
        xpos += velX;
        ypos += velY;
        boolean bounced;

        if (getXpos() <= (getSize() / 2)
                || getXpos() >= ((xbound * 2) - getSize() / 2)) {
            bounced = true;
            setVelX(-getVelX());
        } else if (getYpos() <= (getSize() / 2)
                || getYpos() >= (ybound - getSize() / 2)) {
            bounced = true;
            setVelY(-getVelY());
        } else {
            bounced = false;
        }
        return bounced;
    }

    /**
     * An equal method to compare the shot bubbles with other objects.
     *
     * @param o the object to be compared with.
     * @return the result of comparision.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        // Check this out :)
        ShotBubble that = (ShotBubble) o;
        return Float.compare(that.velX, velX) == 0
                && Float.compare(that.velY, velY) == 0
                && size == that.getSize()
                && color.equals(that.getColor())
                && location.equals(that.getLocation());
    }


    /**
     * A hash method to hash the values of the shot bubble.
     *
     * @return the hash values of the shot bubble attributes.
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), velX, velY);
    }

    /**
     * A getter for the color of the bubble.
     *
     * @return the color of the bubble.
     */
    @Override
    public Color getColor() {
        return this.color;
    }

    /**
     * A setter of the color of the bubble.
     *
     * @param color to be set as the new color.
     */
    @Override
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * A getter for the size of the bubble.
     *
     * @return the size of the bubble.
     */
    @Override
    public int getSize() {
        return this.size;
    }

    /**
     * A setter of the new size.
     *
     * @param size to be set as the new size.
     */
    @Override
    public void setSize(int size) {
        if (size < 0) {
            throw new IllegalArgumentException();
        } else {
            this.size = size;
        }
    }

    /**
     * A getter of the location of the bubble.
     *
     * @return the location of the bubble.
     */
    @Override
    public Coordinate getLocation() {
        return this.location;
    }

    /**
     * A setter of the location of the bubble.
     *
     * @param loc to be set as the new location.
     */
    @Override
    public void setLocation(Coordinate loc) {
        this.location = loc;
    }
}
