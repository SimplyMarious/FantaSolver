package com.spme.fantasolver.entity;

import com.spme.fantasolver.annotations.Generated;

public class User {
    private String username;
    private Team team;

    @Generated
    public User(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team){
        this.team = team;
    }
}
