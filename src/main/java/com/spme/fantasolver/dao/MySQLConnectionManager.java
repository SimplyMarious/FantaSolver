package com.spme.fantasolver.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.logging.Logger;


public class MySQLConnectionManager {

    private MySQLConnectionManager() {}

    public static Connection connectToDatabase(){
        Connection connection = null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/fantasolver?characterEncoding=utf8","root","root");
        }
        catch (Exception e){
            Logger logger = Logger.getLogger("MySQLConnectionManager");
            logger.info("Error while connection to database: " + e.getMessage());
            System.exit(0);
        }

        return connection;
    }

}
