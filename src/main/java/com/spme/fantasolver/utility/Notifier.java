package com.spme.fantasolver.utility;

import com.spme.fantasolver.annotations.Generated;
import javafx.scene.control.Alert;

@Generated
public class Notifier {
    private Notifier(){}

    public static void notifyInfo(String title, String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void notifyError(String title, String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
