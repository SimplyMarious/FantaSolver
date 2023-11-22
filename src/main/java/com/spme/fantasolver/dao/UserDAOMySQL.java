package com.spme.fantasolver.dao;

import java.sql.*;

public class UserDAOMySQL implements UserDAO{
    @Override
    public boolean signUp(String username, String password) {
        Connection connection = DataRetriever.connectToDatabase();

        try {
            String insertQuery = "INSERT INTO user (name, password) VALUES (?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);

                preparedStatement.executeUpdate();
            }

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean signIn(String username, String password) {
        Connection connection = DataRetriever.connectToDatabase();

        try {
            String insertQuery = "SELECT * FROM user WHERE name = ? AND password = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);

                ResultSet result = preparedStatement.executeQuery();

                if(result.next())
                    return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
