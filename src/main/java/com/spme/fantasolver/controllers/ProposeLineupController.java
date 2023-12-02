package com.spme.fantasolver.controllers;

import com.spme.fantasolver.annotations.Generated;
import com.spme.fantasolver.dao.DAOFactory;
import com.spme.fantasolver.entity.Formation;
import com.spme.fantasolver.entity.Player;
import com.spme.fantasolver.ui.ProposeLineupStage;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

public class ProposeLineupController {
    private static ProposeLineupController proposeLineupController = null;
    private ProposeLineupController(){}

    public static ProposeLineupController getInstance(){
        if(proposeLineupController == null){
            proposeLineupController = new ProposeLineupController();
        }
        return proposeLineupController;
    }

    private ProposeLineupStage proposeLineupStage;
    private static final int LINEUP_SIZE = 11;
    private Set<Formation> formations = new HashSet<>();

    public void handleInitialization() {
        try {
            initializeFormations();
            proposeLineupStage.initializeStage();
        } catch (IOException e) {
            Logger logger = Logger.getLogger("ProposeLineupController");
            logger.info("Error in reading FXML file: " + e.getMessage());
            throw new FXMLLoadException();
        }

        proposeLineupStage.show();
    }

    public void handleSelectedTableViewTeamPlayer(int playersSize) {
        if(playersSize < LINEUP_SIZE){
            proposeLineupStage.setAddPlayerToLineupButtonAbility(true);
        }
    }

    public void handlePressedAddPlayerToLineupButton(Player player) {
        if(!proposeLineupStage.getLineupPlayers().contains(player)){
            proposeLineupStage.addPlayerToLineupTableView(player);
        }
        else {
            proposeLineupStage.highlightPlayerInTeamTableView(player);
        }
    }

    public void handleSelectedTableViewLineupPlayer() {
        proposeLineupStage.setRemovePlayerFromLineupButtonAbility(true);
    }

    public void handlePressedRemovePlayerFromLineupButton(Player player) {
        proposeLineupStage.removePlayerFromLineupTableView(player);
    }

    public void handleLineUpTableViewChanged(int lineupSize) {
        if(lineupSize == LINEUP_SIZE){
            proposeLineupStage.setAddPlayerToLineupButtonAbility(false);
            proposeLineupStage.setVerifyLineupButtonAbility(true);
        }
        else if(lineupSize > 0){
            proposeLineupStage.setAddPlayerToLineupButtonAbility(true);
            proposeLineupStage.setVerifyLineupButtonAbility(false);
        }
        else{
            proposeLineupStage.setRemovePlayerFromLineupButtonAbility(false);
        }
    }

    public void handlePressedVerifyLineupButton() {
    }

    public void setProposeLineupStage(ProposeLineupStage proposeLineupStage) {
        this.proposeLineupStage = proposeLineupStage;
    }

    @Generated
    private void initializeFormations() {
        this.formations = DAOFactory.getFormationDAO().retrieveFormations();
    }
}
