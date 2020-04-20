package grid.bubble;


public class B {
}

//package grid.bubble;
//
//        import grid.Coordinate;
//
//        import java.awt.Color;
//
///**
// * This is the bubble class.
// * It represent the bubble with its three main attributes.
// * Every bubble should have a color and location.
// * All bubbles will have the same size, but not all of them will have the same color.
// *
// * @author Team SEM-13.
// */
//public abstract class Bubble {
//
//    private Color color;
//    private int size;
//    private Coordinate location;
//
//    /**
//     * Empty constructor that uses default values.
//     */
//    public Bubble() {
//        color = Color.GRAY;
//        size = 10;
//        this.location = new Coordinate(0, 0);
//    }
//
//    /**
//     * Constructor for only known coordinate with default values.
//     *
//     * @param loc the location of the bubble on the grid.
//     */
//    public Bubble(Coordinate loc) {
//        color = Color.GRAY;
//        size = 10;
//        this.location = loc;
//    }
//
//    /**
//     * Bubble Constructor.
//     *
//     * @param color the color of the bubble.
//     * @param size  the size of the bubble's diameter in pixels.
//     */
//    public Bubble(Color color, int size, Coordinate loc) {
//        this.color = color;
//        this.size = size;
//        this.location = loc;
//    }
//
//    /**
//     * Method to return the bubbles color.
//     *
//     * @return the bubble's color.
//     */
//    public Color getColor() {
//        return this.color;
//    }
//
//    public void setColor(Color color) {
//        this.color = color;
//    }
//
//    public int getSize() {
//        return this.size;
//    }
//
//    /**
//     * Sets the size of the bubble to a new size if the new size is not < 0.
//     *
//     * @param size the new size of the bubble.
//     */
//    public void setSize(int size) {
//        if (size < 0) {
//            throw new IllegalArgumentException();
//        } else {
//            this.size = size;
//        }
//    }
//
//    public Coordinate getLocation() {
//        return this.location;
//    }
//
//    public void setLocation(Coordinate loc) {
//        this.location = loc;
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) {
//            return true;
//        }
//        if (o == null || getClass() != o.getClass()) {
//            return false;
//        }
//        Bubble bubble = (Bubble) o;
//        return size == bubble.size
//                && color.equals(bubble.color)
//                && location.equals(bubble.location);
//    }
//
//    /**
//     * A hash code method.
//     *
//     * @return the hash values of the color, size and location of this bubble.
//     */
//    @Override
//    public int hashCode() {
//        return color.hashCode() + ((Integer) size).hashCode() + location.hashCode();
//    }
//
//
//}
