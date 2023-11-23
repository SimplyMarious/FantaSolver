package com.spme.fantasolver.controllers;

import com.spme.fantasolver.entity.Player;
import com.spme.fantasolver.entity.Role;
import com.spme.fantasolver.entity.Team;
import com.spme.fantasolver.ui.ManageTeamStage;
import com.spme.fantasolver.utility.Utility;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
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

        Team team = AuthenticationManager.getInstance().getUser().getTeam();
        if(team != null){
            manageTeamStage.loadPlayersInTable(team.getPlayers());
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
