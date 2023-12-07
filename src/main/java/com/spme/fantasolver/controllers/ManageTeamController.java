package com.spme.fantasolver.controllers;

import com.spme.fantasolver.annotations.Generated;
import com.spme.fantasolver.dao.DAOFactory;
import com.spme.fantasolver.entity.*;
import com.spme.fantasolver.ui.AbstractManageTeamStage;
import com.spme.fantasolver.utility.Notifier;
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

    private static final String CLASS_NAME = "ManageTeamController";

    private AbstractManageTeamStage manageTeamStage;
    private static final short TEAM_NAME_MIN_LENGTH = 3;
    private static final short TEAM_NAME_MAX_LENGTH = 50;
    private static final short TEAM_MIN_SIZE = 25;
    private static final short TEAM_MAX_SIZE = 30;
    private static final short PLAYER_NAME_MIN_LENGTH = 2;
    private static final short PLAYER_NAME_MAX_LENGTH = 50;
    private static final String NO_ROLE_STRING = "Nessuno";

    public void handleInitialization() {
        try {
            tryHandleInitialization();
        } catch (IOException e) {
            Logger logger = Logger.getLogger(CLASS_NAME);
            logger.info("Error in reading FXML file: " + e.getMessage());
            throw new FXMLLoadException();
        }
    }

    @Generated
    private void tryHandleInitialization() throws IOException {
        manageTeamStage.initializeStage();
        Team team = AuthenticationManager.getInstance().getUser().getTeam();
        if(team != null){
            manageTeamStage.setTextFieldTeamName(team.getName());
            manageTeamStage.loadPlayersInTable(team.getPlayers());
        }
        manageTeamStage.show();
    }

    public void handleTeamPropertyChanged(String teamName, int playersSize) {
        try{
            tryHandleTeamPropertyChanged(teamName, playersSize);
        }
        catch (IllegalArgumentException exception){
            Logger.getLogger(CLASS_NAME).info(exception.getMessage());
        }
    }

    @Generated
    private void tryHandleTeamPropertyChanged(String teamName, int playersSize) {
        manageTeamStage.setConfirmButtonAbility(
                Utility.checkStringValidity(teamName, TEAM_NAME_MIN_LENGTH, TEAM_NAME_MAX_LENGTH) &&
                        TEAM_MIN_SIZE <= playersSize && playersSize < TEAM_MAX_SIZE);
        if(playersSize == 0){
            manageTeamStage.setRemovePlayerButtonAbility(false);
        }
    }

    public void handlePlayerPropertyChanged(String playerName, String firstRole, String secondRole, String thirdRole) {
        boolean isPlayerNameValid = Utility.checkStringValidity(playerName, PLAYER_NAME_MIN_LENGTH, PLAYER_NAME_MAX_LENGTH);
        boolean isFirstRoleValid = !firstRole.equals(NO_ROLE_STRING);
        boolean areSecondAndThirdRolesValid =
                (secondRole.equals(NO_ROLE_STRING) && thirdRole.equals(NO_ROLE_STRING)) ||
                Utility.areStringsDifferentFromEachOther(List.of(firstRole, secondRole, thirdRole));

        manageTeamStage.setAddPlayerButtonAbility(isPlayerNameValid && isFirstRoleValid && areSecondAndThirdRolesValid);
    }

    public void handlePressedAddPlayerButton(String playerName, String firstRole, String secondRole, String thirdRole) {
        try{
            tryHandlePressedAddPlayerButton(playerName, firstRole, secondRole, thirdRole);
        }
        catch (RoleException exception){
            Logger.getLogger(CLASS_NAME).info("Invalid roles: " + exception.getMessage());
        }
    }

    @Generated
    private void tryHandlePressedAddPlayerButton(String playerName, String firstRole, String secondRole, String thirdRole) throws RoleException {
        Player player = createPlayerFromInput(playerName, firstRole, secondRole, thirdRole);

        if(!manageTeamStage.getPlayers().contains(player)){
            manageTeamStage.addPlayerToTableView(player);
        }
        else{
            manageTeamStage.highlightPlayerInTableView(player);
        }
    }

    @Generated
    private Player createPlayerFromInput(String playerName, String firstRole, String secondRole, String thirdRole) throws RoleException {
        Player player = new Player(playerName);
        player.addRole(Role.valueOf(firstRole));
        if(!secondRole.equals(NO_ROLE_STRING)){
            player.addRole(Role.valueOf(secondRole));
        }
        if(!thirdRole.equals(NO_ROLE_STRING)){
            player.addRole(Role.valueOf(thirdRole));
        }
        return player;
    }

    @Generated
    public void handleSelectedPlayerFromTableView() {
        manageTeamStage.setRemovePlayerButtonAbility(true);
    }

    @Generated
    public void handlePressedRemovePlayerButton(Player player) {
        manageTeamStage.removePlayerFromTableView(player);
    }

    public void handlePressedConfirmButton(String teamName, List<Player> players) {
        Team team = new Team(teamName, new HashSet<>(players));
        User user = AuthenticationManager.getInstance().getUser();

        boolean updateResult = DAOFactory.getTeamDAO().updateTeam(team, user);
        if(updateResult){
            user.setTeam(team);
            Notifier.notifyInfo("Rosa salvata",
                    "Rosa salvata correttamente! L'asta sembra essere andata bene, vedo...");
        }
        else{
            Notifier.notifyError("Errore", "Errore imprevisto");
        }
    }

    public void setManageTeamStage(AbstractManageTeamStage manageTeamStage){
        this.manageTeamStage = manageTeamStage;
    }
}
