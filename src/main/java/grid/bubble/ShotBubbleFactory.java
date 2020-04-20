package grid.bubble;

import grid.Coordinate;

import java.awt.Color;

public class ShotBubbleFactory extends BubbleFactory {

    /**
     * The create method to create a bubble.
     */
    @Override
    public Bubble create() {
        return new ShotBubble();
    }

    /**
     * Create a bubble.
     *
     * @param loc the place you want to have the bubble at.
     * @return the bubble created.
     */
    @Override
    public ShotBubble create(Coordinate loc) {
        return new ShotBubble(Color.GRAY, 10, loc);
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
    public ShotBubble create(Color color, int size, Coordinate location) {
        return new ShotBubble(color, size, location);
    }

    @Override
    public ShotBubble createRedBubble(int size, Coordinate location) {
        return new ShotBubble(Color.RED, size, location);
    }

    @Override
    public ShotBubble createBlueBubble(int size, Coordinate location) {
        return new ShotBubble(Color.BLUE, size, location);
    }

    @Override
    public ShotBubble createGreenBubble(int size, Coordinate location) {
        return new ShotBubble(Color.GREEN, size, location);
    }


}
