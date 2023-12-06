package com.spme.fantasolver.controllers;

import com.spme.fantasolver.annotations.Generated;
import com.spme.fantasolver.entity.*;
import com.spme.fantasolver.ui.VerifiedLineupStage;

import java.io.IOException;
import java.util.Set;
import java.util.logging.Logger;

public class VerifiedLineupController {

    private static VerifiedLineupController verifiedLineupController = null;

    @Generated
    private VerifiedLineupController(){}

    public static VerifiedLineupController getInstance(){
        if(verifiedLineupController == null){
            verifiedLineupController = new VerifiedLineupController();
        }
        return verifiedLineupController;
    }

    private VerifiedLineupStage verifiedLineupStage;

    @Generated
    public void handleInitialization(Lineup lineup) {
        try {
            verifiedLineupStage.initializeStage();
            verifiedLineupStage.setLineupFormationLabelText("Il tuo modulo: " + lineup.getFormation().getName());

            Lineup adaptedLineup = adaptLineupForVisualization(lineup);
            verifiedLineupStage.loadPlayersInTable(adaptedLineup.getPlayers());
            verifiedLineupStage.show();
        } catch (IOException e) {
            Logger logger = Logger.getLogger("VerifiedLineupController");
            logger.info("Error in reading FXML file: " + e.getMessage());
            throw new FXMLLoadException();
        }
    }

    @Generated
    private Lineup adaptLineupForVisualization(Lineup lineup) {
        Lineup adpatedLineup = new Lineup();
        adpatedLineup.setFormation(lineup.getFormation());
        adpatedLineup.setPlayers(getPlayersWithRolesFittingToOwnSlot(lineup.getPlayers(), lineup.getFormation().getSlots()));

        return adpatedLineup;
    }

    @Generated
    private static Player[] getPlayersWithRolesFittingToOwnSlot(Player[] players, Slot[] slots) {
        for(int i = 0; i < 11; i++){
            Set<Role> playerRoles = players[i].getRoles();
            playerRoles.retainAll(slots[i].getRoles());
        }
        return players;
    }

    @Generated
    public void setVerifiedLineupStage(VerifiedLineupStage verifiedLineupStage) {
        this.verifiedLineupStage = verifiedLineupStage;
    }
}
