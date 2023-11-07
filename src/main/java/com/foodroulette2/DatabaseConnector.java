package com.foodroulette2;

import com.foodroulette2.exceptions.DatabaseConnectionException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * This class handles the connection to the database.
 * It encapsulates the logic for creating a connection to the database with the given credentials.
 */
public class DatabaseConnector {

    /**
     * Attempts to establish a connection to the specified MySQL database URL using the provided credentials.
     *
     * @param mysqlUrl the URL of the MySQL database to connect to, which includes the server's IP address
     *                 and the database's name (e.g., "jdbc:mysql://localhost:3306/myDatabase").
     * @param user the username for the MySQL database.
     * @param password the password for the MySQL database.
     * @return a {@code Connection} object that represents a connection to the specified database.
     * @throws SQLException if a database access error occurs or the url is {@code null}.
     * @throws DatabaseConnectionException if the MySQL driver class is not found.
     */
    public static Connection getDatabaseConnection(String mysqlUrl, String user, String password) throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new DatabaseConnectionException("Database driver not found.", e);
        }

        return DriverManager.getConnection(mysqlUrl, user, password);
    }
}