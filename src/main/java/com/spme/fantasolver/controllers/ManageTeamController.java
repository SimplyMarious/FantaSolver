package com.spme.fantasolver.controllers;

import com.spme.fantasolver.ui.ManageTeamStage;
import com.spme.fantasolver.utility.Utility;

import java.io.IOException;
import java.util.logging.Logger;

public class ManageTeamController {
    private static ManageTeamController manageTeamController = null;
    private ManageTeamController(){}

    public static ManageTeamController getInstance(){
        if(manageTeamController == null){
            manageTeamController = new ManageTeamController();
        }
        return manageTeamController;
    }

    private ManageTeamStage manageTeamStage;
    private static final short TEAM_NAME_MIN_LENGTH = 3;
    private static final short TEAM_NAME_MAX_LENGTH = 50;

    public void handleInitialization(ManageTeamStage manageTeamStage) {
        this.manageTeamStage = manageTeamStage;

        try {
            manageTeamStage.initializeStage();
        } catch (IOException e) {
            Logger logger = Logger.getLogger("ManageTeamController");
            logger.info("Error in reading FXML file: " + e.getMessage());
            throw new FXMLLoadException();
        }

        if(AuthenticationManager.getInstance().getUser().getTeam() != null){
//            manageTeamStage.loadTeamInTable();
        }

        manageTeamStage.show();
    }

    public void handleTextFieldTeamNameChanged(String teamName) {
//        if(Utility.checkStringValidity(teamName, TEAM_NAME_MIN_LENGTH, TEAM_NAME_MAX_LENGTH) &&
//            25 <= manageTeamStage.getTeamSize() <= 30){
//            manageTeamStage.enableConfirmButton();
//        }
    }
}
