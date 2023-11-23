package com.spme.fantasolver.ui;

import com.spme.fantasolver.Application;
import com.spme.fantasolver.controllers.ManageTeamController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class ManageTeamStage {
    private Stage stage;
    private ManageTeamController manageTeamController;

    private TextField textFieldTeamName;

    public ManageTeamStage(){
        this.manageTeamController = ManageTeamController.getInstance();
        manageTeamController.handleInitialization(this);
    }

    public void initializeStage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("manage-team-stage.fxml"));
        stage = new Stage();
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setScene(scene);
        stage.setTitle("FantaSolver - Gestisci la tua Rosa");

        textFieldTeamName = (TextField)fxmlLoader.getNamespace().get("textFieldTeamName");
        textFieldTeamName.setOnKeyTyped(keyEvent ->
                manageTeamController.handleTextFieldTeamNameChanged(textFieldTeamName.getText()));
    }

    public void show(){
        stage.show();
    }
}
