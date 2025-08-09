package auth;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * Enhanced database connection class with configuration file support.
 * This class handles all database operations for the Bubble-Spinner game.
 * 
 * Features:
 * - Configuration-based database settings
 * - Connection pooling preparation
 * - Better error handling and logging
 * - Support for multiple environments (dev/test/prod)
 * 
 * @author Team SEM-13
 * @version 2.0
 */
@SuppressWarnings("PMD.CloseResource")
public class DatabaseConnectionModern {
    
    private static final Logger LOGGER = Logger.getLogger(DatabaseConnectionModern.class.getName());
    
    // Configuration properties
    private static final Properties dbConfig = new Properties();
    private static String currentEnvironment = "local";
    
    // Database connection parameters
    private static String driverString;
    private static String urlString; 
    private static String userString;
    private static String passString;
    
    // Error messages
    private static final String SQL_WARNING_STRING = "SQL exception thrown: ";
    private static final String CLASS_WARNING_STRING = "Class not found: ";
    
    static {
        loadDatabaseConfiguration();
    }
    
    /**
     * Loads database configuration from properties file.
     */
    private static void loadDatabaseConfiguration() {
        try (InputStream input = DatabaseConnectionModern.class
                .getResourceAsStream("/config/database.properties")) {
            
            if (input == null) {
                LOGGER.warning("Database configuration file not found, using defaults");
                setDefaultConfiguration();
                return;
            }
            
            dbConfig.load(input);
            currentEnvironment = dbConfig.getProperty("db.environment", "local");
            
            // Load configuration for current environment
            String prefix = "db." + currentEnvironment + ".";
            driverString = dbConfig.getProperty(prefix + "driver", "org.mariadb.jdbc.Driver");
            urlString = dbConfig.getProperty(prefix + "url", "jdbc:mariadb://localhost:3306/SEM");
            userString = dbConfig.getProperty(prefix + "username", "bubble_spinner");
            passString = dbConfig.getProperty(prefix + "password", "bubble_game_2025!");
            
            LOGGER.info("Database configuration loaded for environment: " + currentEnvironment);
            
        } catch (IOException e) {
            LOGGER.severe("Failed to load database configuration: " + e.getMessage());
            setDefaultConfiguration();
        }
    }
    
    /**
     * Sets default configuration when properties file is not available.
     */
    private static void setDefaultConfiguration() {
        driverString = "org.mariadb.jdbc.Driver";
        urlString = "jdbc:mariadb://localhost:3306/SEM";
        userString = "bubble_spinner";
        passString = "bubble_game_2025!";
        currentEnvironment = "default";
    }
    
    /**
     * Gets a database connection with the current configuration.
     * 
     * @return Database connection
     * @throws SQLException if connection fails
     * @throws ClassNotFoundException if driver not found
     */
    private static Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName(driverString);
        return DriverManager.getConnection(urlString, userString, passString);
    }
    
    /**
     * Method that checks if the user entered the correct password.
     * 
     * @param username username to check
     * @param password password to check
     * @return true if authentication successful, false otherwise
     */
    public static boolean authentication(String username, String password) {
        // Demo/Test account for offline testing
        if ("demo".equals(username) && "demo".equals(password)) {
            LOGGER.info("Demo user authenticated successfully");
            return true;
        }
        
        try (Connection connection = getConnection()) {
            String query = "SELECT username, password FROM PLAYER WHERE username = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, username);
                try (ResultSet results = statement.executeQuery()) {
                    if (results.next()) {
                        String storedPassword = results.getString("password");
                        boolean isValid = storedPassword.equals(password);
                        if (isValid) {
                            LOGGER.info("User authenticated successfully: " + username);
                        } else {
                            LOGGER.warning("Authentication failed for user: " + username);
                        }
                        return isValid;
                    } else {
                        LOGGER.warning("User not found: " + username);
                        return false;
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.severe(SQL_WARNING_STRING + e.getMessage());
            return false;
        } catch (ClassNotFoundException e) {
            LOGGER.severe(CLASS_WARNING_STRING + e.getMessage());
            return false;
        }
    }
    
    /**
     * Registers a new user in the database.
     * 
     * @param username The username for the new account
     * @param password The password for the new account
     * @param nickname The nickname/display name for the new account
     * @return true if registration was successful, false otherwise
     */
    public static boolean registerUser(String username, String password, String nickname) {
        try (Connection connection = getConnection()) {
            // Check if username already exists
            String checkQuery = "SELECT COUNT(*) FROM PLAYER WHERE username = ?";
            try (PreparedStatement checkStatement = connection.prepareStatement(checkQuery)) {
                checkStatement.setString(1, username);
                try (ResultSet checkResult = checkStatement.executeQuery()) {
                    checkResult.next();
                    if (checkResult.getInt(1) > 0) {
                        LOGGER.warning("Registration failed - username already exists: " + username);
                        return false;
                    }
                }
            }
            
            // Insert new user
            String insertQuery = "INSERT INTO PLAYER (username, password, nickname) VALUES (?, ?, ?)";
            try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
                insertStatement.setString(1, username);
                insertStatement.setString(2, password);
                insertStatement.setString(3, nickname);
                
                int rowsAffected = insertStatement.executeUpdate();
                if (rowsAffected > 0) {
                    LOGGER.info("User registered successfully: " + username);
                    return true;
                } else {
                    LOGGER.warning("Registration failed - no rows affected for user: " + username);
                    return false;
                }
            }
        } catch (SQLException e) {
            LOGGER.severe("SQL exception during registration: " + e.getMessage());
            return false;
        } catch (ClassNotFoundException e) {
            LOGGER.severe("Class not found during registration: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Saves the score of the current player to the database.
     * 
     * @param username Username of the player
     * @param score Score to be saved
     */
    public static void writeScore(String username, int score) {
        try (Connection connection = getConnection()) {
            String query = "INSERT INTO SCORE (userName, score) VALUES (?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, username);
                statement.setInt(2, score);
                
                int rowsAffected = statement.executeUpdate();
                if (rowsAffected > 0) {
                    LOGGER.info("Score saved successfully for user " + username + ": " + score);
                } else {
                    LOGGER.warning("Failed to save score for user: " + username);
                }
            }
        } catch (SQLException e) {
            LOGGER.severe(SQL_WARNING_STRING + e.getMessage());
        } catch (ClassNotFoundException e) {
            LOGGER.severe(CLASS_WARNING_STRING + e.getMessage());
        }
    }
    
    /**
     * Retrieves the general scoreboard.
     * Note: This method returns a ResultSet that must be closed by the caller.
     * 
     * @return ResultSet containing username and score data, or null if error
     */
    public static ResultSet retrieveScoreBoard() {
        try {
            Connection connection = getConnection();
            String query = "SELECT userName, score FROM SCORE ORDER BY score DESC LIMIT 10";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet results = statement.executeQuery();
            
            // Note: Connection and Statement are NOT closed here
            // They must be closed by the caller when done with the ResultSet
            LOGGER.info("Scoreboard retrieved successfully");
            return results;
            
        } catch (SQLException e) {
            LOGGER.severe(SQL_WARNING_STRING + e.getMessage());
        } catch (ClassNotFoundException e) {
            LOGGER.severe(CLASS_WARNING_STRING + e.getMessage());
        }
        return null;
    }
    
    /**
     * Retrieves the maximum score of the specified username.
     * If no score is found, returns "0".
     * 
     * @param username Username of which the score should be found
     * @return String representation of the highest score
     */
    public static String retrieveMaxScoreOfPlayer(String username) {
        try (Connection connection = getConnection()) {
            String query = "SELECT score FROM SCORE WHERE userName = ? ORDER BY score DESC LIMIT 1";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, username);
                try (ResultSet results = statement.executeQuery()) {
                    if (results.next()) {
                        String maxScore = results.getString("score");
                        LOGGER.info("Max score retrieved for user " + username + ": " + maxScore);
                        return maxScore;
                    } else {
                        LOGGER.info("No scores found for user: " + username);
                        return "0";
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.severe(SQL_WARNING_STRING + e.getMessage());
        } catch (ClassNotFoundException e) {
            LOGGER.severe(CLASS_WARNING_STRING + e.getMessage());
        }
        return "0";
    }
    
    /**
     * Gets the current database environment being used.
     * 
     * @return Current environment name
     */
    public static String getCurrentEnvironment() {
        return currentEnvironment;
    }
    
    /**
     * Gets the current database URL for debugging purposes.
     * 
     * @return Current database URL
     */
    public static String getCurrentDatabaseUrl() {
        return urlString;
    }
}
