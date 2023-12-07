package com.spme.fantasolver.controllers;

import com.spme.fantasolver.annotations.Generated;
import com.spme.fantasolver.entity.Team;
import com.spme.fantasolver.entity.UserObserver;
import com.spme.fantasolver.ui.*;

import java.io.IOException;
import java.util.logging.Logger;

public class HomeController implements UserObserver {

    private static HomeController homeController = null;
    private HomeStage homeStage;
    private StageFactory stageFactory;

    private HomeController(){}

    public static HomeController getInstance(){
        if(homeController == null){
            homeController = new HomeController();
        }
        return homeController;
    }

    @Generated
    public void setStageFactory(StageFactory factory){
        this.stageFactory = factory;
    }

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
        stageFactory.createManageStage();
    }

    @Generated
    public void handlePressedProposeLineupButton() {
        Team team = AuthenticationManager.getInstance().getUser().getTeam();
        stageFactory.createProposeLineupStage(team);
    }

    public void handlePressedSignOutButton() {
        AuthenticationManager.getInstance().signOut();
        stageFactory.createSignInStage();
    }

    public void setHomeStage(HomeStage homeStage) {
        this.homeStage = homeStage;
    }

    @Override
    public void onTeamChanged() {
        homeStage.setManageTeamScreenVisible();
    }
}
