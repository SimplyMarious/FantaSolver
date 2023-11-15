package com.spme.fantasolver.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.sql.Connection;
import java.sql.DriverManager;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        connectToDatabase();
        welcomeText.setText("Ho provato!!!!!!!!!!!!!");
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