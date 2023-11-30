package com.spme.fantasolver.ui;

import com.spme.fantasolver.FantaSolver;
import com.spme.fantasolver.annotations.Generated;
import com.spme.fantasolver.controllers.AuthenticationManager;
import com.spme.fantasolver.controllers.HomeController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class HomeStage {
    private final HomeController homeController;

    private Label labelWelcomeUser;
    private VBox vBoxAddTeam;
    private VBox vBoxManageTeam;
    private Button buttonAddTeam;
    private Button buttonManageTeam;
    private Button buttonProposeLineup;
    private Button buttonSignOut;

    public HomeStage(boolean doesTeamExist) {
        this.homeController = HomeController.getInstance();
        homeController.setHomeStage(this);
        homeController.handleInitialization(doesTeamExist);
    }

    public void initializeStage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(FantaSolver.class.getResource("home-stage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        FantaSolver.getStage().setScene(scene);
        FantaSolver.getStage().setTitle("FantaSolver - Home");

        initializeUIComponents(fxmlLoader);
    }

    @Generated
    private void initializeUIComponents(FXMLLoader fxmlLoader) {
        labelWelcomeUser = (Label) fxmlLoader.getNamespace().get("labelWelcomeUser");
        labelWelcomeUser.setText("Benvenuto, " + AuthenticationManager.getInstance().getUser().getUsername() + "!");

        vBoxAddTeam = (VBox) fxmlLoader.getNamespace().get("vBoxAddTeam");
        vBoxManageTeam = (VBox) fxmlLoader.getNamespace().get("vBoxManageTeam");

        buttonAddTeam = (Button) fxmlLoader.getNamespace().get("buttonAddTeam");
        buttonAddTeam.setOnAction(actionEvent -> homeController.handlePressedManageTeamButton());

        buttonManageTeam = (Button) fxmlLoader.getNamespace().get("buttonManageTeam");
        buttonManageTeam.setOnAction(actionEvent -> homeController.handlePressedManageTeamButton());

        buttonProposeLineup = (Button) fxmlLoader.getNamespace().get("buttonProposeLineup");
        buttonProposeLineup.setOnAction(actionEvent -> homeController.handlePressedProposeLineupButton());

        buttonSignOut = (Button) fxmlLoader.getNamespace().get("buttonSignOut");
        buttonSignOut.setOnAction(actionEvent -> homeController.handlePressedSignOutButton());

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
        FantaSolver.getStage().show();
    }
}
