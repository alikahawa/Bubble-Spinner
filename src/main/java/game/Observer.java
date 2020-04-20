package game;

/**
 * Basic interface used for the observer pattern.
 * Made because Java's observer is deprecated.
 *
 * @author Group-13
 */
public interface Observer {

    /**
     *  The update method that will be called on observers when an update happens in the game.
     * @param type The type of update that occurs.
     */
    void update(UpdateType type);
}
