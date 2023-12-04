package com.spme.fantasolver.entity;

import com.spme.fantasolver.annotations.Generated;

import java.util.*;

public class Player implements Comparable<Player>{
    private String name;
    private Set<Role> roles;
    private static final short MAX_ROLES_PER_PLAYER = 3;

    public Player(){
        this.roles = new HashSet<>();
    }

    @Generated
    public Player(String name, Set<Role> roles){
        this.name = name;
        this.roles = new HashSet<>(roles);
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
    public int getRolesSize() {
        return roles.size();
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

    @Override
    public int compareTo(Player player) {
        return Comparator.comparingInt(Player::getRolesSize).thenComparingInt(p -> p.getFirstRole().ordinal()).
                        compare(this, player);
    }

    @Generated
    private Role getFirstRole(){
        return (Role)roles.toArray()[0];
    }

    public static List<Player> sortPlayers(List<Player> players){
        Collections.sort(players);
        return players;
    }
}
