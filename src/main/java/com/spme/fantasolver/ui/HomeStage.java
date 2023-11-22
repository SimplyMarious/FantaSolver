package com.spme.fantasolver.ui;

import com.spme.fantasolver.Application;
import com.spme.fantasolver.controllers.HomeController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class HomeStage {
    private final HomeController homeController;

    private VBox vBoxAddTeam;
    private VBox vBoxManageTeam;
    private Button buttonAddTeam;

    public HomeStage(boolean doesTeamExist) {
        this.homeController = HomeController.getInstance();
        homeController.handleInitialization(this, doesTeamExist);
    }

    public void initializeStage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("home-stage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        Application.getStage().setScene(scene);
        Application.getStage().setTitle("FantaSolver - Home");

        vBoxAddTeam = (VBox)fxmlLoader.getNamespace().get("vBoxAddTeam");
        vBoxManageTeam = (VBox)fxmlLoader.getNamespace().get("vBoxManageTeam");

        buttonAddTeam = (Button)fxmlLoader.getNamespace().get("buttonAddTeam");
        buttonAddTeam.setOnMouseClicked(mouseEvent -> buttonAddTeam.setVisible(false));
    }

    public void setAddTeamScreenVisible() {
        vBoxAddTeam.setVisible(true);
        vBoxManageTeam.setVisible(false);
    }

    public void setManageTeamScreenVisible() {
        vBoxManageTeam.setVisible(true);
        vBoxAddTeam.setVisible(false);
    }

    public void show() {
        Application.getStage().show();
    }
}
