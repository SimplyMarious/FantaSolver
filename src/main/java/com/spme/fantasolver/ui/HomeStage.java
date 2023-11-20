package com.spme.fantasolver.ui;

import com.spme.fantasolver.Application;
import com.spme.fantasolver.controllers.HomeController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;

public class HomeStage {
    private final HomeController homeController;
    public HomeStage(boolean doesTeamExist) {
        this.homeController = HomeController.getInstance();
        homeController.handleInitialization(this, doesTeamExist);
    }

    public void initializeStage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("home-stage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);

        Application.getStage().setTitle("FantaSolver - Home");
        Application.getStage().setScene(scene);
        Application.getStage().show();
    }
}
