package grid;

import grid.bubble.Bubble;
import grid.bubble.GridBubbleFactory;
import grid.bubble.ShotBubble;

import java.io.InputStream;
import java.security.InvalidParameterException;
import java.util.Scanner;

public class GridCreator {
    private static transient GridBubbleFactory gridBubbleFactory = new GridBubbleFactory();


    /**
     * Sticks a ShotBall into the grid.
     *
     * @param collisionSet The collisionSet of the collision.
     */
    @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
    public static int stickInGrid(CollisionSet collisionSet, Grid grid) {
        int side = collisionSet.getSide();
        Coordinate locationHitBubble = collisionSet.getCoordinateInGrid();
        //ShotBubble shot = collisionSet.getShotBubble();

        int x = locationHitBubble.getXcoord();
        int y = locationHitBubble.getYcoord();
        System.out.println(x + "," + y);
        Coordinate coor;
        //For more explanation about shifting, please check the wiki.
        if (y % 2 == 0) {
            System.out.println(x + "," + y);
            coor = stickEvenRow(side, x, y);
            x = coor.getXcoord();
            y = coor.getYcoord();
        } else {
            coor = stickOddRow(side, x, y);
            x = coor.getXcoord();
            y = coor.getYcoord();

        }
        Coordinate worldCoordinate  = grid.positionBubbleWorld(new Coordinate(x, y));
        Coordinate hitWorldCoordinate = grid.positionBubbleWorld(locationHitBubble);


        int xdifCenter = worldCoordinate.getXcoord() - grid.getCenterInWorld().getXcoord();
        int ydifCenter = worldCoordinate.getYcoord() - grid.getCenterInWorld().getYcoord();
        float xspeed = collisionSet.getShotBubble().getVelX();
        float yspeed = collisionSet.getShotBubble().getVelY();

        double speedbonus = 0.07;
        if ((xdifCenter > 0 && yspeed < 0) || (xdifCenter > 0 && yspeed > 0)) {
            speedbonus = -speedbonus;
        }
        System.out.println("adding speed:" + speedbonus);
        grid.setSpeed(speedbonus);

        ShotBubble shotBubble = collisionSet.getShotBubble();
        Bubble bubble = gridBubbleFactory.create(shotBubble.getColor(),
                grid.getBubbleSize(), new Coordinate(x, y));
        return grid.putBubble(bubble,true);
    }

    private static Coordinate stickEvenRow(int side, int x, int y) {
        switch (side) {
            case (1):
                y -= 1;
                x -= 1;
                break;
            case (2):
                y -= 1;
                break;
            case (3):
                x += 1;
                break;
            case (4):
                y += 1;
                break;
            case (5):
                y += 1;
                x -= 1;
                break;
            case (6):
                x -= 1;
                break;
            default:
                throw new IllegalArgumentException();
        }
        System.out.println(x + "," + y);
        return new Coordinate(x,y);

    }

    private static Coordinate stickOddRow(int side, int x, int y) {
        switch (side) {
            case (1):
                y -= 1;
                break;
            case (2):
                y -= 1;
                x += 1;
                break;
            case (3):
                x += 1;
                break;
            case (4):
                x += 1;
                y += 1;
                break;
            case (5):
                y += 1;
                break;
            case (6):
                x -= 1;
                break;
            default:
                throw new IllegalArgumentException();
        }
        return new Coordinate(x,y);
    }

    /**
     * Static method that generates a Grid class from an input file location.
     *
     * @param file the location of the file relative to the resources folder.
     * @return A grid constructed with the parameters retrieved from the file.
     */
    @SuppressWarnings("PMD")
    public static Grid makeGridFromFile(String file) {
        InputStream stream = Grid.class.getResourceAsStream(file);
        if (stream == null) {
            throw new InvalidParameterException("Could not load from file: " + file);
        }
        Scanner sc = new Scanner(stream);

        int width = sc.nextInt();
        int height = sc.nextInt();
        sc.nextLine();
        sc.useDelimiter("");
        Grid grid = new Grid(new Coordinate(0, 0), width, height);
        populateGrid(sc, grid, height, width);
        sc.close();
        return grid;
    }

    /**
     * Populate the grid with bubbles.
     *
     * @param sc     scanner to read.
     * @param grid   where the populations will be set.
     * @param height the height of the grid.
     * @param width  the width of the grid.
     */
    //TODO: Check how to fix the now suppressed warning.
    @SuppressWarnings("PMD")
    private static void populateGrid(Scanner sc, Grid grid, int height, int width) {
        for (int i = 0; i < height; i++) {

            if (sc.hasNextLine()) {
                String line = sc.nextLine();
                for (int j = 0; j < width; j++) {
                    char color = line.charAt(j);
                    Bubble bubbl = charToBubble(color, j, i, grid);
                    if (bubbl != null) {
                        grid.putBubble(bubbl, false);
                    }
                }
            }
        }
    }

    private static Bubble charToBubble(char color, int j, int i, Grid grid) {
        switch (color) {
            case ('R'):
                return gridBubbleFactory.createRedBubble(
                        grid.getBubbleSize(),new Coordinate(j,i));
            //grid[j][i] = red;
            case ('B'):
                return gridBubbleFactory.createBlueBubble(
                        grid.getBubbleSize(),new Coordinate(j,i));
            //grid[j][i] = blue;
            case ('G'):
                return gridBubbleFactory.createGreenBubble(
                        grid.getBubbleSize(),new Coordinate(j,i));
            //grid[j][i] = green;
            case ('.'):
                return null;
            //we do nothing because we dont want a bubble in this spot
            default:
                throw new InvalidParameterException();
        }
    }
}
