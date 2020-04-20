package grid.bubble;

import grid.Coordinate;
import grid.Grid;

import java.awt.Color;

public class GridBubbleFactory extends BubbleFactory {

    /**
     * The create method to create a bubble.
     */
    @Override
    public Bubble create() {
        return new GridBubble();
    }

    /**
     * Create a bubble.
     *
     * @param location the color you want to assign to the bubble.
     * @return the bubble created.
     */
    @Override
    public Bubble create(Coordinate location) {
        return new GridBubble(Color.GRAY, 10, location);
    }

    /**
     * Create a bubble.
     *
     * @param color    the color you want to assign to the bubble.
     * @param size     the size of the bubble.
     * @param location the location of the bubble.
     * @return the bubble created.
     */
    @Override
    public GridBubble create(Color color, int size, Coordinate location) {
        return new GridBubble(color, size, location);
    }

    @Override
    public GridBubble createRedBubble(int size, Coordinate location) {
        return new GridBubble(Color.RED, size, location);
    }

    @Override
    public GridBubble createBlueBubble(int size, Coordinate location) {
        return new GridBubble(Color.BLUE, size, location);
    }

    @Override
    public GridBubble createGreenBubble(int size, Coordinate location) {
        return new GridBubble(Color.GREEN, size, location);
    }

    public GridBubble createCenterBubble(int size, Coordinate location) {
        return new CenterBubble(size,location);
    }
}
