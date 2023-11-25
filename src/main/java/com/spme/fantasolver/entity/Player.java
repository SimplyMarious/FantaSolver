package com.spme.fantasolver.entity;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Player {
    private String name;
    private Set<Role> roles;
    private static final short MAX_ROLES_PER_PLAYER = 3;

    public Player(String name, Set<Role> roles){
        this.name = name;
        this.roles = roles;
    }

    public Player(String name){
        this.name = name;
        this.roles = new HashSet<>();
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
        return Objects.equals(name, player.name) && Objects.equals(roles, player.roles);
    }

}
