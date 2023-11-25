package com.spme.fantasolver.controllers;

import com.spme.fantasolver.entity.Player;
import com.spme.fantasolver.entity.Role;
import com.spme.fantasolver.entity.RoleException;
import com.spme.fantasolver.entity.Team;
import com.spme.fantasolver.ui.ManageTeamStage;
import com.spme.fantasolver.utility.Utility;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
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
    private static final short PLAYER_NAME_MIN_LENGTH = 2;
    private static final short PLAYER_NAME_MAX_LENGTH = 50;
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
            manageTeamStage.setTextFieldTeamName(team.getName());
            manageTeamStage.loadPlayersInTable(team.getPlayers());
        }

        manageTeamStage.show();
    }

    public void handleTextFieldTeamNameChanged(String teamName) {
        int playersSize = manageTeamStage.getPlayersSize();
        if(Utility.checkStringValidity(teamName, TEAM_NAME_MIN_LENGTH, TEAM_NAME_MAX_LENGTH) &&
            25 <= playersSize && playersSize <= 30){
            manageTeamStage.enableConfirmButton();
        }
    }

    public void handlePlayerPropertyChanged(String playerName, String firstRole, String secondRole, String thirdRole) {
        boolean isPlayerNameValid = Utility.checkStringValidity(playerName, PLAYER_NAME_MIN_LENGTH, PLAYER_NAME_MAX_LENGTH);
        boolean isFirstRoleValid = !firstRole.equals("Nessuno");
        boolean areSecondAndThirdRolesValid =
                (secondRole.equals("Nessuno") && thirdRole.equals("Nessuno")) ||
                Utility.areStringsDifferentFromEachOther(List.of(firstRole, secondRole, thirdRole));

        manageTeamStage.setAddPlayerButtonAbility(isPlayerNameValid && isFirstRoleValid && areSecondAndThirdRolesValid);
    }

    public void handlePressedAddPlayerButton(String playerName, String firstRole, String secondRole, String thirdRole) {
        try{
            Player player = new Player(playerName);
            player.addRole(Role.valueOf(firstRole));
            if(!secondRole.equals("Nessuno")){
                player.addRole(Role.valueOf(secondRole));
            }
            if(!thirdRole.equals("Nessuno")){
                player.addRole(Role.valueOf(thirdRole));
            }

            if(!manageTeamStage.getPlayers().contains(player)){
                manageTeamStage.addPlayerToTableView(player);
            }
            else{
                manageTeamStage.highlightPlayerInTableView(player);
            }

        }
        catch (RoleException roleException){
            System.err.println("Ruoli non validi, riprovare!");
        }
    }

    public void handlePressedConfirmButton() {
        //TODO:connect to database and save the team; should I pass as parameter Set<Player> players?
    }
}
