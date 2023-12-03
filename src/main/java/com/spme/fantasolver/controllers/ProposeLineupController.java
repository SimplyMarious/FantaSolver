package com.spme.fantasolver.controllers;

import com.spme.fantasolver.annotations.Generated;
import com.spme.fantasolver.dao.DAOFactory;
import com.spme.fantasolver.entity.*;
import com.spme.fantasolver.ui.ProposeLineupStage;
import com.spme.fantasolver.utility.Notifier;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

    public void handlePressedVerifyLineupButton(Set<Player> players) {
        Lineup lineup = getSuitableLineup(players);
        if(lineup != null){
            Notifier.notifyInfo(
                    "Esito verifica", "Bravo! Esiste un modulo. E' il " + lineup.getFormation().getName());
        }
        else{
            Notifier.notifyInfo(
                    "Esito verifica", "Mi dispiace, non esiste un modulo adatto alla tua formazione... Dovevi pensarci all'asta!");
        }
    }

    @Generated
    private Lineup getSuitableLineup(Set<Player> players) {
        for(Formation formation: formations){
            System.out.println(formation.getName());
            Set<Player> currentPlayers = new HashSet<>(players);
            Lineup lineup = new Lineup();

            List<Player> sortedPlayers = Player.sortPlayers(new ArrayList<>(currentPlayers));
            Slot[] sortedSlots = Slot.sortSlotsByRolesSize(formation.getSlots());

            for(Slot slot: sortedSlots){
                System.out.println("Slot: " + slot.getId());
                for(Player player: sortedPlayers){
                    System.out.println("Provo " + player.getName());
                    Set<Role> commonRoles = new HashSet<>(slot.getRoles());
                    commonRoles.retainAll(player.getRoles());
                    if(!commonRoles.isEmpty()){
                        System.out.println(player.getName() + " va in Slot " + slot.getId() +
                                " con ruoli: ");
                        for(Role role: commonRoles){
                            System.out.println(role);
                        }
                        lineup.setPlayer(player, slot);
                        sortedPlayers.remove(player);
                        break;
                    }
                }
                if(lineup.getPlayers()[slot.getId()] == null){
                    System.out.println("Slot " + slot.getId() + " nullo");
                    break;
                }
            }
            if(lineup.checkValidity()){
                System.out.println("Modulo trovato: " + formation.getName());
                lineup.setFormation(formation);
                return lineup;
            }
        }
        return null;
    }



    @Generated
    private void initializeFormations() {
        this.formations = DAOFactory.getFormationDAO().retrieveFormations();

        for(Formation formation: formations){
            System.out.println("Formation: " + formation.getName());
            Slot[] sortedSlots = Slot.sortSlotsByRolesSize(formation.getSlots());
            for(Slot sortedSlot: sortedSlots){
                System.out.println("Id: " + sortedSlot.getId());
                System.out.println("Roles: " + sortedSlot.getRoles());
            }
        }
    }

    @Generated
    public void setProposeLineupStage(ProposeLineupStage proposeLineupStage) {
        this.proposeLineupStage = proposeLineupStage;
    }
}
