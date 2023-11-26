package com.spme.fantasolver.entity;

import java.util.Set;

public class Team {
    private String name;
    private Set<Player> players;

    public Team(String name, Set<Player> players){
        this.name = name;
        this.players = players;
    }

    public String getName() {
        return name;
    }

    public Set<Player> getPlayers() {
        return players;
    }

    public void setPlayers(Set<Player> players) {
        this.players = players;
    }
}
