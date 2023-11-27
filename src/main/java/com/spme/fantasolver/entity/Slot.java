package com.spme.fantasolver.entity;

import com.spme.fantasolver.annotations.Generated;

import java.util.HashSet;
import java.util.Set;

public class Slot {
    private short id;
    private Set<Role> roles = new HashSet<>();
    private static final short MAX_ROLES_PER_SLOT = 3;

    @Generated
    public short getId() {
        return id;
    }

    @Generated
    public void setId(short id) {
        this.id = id;
    }

    @Generated
    public Set<Role> getRoles() {
        return roles;
    }

    public void addRole(Role role) throws RoleException {
        if(Role.checkNewRoleSuitability(role, roles, MAX_ROLES_PER_SLOT)){
            roles.add(role);
        }
    }
}
