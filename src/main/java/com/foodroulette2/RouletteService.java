package com.foodroulette2;

import com.foodroulette2.exceptions.DatabaseConnectionException;
import com.foodroulette2.exceptions.DatabaseQueryException;

import java.sql.*;

/**
 * Service class to handle food roulette operations.
 */

public class RouletteService {
    private final String mysqlUrl = "jdbc:mysql://localhost:3306/FoodRoulette";
    private final String user = "root";
    private final String password = "MYSQLTest";

    /**
     * @return A string with random restaurant and food information or an error message.
     */
    public String getRandomFoodInfo() {
        try (Connection connection = DatabaseConnector.getDatabaseConnection(mysqlUrl, user, password)) {
            String restaurantName = getRandomRestaurantName(connection);
            return restaurantName != null ? getFoodInfo(connection, restaurantName) : "Food not found, try again later.";
        } catch (SQLException ex) {
            throw new DatabaseConnectionException("Database connection error: " + ex.getMessage(), ex);
        }
    }

    /**
     * @param connection The connection to the database.
     * @return The restaurant name or null if not found.
     * @throws SQLException if there is a database access error.
     */
    private String getRandomRestaurantName(Connection connection) throws SQLException {
        return executeQueryForSingleResult(connection, "SELECT nazwa FROM FoodRoulette.restauracje ORDER BY RAND() LIMIT 1", "nazwa");
    }

    /**
     * @param connection The connection to the database.
     * @param restaurantName The name of the restaurant.
     * @return A string with food information or an error message.
     * @throws SQLException if there is a database access error.
     */
    private String getFoodInfo(Connection connection, String restaurantName) throws SQLException {
        int restaurantId = getRestaurantId(connection, restaurantName);
        return restaurantId != -1 ? buildFoodInfo(connection, restaurantId) : "Restaurant not found.";
    }

    /**
     * @param connection The connection to the database.
     * @param restaurantId The ID of the restaurant.
     * @return A string with food name and price or an error message.
     * @throws SQLException if there is a database access error.
     */
    private String buildFoodInfo(Connection connection, int restaurantId) throws SQLException {
        String query = "SELECT nazwa, cena FROM FoodRoulette.menu WHERE restauracja_id = ? ORDER BY RAND() LIMIT 1";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, restaurantId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return formatFoodResult(resultSet);
                }
                return "Food item not found.";
            }
        }
    }


    /**
     * @param connection The connection to the database.
     * @param restaurantName The name of the restaurant to find the ID for.
     * @return The ID of the restaurant or -1 if not found.
     * @throws SQLException if there is a database access error.
     */
    private int getRestaurantId(Connection connection, String restaurantName) throws SQLException {
        String query = "SELECT id FROM FoodRoulette.restauracje WHERE nazwa = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, restaurantName);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("id");
                }
                return -1;
            }
        }
    }

    /**
     * @param connection The connection to the database.
     * @param query The SQL query to execute.
     * @param columnName The column name to retrieve from the result set.
     * @return The result as a string or null if no result is found.
     * @throws SQLException if there is a database access error.
     */
    private String executeQueryForSingleResult(Connection connection, String query, String columnName) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getString(columnName);
            }
            return null;
        } catch (SQLException ex) {
            throw new DatabaseQueryException("Database query error: " + ex.getMessage(), ex);
        }
    }

    /**
     * @param resultSet The ResultSet object containing the food information.
     * @return A formatted string with the name and price of the food.
     * @throws SQLException if there is an error retrieving data from the ResultSet.
     */
    private String formatFoodResult(ResultSet resultSet) throws SQLException {
        String foodName = resultSet.getString("nazwa");
        double foodPrice = resultSet.getDouble("cena");
        return "Random food: " + foodName + "\nPrice: " + foodPrice;
    }
}
