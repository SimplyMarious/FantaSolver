package com.spme.fantasolver.controllers;

import com.spme.fantasolver.annotations.Generated;
import com.spme.fantasolver.dao.DAOFactory;
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

    public void handlePressedVerifyLineupButton(Set<Player> players) {
        Lineup lineup = getSuitableLineup(players);
        if(lineup != null){
            new VerifiedLineupStage(lineup);
        }
        else{
            Notifier.notifyInfo(
                    "Esito verifica", "Mi dispiace, non esiste un modulo adatto alla tua formazione... Dovevi pensarci all'asta!");
        }
    }

    //TODO: this method must be private
    @Generated
    public Lineup getSuitableLineup(Set<Player> players) {
        for(Formation formation: formations){
           List<Player> sortedPlayers = Player.sortPlayers(new ArrayList<>(players));
           Lineup lineup = getLineup(sortedPlayers, formation);

            if(lineup.checkValidity()){
                lineup.setFormation(formation);
                Logger logger = Logger.getLogger("ProposeLineupController");
                logger.info("Formation: " + lineup.getFormation().getName());

                return lineup;
            }
        }
        return null;
    }

    @Generated
    private Lineup getLineup(List<Player> players, Formation formation) {
        Lineup lineup = new Lineup();
        Slot[] sortedSlots = Slot.sortSlotsByRolesSize(formation.getSlots());

        setSuitableSlots(sortedSlots, players, lineup);

        return lineup;
    }

    @Generated
    private void setSuitableSlots(Slot[] slots, List<Player> players, Lineup lineup) {
        int i = 0;
        boolean isValidSlot = true;

        while(i < LINEUP_SIZE && isValidSlot) {
            setSuitablePlayers(players, slots[i], lineup);

            if (lineup.getPlayers()[slots[i].getId()] == null) {
                isValidSlot = false;
            }

            i++;
        }
    }

    @Generated
    private void setSuitablePlayers(List<Player> players, Slot slot, Lineup lineup) {
        Iterator<Player> playersIterator = players.iterator();
        boolean playerFound = false;

        while (playersIterator.hasNext() && !playerFound) {
            Player player = playersIterator.next();
            Set<Role> commonRoles = new HashSet<>(slot.getRoles());
            commonRoles.retainAll(player.getRoles());

            if (!commonRoles.isEmpty()) {
                lineup.setPlayer(player, slot);
                playersIterator.remove();
                playerFound = true;
            }
        }
    }

    @Generated
    public void initializeFormations() {
        if (this.formations.isEmpty()) {
            this.formations = DAOFactory.getFormationDAO().retrieveFormations();
        }
    }

    @Generated
    public void setProposeLineupStage(ProposeLineupStage proposeLineupStage) {
        this.proposeLineupStage = proposeLineupStage;
    }
}
