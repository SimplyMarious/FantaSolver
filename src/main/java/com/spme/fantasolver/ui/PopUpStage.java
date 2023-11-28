package com.spme.fantasolver.ui;

import com.spme.fantasolver.Application;
import com.spme.fantasolver.controllers.FXMLLoadException;
import com.spme.fantasolver.controllers.PopUpController;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Logger;


public class PopUpStage {
    private PopUpController popUpController;
    private FXMLLoader fxmlLoader;
    private Stage stage;

    private Label labelErrorMessage;
    private Button buttonOK;


    public PopUpStage(String message){
        popUpController = PopUpController.getInstance();
        popUpController.setPopUpStage(this);
        popUpController.handleInitialization();
        initializeUIComponents(message);
    }

    public void initializeStage() throws IOException {
        fxmlLoader = new FXMLLoader(Application.class.getResource("popup-stage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 490, 176);
        stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Errore");
    }

    private void initializeUIComponents(String message) {
        labelErrorMessage = (Label)fxmlLoader.getNamespace().get("labelErrorMessage");
        labelErrorMessage.setText(message);

        buttonOK = (Button) fxmlLoader.getNamespace().get("buttonOK");
        buttonOK.setOnAction(actionEvent -> popUpController.handlePressedOKButton());
    }

    public void show() {
        stage.show();
    }
}
