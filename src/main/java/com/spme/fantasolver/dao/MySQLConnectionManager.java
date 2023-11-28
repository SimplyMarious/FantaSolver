package com.spme.fantasolver.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;


public class MySQLConnectionManager {

    private MySQLConnectionManager() {}

    public static Connection connectToDatabase() throws ClassNotFoundException, SQLException{
        Connection connection = null;

        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/fantasolver?characterEncoding=utf8","root","root");

//
//            Logger logger = Logger.getLogger("MySQLConnectionManager");
//            logger.info("Error while connection to database: " + e.getMessage());
        return connection;
    }

}
