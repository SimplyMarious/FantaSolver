package com.spme.fantasolver.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class MySQLConnectionManager {

    private MySQLConnectionManager() {}

    public static Connection connectToDatabase() throws ClassNotFoundException, SQLException{
        Connection connection;

        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/fantasolver?allowMultiQueries=true&characterEncoding=utf8","root","root");
        return connection;
    }
}
