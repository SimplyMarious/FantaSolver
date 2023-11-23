package com.spme.fantasolver.dao;

import java.sql.*;

public class UserDAOMySQL implements UserDAO{

    @Override
    public boolean signUp(String username, String password) {
        try {
            return trySignUp(username, password);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean signIn(String username, String password) {
        try {
            return trySignIn(username, password);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isUserExist(String username) throws SQLException {
        Connection connection = DataRetriever.connectToDatabase();
        String searchUser = "SELECT * FROM user WHERE name = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(searchUser)) {
            preparedStatement.setString(1, username);
            boolean result = preparedStatement.executeQuery().next();

            connection.close();
            return result;
        }
    }

    private boolean trySignUp(String username, String password) throws SQLException {
        if (isUserExist(username)) return false;

        Connection connection = DataRetriever.connectToDatabase();
        String insertQuery = "INSERT INTO user (name, password) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.executeUpdate();
        }
        connection.close();

        return true;
    }

    private boolean trySignIn(String username, String password) throws SQLException {
        Connection connection = DataRetriever.connectToDatabase();
        String insertQuery = "SELECT * FROM user WHERE name = ? AND password = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            boolean result = preparedStatement.executeQuery().next();

            connection.close();
            return result;
        }
    }
}
