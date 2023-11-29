package com.spme.fantasolver.dao;

import com.spme.fantasolver.utility.Utility;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class MySQLConnectionManager {

    private MySQLConnectionManager() {}

    public static Connection connectToDatabase() throws ClassNotFoundException, SQLException{
        Connection connection;
        String host;
        String port;
        try {
            host = Utility.getValueFromProperties("host");
            port = Utility.getValueFromProperties("port");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(
                "jdbc:mysql://"+host+":"+port+"/fantasolver?characterEncoding=utf8","root","root");
        return connection;
    }
}
