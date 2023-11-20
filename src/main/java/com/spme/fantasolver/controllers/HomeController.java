package com.spme.fantasolver.controllers;

import com.spme.fantasolver.ui.HomeStage;

import java.io.IOException;

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

    public void handleInitialization(HomeStage homeStage, boolean doesTeamExist){
        this.homeStage = homeStage;
        this.doesTeamExist = doesTeamExist;

        try {
            homeStage.initializeStage();
        } catch (IOException e) {
            System.err.println("Error in reading FXML file.");
            e.printStackTrace();
            System.exit(1);
        }
    }

}
