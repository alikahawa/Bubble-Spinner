package grid.bubble;

import grid.Coordinate;

import java.awt.Color;

/**
 * Basic factory used for the factory pattern.
 * Made because Java's factory is deprecated.
 *
 * @author SEM Team-13
 */
public abstract class BubbleFactory {

    /**
     * The create method to create a bubble.
     */
    @SuppressWarnings("PMD")
    public  abstract Bubble create();

    /**
     * Create a bubble.
     * @param loc the place you want to have the bubble at.
     * @return the bubble created.
     */
    public abstract Bubble create(Coordinate loc);

    /**
     * Create a bubble.
     * @param color the color you want to assign to the bubble.
     * @param size the size of the bubble.
     * @param location the location of the bubble.
     * @return the bubble created.
     */
    public abstract Bubble create(Color color, int size, Coordinate location);

    public abstract Bubble createRedBubble(int size, Coordinate location);

    public abstract Bubble createBlueBubble(int size, Coordinate location);

    public abstract Bubble createGreenBubble(int size, Coordinate location);
}
