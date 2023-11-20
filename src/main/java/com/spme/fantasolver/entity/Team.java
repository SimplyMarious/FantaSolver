package com.spme.fantasolver.entity;

import java.util.Set;

public class Team {
    private String name;
    private Set<Player> players;

    public String getName() {
        return name;
    }

    public void setPlayers(Set<Player> players) {
        this.players = players;
    }
}
