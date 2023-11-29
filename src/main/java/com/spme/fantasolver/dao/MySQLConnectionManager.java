package com.spme.fantasolver.dao;

import com.spme.fantasolver.utility.Notifier;
import com.spme.fantasolver.utility.Utility;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

public class MySQLConnectionManager {

    private MySQLConnectionManager() {}

    public static Connection connectToDatabase() throws ClassNotFoundException, SQLException {
        try {
            return tryConnectToDatabase();
        } catch (IOException e) {
            Logger logger = Logger.getLogger("MySQLConnectionManager");
            logger.info("Error while connection: " + e.getMessage());
            Notifier.notifyError("Errore", "Connesione al database fallita.");
            return null;
        }
    }

    private static Connection tryConnectToDatabase() throws IOException, ClassNotFoundException, SQLException {
        Connection connection;
        String host;
        String port;
        host = Utility.getValueFromProperties("host");
        port = Utility.getValueFromProperties("port");
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(
                "jdbc:mysql://"+host+":"+port+"/fantasolver?allowMultiQueries=true&characterEncoding=utf8",
                "root","root");
        return connection;
    }
}
