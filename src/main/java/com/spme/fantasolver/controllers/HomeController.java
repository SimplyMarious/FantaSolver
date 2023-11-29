package com.spme.fantasolver.controllers;

import com.spme.fantasolver.ui.HomeStage;
import com.spme.fantasolver.ui.ManageTeamStage;
import com.spme.fantasolver.ui.ProposeLineupStage;
import com.spme.fantasolver.ui.SignInStage;

import java.io.IOException;
import java.util.logging.Logger;

public class HomeController {

    private static HomeController homeController = null;
    private HomeController(){}

    public static HomeController getInstance(){
        if(homeController == null){
            homeController = new HomeController();
        }
        return homeController;
    }

    private HomeStage homeStage;
    private boolean doesTeamExist;

    public void handleInitialization(boolean doesTeamExist){
        this.doesTeamExist = doesTeamExist;

        try {
            homeStage.initializeStage();
        } catch (IOException e) {
            Logger logger = Logger.getLogger("HomeController");
            logger.info("Error in reading FXML file: " + e.getMessage());
            throw new FXMLLoadException();
        }

        if(doesTeamExist){
            homeStage.setManageTeamScreenVisible();
        }
        else {
            homeStage.setAddTeamScreenVisible();
        }

        homeStage.show();
    }

    public void handlePressedManageTeamButton() {
        new ManageTeamStage();
    }

    public void handlePressedProposeLineupButton() {
        new ProposeLineupStage(AuthenticationManager.getInstance().getUser().getTeam());
    }

    public void handlePressedSignOutButton() {
        AuthenticationManager.getInstance().signOut();
        new SignInStage();
    }

    public void setHomeStage(HomeStage homeStage) {
        this.homeStage = homeStage;
    }
}
