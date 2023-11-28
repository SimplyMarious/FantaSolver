package com.spme.fantasolver.dao;

import com.spme.fantasolver.annotations.Generated;

import java.sql.*;
import java.util.logging.Logger;

public class UserDAOMySQL implements UserDAO{

    @Override
    public boolean signUp(String username, String password) {
        try {
            return trySignUp(username, password);
        } catch (ClassNotFoundException | SQLException exception) {
            Logger logger = Logger.getLogger("UserDAOMySQL");
            logger.info("Error during the sign up: " + exception.getMessage());
            return false;
        }
    }

    @Override
    public boolean signIn(String username, String password) {
        try {
            return trySignIn(username, password);
        } catch (ClassNotFoundException | SQLException exception) {
            Logger logger = Logger.getLogger("UserDAOMySQL");
            logger.info("Error during the sign in: " + exception.getMessage());
            return false;
        }
    }

    public boolean isUserExist(String username) throws ClassNotFoundException, SQLException {
        Connection connection = MySQLConnectionManager.connectToDatabase();
        String searchUser = "SELECT * FROM user WHERE name = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(searchUser)) {
            preparedStatement.setString(1, username);
            boolean result = preparedStatement.executeQuery().next();

            connection.close();
            return result;
        }
    }

    @Generated
    private boolean trySignUp(String username, String password) throws ClassNotFoundException, SQLException {
        if (isUserExist(username)) return false;

        Connection connection = MySQLConnectionManager.connectToDatabase();
        String insertQuery = "INSERT INTO user (name, password) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.executeUpdate();
        }
        connection.close();

        return true;
    }

    @Generated
    private boolean trySignIn(String username, String password) throws ClassNotFoundException, SQLException {
        Connection connection = MySQLConnectionManager.connectToDatabase();
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
