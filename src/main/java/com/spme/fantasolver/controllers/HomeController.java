package com.spme.fantasolver.controllers;

import com.spme.fantasolver.annotations.Generated;
import com.spme.fantasolver.entity.User;
import com.spme.fantasolver.entity.UserObserver;
import com.spme.fantasolver.ui.HomeStage;
import com.spme.fantasolver.ui.ManageTeamStage;
import com.spme.fantasolver.ui.ProposeLineupStage;
import com.spme.fantasolver.ui.SignInStage;

import java.io.IOException;
import java.util.logging.Logger;

public class HomeController implements UserObserver {

    private static HomeController homeController = null;
    private HomeController(){}

    public static HomeController getInstance(){
        if(homeController == null){
            homeController = new HomeController();
        }
        return homeController;
    }

    private HomeStage homeStage;

    public void handleInitialization(){
        try {
            tryHandleInitialization();
        } catch (IOException e) {
            Logger logger = Logger.getLogger("HomeController");
            logger.info("Error in reading FXML file: " + e.getMessage());
            throw new FXMLLoadException();
        }
    }

    @Generated
    private void tryHandleInitialization() throws IOException {
        AuthenticationManager.getInstance().getUser().addObserver(this);
        homeStage.initializeStage();
        if(AuthenticationManager.getInstance().getUser().getTeam() != null){
            homeStage.setManageTeamScreenVisible();
        }
        else {
            homeStage.setAddTeamScreenVisible();
        }
        homeStage.show();
    }

    @Generated
    public void handlePressedManageTeamButton() {
        new ManageTeamStage();
    }

    @Generated
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

    @Override
    public void onTeamChanged() {
        homeStage.setManageTeamScreenVisible();
    }
}
