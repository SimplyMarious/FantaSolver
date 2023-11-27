package com.spme.fantasolver.controllers;

import com.spme.fantasolver.annotations.Generated;
import com.spme.fantasolver.dao.DAOFactory;
import com.spme.fantasolver.entity.Player;
import com.spme.fantasolver.entity.Role;
import com.spme.fantasolver.entity.RoleException;
import com.spme.fantasolver.entity.Team;
import com.spme.fantasolver.ui.ManageTeamStage;
import com.spme.fantasolver.utility.Utility;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
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
    private static final short TEAM_MIN_SIZE = 25;
    private static final short TEAM_MAX_SIZE = 30;
    private static final short PLAYER_NAME_MIN_LENGTH = 2;
    private static final short PLAYER_NAME_MAX_LENGTH = 50;

    public void handleInitialization() {
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

    public void handleTeamPropertyChanged(String teamName, int playersSize) {
        try{
            manageTeamStage.setConfirmButtonAbility(
                    Utility.checkStringValidity(teamName, TEAM_NAME_MIN_LENGTH, TEAM_NAME_MAX_LENGTH) &&
                            TEAM_MIN_SIZE <= playersSize && playersSize <= TEAM_MAX_SIZE);
            if(playersSize == 0){
                manageTeamStage.setRemovePlayerButtonAbility(false);
            }
        }
        catch (IllegalArgumentException exception){
            Logger.getLogger("ManageTeamController").info(exception.getMessage());
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
            Player player = createPlayerFromInput(playerName, firstRole, secondRole, thirdRole);

            if(!manageTeamStage.getPlayers().contains(player)){
                manageTeamStage.addPlayerToTableView(player);
            }
            else{
                manageTeamStage.highlightPlayerInTableView(player);
            }
        }
        catch (RoleException exception){
            Logger.getLogger("ManageTeamController").info("Invalid roles: " + exception.getMessage());
        }
    }

    @Generated
    private Player createPlayerFromInput(String playerName, String firstRole, String secondRole, String thirdRole) throws RoleException {
        Player player = new Player(playerName);
        player.addRole(Role.valueOf(firstRole));
        if(!secondRole.equals("Nessuno")){
            player.addRole(Role.valueOf(secondRole));
        }
        if(!thirdRole.equals("Nessuno")){
            player.addRole(Role.valueOf(thirdRole));
        }
        return player;
    }

    public void handleSelectedPlayerFromTableView() {
        manageTeamStage.setRemovePlayerButtonAbility(true);
    }

    public void handlePressedRemovePlayerButton(Player player) {
        manageTeamStage.removePlayerFromTableView(player);
    }


    public void handlePressedConfirmButton(String teamName, List<Player> players) {
        Team team = new Team(teamName, new HashSet<>(players));
        boolean updateResult = DAOFactory.getTeamDAO().updateTeam(team, AuthenticationManager.getInstance().getUser());
        if(updateResult){
            AuthenticationManager.getInstance().getUser().setTeam(team);
            manageTeamStage.close();
        }
    }

    public void setManageTeamStage(ManageTeamStage manageTeamStage){
        this.manageTeamStage = manageTeamStage;
    }


}
