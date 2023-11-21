package com.spme.fantasolver.entity;

import java.util.HashSet;
import java.util.Set;

public class Slot {
    private short id;
    private Set<Role> roles = new HashSet<>();
    private static final short MAX_ROLES_PER_SLOT = 3;

    public short getId() {
        return id;
    }

    public void setId(short id) {
        this.id = id;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void addRole(Role role) throws RoleException {
        if(Role.checkNewRoleSuitability(role, roles, MAX_ROLES_PER_SLOT)){
            roles.add(role);
        }
    }
}
