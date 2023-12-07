package com.spme.fantasolver.utility;

import com.spme.fantasolver.annotations.Generated;
import javafx.scene.control.Alert;

@Generated
public class Notifier {
    private Notifier(){}

    public static void notifyInfo(String title, String message){
        notify(title, message, Alert.AlertType.INFORMATION);

    }

    public static void notifyError(String title, String message){
        notify(title, message, Alert.AlertType.ERROR);
    }

    private static void notify(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
