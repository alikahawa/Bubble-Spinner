package game;

import grid.Coordinate;
import grid.Grid;
import grid.bubble.Bubble;
import grid.bubble.ShotBubble;
import grid.bubble.ShotBubbleFactory;

import java.awt.Color;
import java.util.concurrent.ThreadLocalRandom;


/**
 * Class that is responsible for firing the bubble in the proper direction.
 * Currently simple but can be expanded upon later.
 *
 * @author Group-13
 */
public class Cannon {

    private Coordinate coordinate;
    private transient Color currentColor;
    private transient Color nextColor;
    private transient ShotBubbleFactory shotBubbleFactory;

    /**
     * Constructor.
     */
    public Cannon() {
        coordinate = new Coordinate(0, 0);
        this.shotBubbleFactory = new ShotBubbleFactory();
        currentColor = generateColor();
        nextColor = generateColor();
    }

    /**
     * Method that makes the cannon "shoot"  a new bubble.
     *
     * @param x the x position of the direction(mouse position).
     * @param y the y position of the direction(mouse position).
     * @return a shotBubble with the right position, color and direction.
     */
    //Suppresses a false positive.
    @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
    public ShotBubble shoot(int x, int y) {
        double angle = Math.atan2(y - coordinate.getYcoord(), x - coordinate.getXcoord());
        float dx = (float) (Math.cos(angle) * 9);
        float dy = (float) (Math.sin(angle) * 9);


        ShotBubble bubble = new ShotBubble(currentColor, Grid.DEFAULT_BUBBLE_SIZE, coordinate);

        //updates the color of the current and next bubble to be shot
        updateColor();

        /* System.out.println(dx + "," + dy);

        System.out.println("current color" + currentColor);
        System.out.println("nextcolor" + nextColor);*/
        bubble.setVelX(dx);
        bubble.setVelY(dy);
        return bubble;
    }

    /**
     * Method that generates a random color between RED, GREEN and BLUE.
     * @return randomly generated color
     */
    private Color generateColor() {
        int random = ThreadLocalRandom.current().nextInt(0, 3);
        Color color;
        switch (random) {
            case (0):
                color = Color.red;
                break;
            case (1):
                color = Color.blue;
                break;
            case (2):
                color = Color.green;
                break;
            default:
                throw new IllegalArgumentException();
        }

        return color;
    }

    /**
     * Update the colors of the current and next bubble to be shot.
     */
    private void updateColor() {
        currentColor = nextColor;
        nextColor = generateColor();
    }

    public Color getCurrentColor() {
        return currentColor;
    }

    public Color getNextColor() {
        return nextColor;
    }


    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

}
