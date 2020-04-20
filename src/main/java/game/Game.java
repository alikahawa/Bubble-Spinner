package game;

import grid.Collision;
import grid.CollisionSet;
import grid.Coordinate;
import grid.Grid;
import grid.GridCreator;
import grid.bubble.ShotBubble;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 *  This is the game class that contains the main loop.
 *  It keeps track of observers to send updates to.
 *  It contains a grid and a cannon that are used
 *
 * @author Group-13
 */
public class Game {

    private transient List<Observer> observers;
    private Grid grid;

    private transient boolean playing;
    private transient ShotBubble shotBubble;
    private transient Cannon cannon;
    private transient ScheduledExecutorService service;
    private transient ScheduledFuture<?> loop;
    private static final int FRAME_INTERVAL = 20;
    private static final ShotBubble NULL_BUBBLE = null;
    private static final int MAX_BOUNCES = 3;
    private int xbound;
    private int ybound;
    private transient int bounces = 0;
    private transient ScoreCalculator scoreCalculator;

    public int getBounces() {
        return bounces;
    }

    /**(Temporary) empty constructor for the game.
     * Creates a grid and starts the game loop that updates the game.
     * For now only updates position of fired ball,
     * later can also handle the updated rotation of the grid.
     *
     */
    public Game() {
        scoreCalculator = new ScoreCalculator(null,0);
        grid = new Grid();
        observers = new ArrayList<>();
        playing = false;
        cannon = new Cannon();
        service = Executors.newSingleThreadScheduledExecutor();
        loop = service.scheduleAtFixedRate(this::mainLoop,0,FRAME_INTERVAL, TimeUnit.MILLISECONDS);
    }

    /**
     *  Constructor that takes in a ready made grid.
     * @param g the grid the game has to use.
     * @param x the x bound of the grid.
     * @param y the y bound of the grid.
     */
    public Game(Grid g,int x,int y) {
        scoreCalculator = new ScoreCalculator(null,0);
        xbound = x;
        ybound = y;
        grid = g;
        observers = new ArrayList<>();
        playing = false;
        cannon = new Cannon();
        service = Executors.newSingleThreadScheduledExecutor();
        loop = service.scheduleAtFixedRate(this::mainLoop,0,FRAME_INTERVAL, TimeUnit.MILLISECONDS);
    }

    /**
     *  Constructor that takes in a  ready made grid.
     * @param g the grid the game has to use.
     * @param x the x bound of the grid.
     * @param y the y bound of the grid.
     * @param sc the ScoreCalculator used for keeping score.
     */
    public Game(Grid g,int x,int y, ScoreCalculator sc) {
        scoreCalculator = sc;
        xbound = x;
        ybound = y;
        grid = g;
        observers = new ArrayList<>();
        playing = false;
        cannon = new Cannon();
        service = Executors.newSingleThreadScheduledExecutor();
        loop = service.scheduleAtFixedRate(this::mainLoop,0,FRAME_INTERVAL, TimeUnit.MILLISECONDS);
    }

    /**
     * Starts the game
     * The game loop is already running but sets to boolean to allow the game to run.
     */
    public void play() {
        playing = true;
    }

    /**
     *  Pauses the game.
     */
    public void pause() {
        playing = false;
    }

    /**
     * Tells the cannon to fire a bubble.
     * @param x the x coordinate of the mouse in the playfield.
     * @param y the y coordinate of the mouse in the playfield.
     */
    public void shoot(int x, int y) {
        if (playing) {
            if (shotBubble == null) {
                shotBubble = cannon.shoot(x,y);
                notifyObservers(UpdateType.SHOT);

            }
        }
    }

    /**
     * This is the main loop of the game, it is executed in the scheduled service.
     * It takes care of moving the fired bubble and checking for collisions.
     * As well as updating observers when required.
     */
    //Suppress the PMD rule violation, because it flags line 146. Line 146 is needed to check if
    // a bubble is outside of the grid. There are only ever 6 sides in a hexagonal grid.
    @SuppressWarnings("PMD.AvoidLiteralsInIfCondition")
    private void mainLoop() {
        if (playing) {
            if (grid.rotate()) {
                notifyObservers(UpdateType.GRID);
                CollisionSet set = Collision.collision(null, grid, xbound, ybound);

                if (set != null) {
                    if (set.getSide() == 7) {
                        notifyObservers(UpdateType.LOSE);
                    }
                }
            }
            if (shotBubble != null) {
                //loop that only runs if there is an active shot bubble.
                firedLoop();
            }
        }
    }

    //Suppress the PMD rule violation, because it flags line 171. Line 171 is needed to check if
    // a bubble is outside of the grid. There are only ever 6 sides in a hexagonal grid.
    @SuppressWarnings("PMD.AvoidLiteralsInIfCondition")
    private void firedLoop() {
        moveBubble();
        //System.out.println("moving bubble?");
        //System.out.println(shotBubble.getLocation().getXcoord() + " "
        //       + shotBubble.getLocation().getYcoord());

        if (shotBubble != null) {
            CollisionSet set = Collision.collision(shotBubble, grid, xbound, ybound);
            if (set != null) {

                if (set.getSide() == 7) {
                    notifyObservers(UpdateType.LOSE);
                }

                //System.out.println("hit something?" + set.getBubble().getColor());
                //System.out.println("side " + set.getSide());
                int removed = GridCreator.stickInGrid(set, grid);
                if (removed > 0) {
                    scoreCalculator.updateScore(removed);
                }
                shotBubble = NULL_BUBBLE;
                bounces = 0;
                notifyObservers(UpdateType.DESTROY);
                notifyObservers(UpdateType.GRID);
            }
            if (grid.getBubbleCount() <= 0) {
                notifyObservers(UpdateType.WIN);
            }
        }
    }

    /**
     * Moves the shot bubble based on its coordinates.
     * Is currently responsible for changing it's direction when hitting the walls too.
     */
    public void moveBubble() {
        boolean bounced = shotBubble.move(xbound,ybound);
        if (bounced) {
            bounces++;
            notifyObservers(UpdateType.BOUNCE);
        }
        if (bounces >= MAX_BOUNCES) {
            //System.out.println("destroy?");
            shotBubble = NULL_BUBBLE;
            bounces = 0;
            notifyObservers(UpdateType.DESTROY);
        } else {
            notifyObservers(UpdateType.MOVE);
        }
    }

    /**
     *  Makes sure to breaks the loop when quitting the game.
     *  so it doesnt stay as a background process.
     */
    public void quit() {
        //make sure to stop the scheduled loop
        playing = false;
        loop.cancel(true);
    }

    public Grid getGrid() {
        return grid;
    }

    public void setGrid(Grid grid) {
        this.grid = grid;
    }

    /** Add an observer to the list of observers.
     *
     * @param ob the observer to be added.
     * @return whether the observer was added succesfully or not.
     */
    public boolean addObserver(Observer ob) {
        if (observers.contains(ob)) {
            return false;
        }
        observers.add(ob);

        return true;
    }

    /** Remove an observer from the list of observers.
     *
     * @param ob the observer to be removed.
     * @return whether the observer was succesfully removed or not.
     */
    public boolean removeObserver(Observer ob) {
        if (observers.contains(ob)) {
            observers.remove(ob);
            return true;
        }
        return false;
    }

    public int getXbound() {
        return xbound;
    }

    public void setXbound(int xbound) {
        this.xbound = xbound;
    }

    public int getYbound() {
        return ybound;
    }

    public void setYbound(int ybound) {
        this.ybound = ybound;
    }

    public boolean isPlaying() {
        return playing;
    }

    public ShotBubble getShotBubble() {
        return shotBubble;
    }

    public void setCannonPosition(Coordinate coor) {
        cannon.setCoordinate(coor);
    }

    public Coordinate getCannonPosition() {
        return cannon.getCoordinate();
    }

    public int getScore() {
        return scoreCalculator.getScore();
    }

    private void notifyObservers(UpdateType type) {
        observers.forEach((ob) -> ob.update(type));
    }

    public Color getCurrentColor() {
        return cannon.getCurrentColor();
    }

    public Color getNextColor() {
        return cannon.getNextColor();
    }

    public void setBounces(int bounce) {
        this.bounces = bounce;
    }


}
