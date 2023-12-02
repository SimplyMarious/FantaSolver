package com.spme.fantasolver.entity;

import com.spme.fantasolver.annotations.Generated;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Player {
    private String name;
    private Set<Role> roles;
    private static final short MAX_ROLES_PER_PLAYER = 3;

    public Player(){
        this.roles = new HashSet<>();
    }

    @Generated
    public Player(String name, Set<Role> roles){
        this.name = name;
        this.roles = roles;
    }

    @Generated
    public Player(String name){
        this.name = name;
        this.roles = new HashSet<>();
    }

    @Generated
    public String getName() {
        return name;
    }

    @Generated
    public void setName(String name) {
        this.name = name;
    }

    @Generated
    public Set<Role> getRoles() {
        return roles;
    }

    @Generated
    public void setRoles(Set<Role> role) {
        this.roles = role;
    }

    public void addRole(Role role) throws RoleException {
        if(Role.checkNewRoleSuitability(role, roles, MAX_ROLES_PER_PLAYER)){
            roles.add(role);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(name, player.name);
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", roles=" + roles +
                '}';
    }
}
