package auth;

import java.io.Serializable;
import java.util.Objects;

/**
 * This class defines the player as it is saved in the database.
 * The player is formed with three fields, username, password and nickname.
 * All the fields are private.
 *
 * @author Team 13
 */
public class Player implements Serializable {

    static final long serialVersionUID = 42L;
    private String username;
    private String password;
    private String nickname;

    /**
     * The player attribute are the next three parameters.
     *
     * @param username the username used to login, it is a unique username.
     * @param password the password used to login in combination with the username.
     * @param nickname the nickname of a player the can be shown on the scoreboard.
     */
    public Player(String username, String password, String nickname) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
    }

    /**
     * The getter for the username of a player.
     *
     * @return the username of a given player.
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * The getter for the password of a player.
     *
     * @return the password of a given player.
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * The getter for the nickname of a player.
     *
     * @return the nickname of a given player.
     */
    public String getNickname() {
        return this.nickname;
    }

    /**
     * A setter to set/change the nickname of a player.
     *
     * @param nickname is the nickname to be set.
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * A setter to set/change the password of a player.
     *
     * @param password is the password to be set.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * A setter to set/change the username of a player.
     *
     * @param username is the username to be set.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * A generated equals to check if two players are equals,
     * and if there are two objects equals of not.
     *
     * @param o is the object to be compared to this player.
     * @return whether o is equal to this player or not.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Player player = (Player) o;
        return username.equals(player.username)
                && password.equals(player.password)
                && nickname.equals(player.nickname);
    }

    /**
     * A generated method to hash the object player.
     *
     * @return a hashed player.
     */
    @Override
    public int hashCode() {
        return Objects.hash(username, password, nickname);
    }

    /**
     * Make the player object a string one.
     *
     * @return the information of the player object as string.
     */
    public String toString() {
        String res = "Player username: " + this.getUsername() + "\n"
                + "Player password: " + this.getPassword()
                + "\n" + "Player nickname: " + this.getNickname();
        //res = res + this.getUsername() + "\n" + "Player password: " + this.getPassword();
        //res = res + "\n" + "Player nickname: " + this.getNickname();

        return res;
    }

}
