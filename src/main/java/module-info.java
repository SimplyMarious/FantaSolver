module com.spme.fantasolver {
    requires javafx.controls;
    requires javafx.fxml;
            
        requires org.controlsfx.controls;
    requires java.sql;

    opens com.spme.fantasolver to javafx.fxml;
    exports com.spme.fantasolver;
}