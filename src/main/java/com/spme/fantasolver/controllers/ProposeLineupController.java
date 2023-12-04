package com.spme.fantasolver.controllers;

import com.spme.fantasolver.annotations.Generated;
import com.spme.fantasolver.entity.*;
import com.spme.fantasolver.ui.ProposeLineupStage;
import com.spme.fantasolver.ui.VerifiedLineupStage;
import com.spme.fantasolver.utility.Notifier;

import java.io.IOException;
import java.util.*;
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

    public void handleInitialization() {
        try {
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

    public void handlePressedVerifyLineupButton(Set<Player> players) {
        Lineup lineup = LineupVerifier.getInstance().getSuitableLineup(players);
        if(lineup != null){
            new VerifiedLineupStage(lineup);
        }
        else{
            Notifier.notifyInfo(
                    "Esito verifica", "Mi dispiace, non esiste un modulo adatto alla tua formazione... Dovevi pensarci all'asta!");
        }
    }

    @Generated
    public void setProposeLineupStage(ProposeLineupStage proposeLineupStage) {
        this.proposeLineupStage = proposeLineupStage;
    }
}
