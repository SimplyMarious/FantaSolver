package com.spme.fantasolver.entity;

import java.util.Set;

public class Player {
    private String name;
    private Set<Role> roles;

    public Player(String name, Set<Role> roles){
        this.name = name;
        this.roles = roles;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> role) {
        this.roles = role;
    }
}
