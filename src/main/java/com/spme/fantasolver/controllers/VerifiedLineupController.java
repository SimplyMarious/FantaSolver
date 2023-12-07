package com.spme.fantasolver.controllers;

import com.spme.fantasolver.annotations.Generated;
import com.spme.fantasolver.entity.*;
import com.spme.fantasolver.ui.VerifiedLineupStage;

import java.io.IOException;
import java.util.HashSet;
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
    private static final int LINEUP_SIZE = 11;

    public void handleInitialization(Lineup lineup) {
        try {
            tryHandleInitialization(lineup);
        } catch (IOException e) {
            Logger logger = Logger.getLogger("VerifiedLineupController");
            logger.info("Error in reading FXML file: " + e.getMessage());
            throw new FXMLLoadException();
        }
    }

    @Generated
    private void tryHandleInitialization(Lineup lineup) throws IOException {
        verifiedLineupStage.initializeStage();
        verifiedLineupStage.setLineupFormationLabelText("Il tuo modulo: " + lineup.getFormation().getName());

        Lineup adaptedLineup = adaptLineupForVisualization(lineup);
        verifiedLineupStage.loadPlayersInTable(adaptedLineup.getPlayers());
        verifiedLineupStage.show();
    }

    @Generated
    private Lineup adaptLineupForVisualization(Lineup lineup) {
        Lineup adpatedLineup = new Lineup();
        adpatedLineup.setFormation(lineup.getFormation());
        Player[] adaptedPlayers = copyPlayersFromLineup(lineup.getPlayers());
        setPlayerRolesAsFittingToOwnSlot(adaptedPlayers, lineup.getFormation().getSlots());
        adpatedLineup.setPlayers(adaptedPlayers);

        return adpatedLineup;
    }

    @Generated
    private static Player[] copyPlayersFromLineup(Player[] lineupPlayers) {
        Player[] players = new Player[lineupPlayers.length];
        int i = 0;
        for(Player player: lineupPlayers){
            players[i] = new Player(player.getName(), player.getRoles());
        }
        return players;
    }

    @Generated
    private void setPlayerRolesAsFittingToOwnSlot(Player[] players, Slot[] slots) {
        if (players.length == LINEUP_SIZE && slots.length == LINEUP_SIZE) {
            for (int i = 0; i < LINEUP_SIZE; i++) {
                Set<Role> playerRoles = players[i].getRoles();
                playerRoles.retainAll(slots[i].getRoles());
            }
        }
    }

    @Generated
    public void setVerifiedLineupStage(VerifiedLineupStage verifiedLineupStage) {
        this.verifiedLineupStage = verifiedLineupStage;
    }
}
