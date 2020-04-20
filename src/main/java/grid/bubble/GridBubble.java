package grid.bubble;

import grid.Coordinate;

import java.awt.Color;

/**
 * This is the Grid bubble class.
 * It represent the bubble located on the grid with its three main attributes.
 * Every bubble should have a color and location.
 * All bubbles will have the same size, but not all of them will have the same color.
 *
 * @author Team SEM-13.
 */
public class GridBubble implements Bubble {

    private Color color;
    private int size;
    private Coordinate location;

    /**
     * The constructor for a ShotBubble.
     *
     * @param color    The color of the shot bubble.
     * @param size     The size of the shot bubble.
     * @param location The current location of the shot bubble in the world.
     */
    public GridBubble(Color color, int size, Coordinate location) {
        this.color = color;
        this.size = size;
        this.location = location;
    }

    /**
     * Empty constructor.
     */
    public GridBubble() {
        color = Color.GRAY;
        size = 10;
        this.location = new Coordinate(0, 0);
    }

    /**
     * Constructor for only known coordinate with default values.
     *
     * @param location the location of the bubble on the grid.
     */
    public GridBubble(Coordinate location) {
        color = Color.GRAY;
        size = 10;
        this.location = location;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Bubble bubble = (Bubble) o;
        return size == bubble.getSize()
                && color.equals(bubble.getColor())
                && location.equals(bubble.getLocation());
    }

    /**
     * A hash code method.
     *
     * @return the hash values of the color, size and location of this bubble.
     */
    @Override
    public int hashCode() {
        return color.hashCode() + ((Integer) size).hashCode() + location.hashCode();
    }
}
