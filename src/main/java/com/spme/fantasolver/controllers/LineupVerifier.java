package com.spme.fantasolver.controllers;

import com.spme.fantasolver.annotations.Generated;
import com.spme.fantasolver.dao.DAOFactory;
import com.spme.fantasolver.entity.*;

import java.util.*;
import java.util.logging.Logger;

public class LineupVerifier {

    private static LineupVerifier lineupVerifier = null;

    private LineupVerifier(){
        initializeFormations();
    }

    public static LineupVerifier getInstance() {
        if (lineupVerifier == null){
            lineupVerifier = new LineupVerifier();
        }
        return lineupVerifier;
    }

    private static final int LINEUP_SIZE = 11;
    private Set<Formation> formations = new HashSet<>();

    public Lineup getSuitableLineup(Set<Player> players) {
        for(Formation formation: formations){
            List<Player> sortedPlayers = Player.sortPlayers(new ArrayList<>(players));
            Lineup lineup = getLineup(sortedPlayers, formation);

            if(lineup.checkValidity()){
                lineup.setFormation(formation);
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
    private void initializeFormations() {
        if (this.formations.isEmpty()) {
            this.formations = DAOFactory.getFormationDAO().retrieveFormations();
        }
    }

    @Generated
    public void setFormations(Set<Formation> formations) {
        this.formations = formations;
    }
}
