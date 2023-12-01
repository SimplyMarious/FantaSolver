package com.spme.fantasolver.entity;

import com.spme.fantasolver.annotations.Generated;

import java.util.HashSet;
import java.util.Set;

@Generated
public class User {
    private String username;
    private Team team;
    private Set<UserObserver> observers = new HashSet<>();


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
        if (this.team != team) {
            this.team = team;
            notifyObservers();
        }
    }

    public void addObserver(UserObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(UserObserver observer) {
        observers.remove(observer);
    }

    private void notifyObservers() {
        for (UserObserver observer : observers) {
            observer.onTeamChanged(this);
        }
    }
}
