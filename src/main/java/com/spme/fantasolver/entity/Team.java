package com.spme.fantasolver.entity;

import com.spme.fantasolver.annotations.Generated;

import java.util.Set;

@Generated
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
