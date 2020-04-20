package auth;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * This class is the authenticator for the player logging in.
 * It checks whether the player information are saved in the database.
 * If they are, the player will be granted a permission to play.
 * Otherwise the player won't be able to play.
 *
 * @author Team SEM-13.
 */
//suppressing warning because the resources (connections,queries and results).
// Are all being closed after each connection.
// Put above class because it occured for too many methods.
@SuppressWarnings("PMD.CloseResource")
public class DatabaseConnection {

    private static final transient  String DRIVER_STRING = "org.mariadb.jdbc.Driver";
    private static final transient  String URL_STRING = "jdbc:mariadb://84.104.121.142/SEM";
    private static final transient String USER_STRING = "sem";
    private static final transient String PASS_STRING = "123456";
    private static final transient String SQL_WARNING_STRING = "sql exception thrown:";
    private static final transient String CLASS_WARNING_STRING = "class not found";

    /**
     * Method that checks if the user entered the correct password.
     * @param username username to check.
     * @param password password to check.
     * @return
     */
    //suppressing warning because the resources are being closed in lines 31,32 and 36.
    public static boolean authentication(String username, String password) {

        try {

            Class.forName(DRIVER_STRING);

            Connection connection = DriverManager.getConnection(
                    URL_STRING, USER_STRING, PASS_STRING
            );

            String authenticationString = "SELECT username,password FROM PLAYER WHERE userName= ? ";
            PreparedStatement authenticationStatement =
                    connection.prepareStatement(authenticationString);
            authenticationStatement.setString(1,username);
            ResultSet results = authenticationStatement.executeQuery();

            authenticationStatement.close();
            connection.close();
            results.absolute(1);
            return  (results.getString(2).equals(password));


        } catch (SQLException e) {
            System.out.println(SQL_WARNING_STRING + e.getMessage());
            return false;
        } catch (ClassNotFoundException e) {
            System.out.println(CLASS_WARNING_STRING + e.getMessage());
            return false;
        }
    }

    /**
     * This method will save the score of the current player at the db.
     *
     * @param username of the player where the new score will be saved.
     */
    //suppressing warning because the resources are being closed in lines 31,32 and 36.
    public static void writeScore(String username, int score) {
        try {

            Class.forName(DRIVER_STRING);

            Connection connection = DriverManager.getConnection(
                    URL_STRING, USER_STRING, PASS_STRING
            );

            String authenticationString = "INSERT INTO SCORE(userName,score) VALUES ( ? , ? )";
            PreparedStatement authenticationStatement =
                    connection.prepareStatement(authenticationString);
            authenticationStatement.setString(1, username);
            authenticationStatement.setInt(2, score);
            ResultSet results = authenticationStatement.executeQuery();
            authenticationStatement.close();
            results.close();
            connection.close();


        } catch (SQLException e) {
            System.out.println(SQL_WARNING_STRING + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println(CLASS_WARNING_STRING + e.getMessage());
        }
    }

    /**
     * This method retrieves the general scoreboard.
     *
     * @return the resultset containing username + score.
     */
    //PMD comaplins about result result not being closed
    // but it gets closed in the method where it is returned to.
    public static ResultSet retrieveScoreBoard() {
        try {

            Class.forName(DRIVER_STRING);

            Connection connection = DriverManager.getConnection(
                    URL_STRING, USER_STRING, PASS_STRING
            );

            String authenticationString =
                    "SELECT userName,score FROM SCORE ORDER BY score DESC LIMIT 10";
            PreparedStatement authenticationStatement =
                    connection.prepareStatement(authenticationString);
            ResultSet results = authenticationStatement.executeQuery();

            authenticationStatement.close();
            connection.close();
            return results;

        } catch (SQLException e) {
            System.out.println(SQL_WARNING_STRING + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println(CLASS_WARNING_STRING + e.getMessage());
        }
        return null;
    }

    /**
     * Retrieves the max score of the specified username.
     * If no score is found, return 0.
     *
     * @param username Username of which the score should be found.
     * @return String of found score.
     */
    public static String retrieveMaxScoreOfPlayer(String username) {
        try {

            Class.forName(DRIVER_STRING);

            Connection connection = DriverManager.getConnection(
                    URL_STRING, USER_STRING, PASS_STRING
            );

            String queryString =
                    "SELECT score FROM SCORE WHERE username=? ORDER BY score DESC LIMIT 1";

            PreparedStatement authenticationStatement =
                    connection.prepareStatement(queryString);
            authenticationStatement.setString(1,username);
            ResultSet results = authenticationStatement.executeQuery();

            if (!results.next()) {
                return "0";
            }
            authenticationStatement.close();
            connection.close();
            String res =  results.getString(1);
            results.close();
            return res;

        } catch (SQLException e) {
            System.out.println(SQL_WARNING_STRING + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println(CLASS_WARNING_STRING + e.getMessage());
        }
        return null;
    }

}
