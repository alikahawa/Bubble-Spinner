package grid.bubble;

import grid.Coordinate;

import java.awt.Color;

/** CenterBubble class, Currently does not have extra functionality.
 * but can be expanded upon in the future.
 *
 */
public class CenterBubble extends GridBubble {

    /** Constructor for the Centerbubble.
     *
     * @param size the size (radius) of the bubble.
     * @param location the location where it is placed in the grid.
     */
    public CenterBubble(int size, Coordinate location) {
        setColor(Color.gray);
        setSize(size);
        setLocation(location);
    }
}
