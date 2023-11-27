package com.spme.fantasolver.dao;

import java.sql.Connection;
import java.sql.DriverManager;


public class MySQLConnectionManager {
    private Connection mySqlConnection;

    public void retrieveData() {
        this.mySqlConnection = connectToDatabase();
    }

    public static Connection connectToDatabase(){
        Connection connection = null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/fantasolver?characterEncoding=utf8","root","root");
        }
        catch (Exception e){
            System.out.println("Error during connection to database: " + e.getMessage());
            e.printStackTrace();
            System.exit(0);
        }

        return connection;
    }

}