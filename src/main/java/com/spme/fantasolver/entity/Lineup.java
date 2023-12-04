package com.spme.fantasolver.entity;

import com.spme.fantasolver.annotations.Generated;

@Generated
public class Lineup {
    private Formation formation;
    private Player[] players;

    public Lineup(){
        this.players = new Player[11];
    }


    public Formation getFormation() {
        return formation;
    }

    public void setFormation(Formation formation) {
        this.formation = formation;
    }

    public Player[] getPlayers() {
        return players;
    }
    public void setPlayers(Player[] players) {
        this.players = players;
    }

    public void setPlayer(Player player, Slot slot) {
        players[slot.getId()] = player;
    }

    public boolean checkValidity(){
        for(short i = 0; i < 11; i++){
            if(players[i] == null){
                return false;
            }
        }
        return true;
    }
}
