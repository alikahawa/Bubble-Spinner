package grid.bubble;

import grid.Coordinate;

import java.awt.Color;

/**
 * This is the bubble class.
 * It represent the bubble with its three main attributes.
 * Every bubble should have a color and location.
 * All bubbles will have the same size, but not all of them will have the same color.
 *
 * @author Team SEM-13.
 */
public interface Bubble {

    /**
     * A getter for the color of the bubble.
     *
     * @return the color of the bubble.
     */
    Color getColor();

    /**
     * A setter of the color of the bubble.
     *
     * @param color to be set as the new color.
     */
    void setColor(Color color);

    /**
     * A getter for the size of the bubble.
     *
     * @return the size of the bubble.
     */
    int getSize();

    /**
     * A setter of the new size.
     *
     * @param size to be set as the new size.
     */
    void setSize(int size);

    /**
     * A getter of the location of the bubble.
     *
     * @return the location of the bubble.
     */
    Coordinate getLocation();

    /**
     * A setter of the location of the bubble.
     *
     * @param loc to be set as the new location.
     */
    void setLocation(Coordinate loc);


    boolean equals(Object other);


}
