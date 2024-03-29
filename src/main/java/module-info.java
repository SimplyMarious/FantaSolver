module com.spme.fantasolver {
    requires javafx.controls;
    requires javafx.fxml;
            
        requires org.controlsfx.controls;
    requires java.sql;
    requires mysql.connector.java;

    opens com.spme.fantasolver to javafx.fxml;
    exports com.spme.fantasolver;
    exports com.spme.fantasolver.entity;
    opens com.spme.fantasolver.entity to javafx.fxml;
    exports com.spme.fantasolver.controllers;
    opens com.spme.fantasolver.controllers to javafx.fxml;
    exports com.spme.fantasolver.dao;
    opens com.spme.fantasolver.dao to javafx.fxml;
    exports com.spme.fantasolver.ui;
    opens com.spme.fantasolver.ui to javafx.fxml;
}