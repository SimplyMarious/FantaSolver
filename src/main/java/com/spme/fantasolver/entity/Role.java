package com.spme.fantasolver.entity;

import com.spme.fantasolver.annotations.Generated;
import com.spme.fantasolver.dao.RoleNotFoundException;

import java.util.EnumSet;
import java.util.Set;

public enum Role {
    POR,
    DC,
    DD,
    DS,
    E,
    M,
    C,
    W,
    T,
    A,
    PC;

    public static boolean checkNewRoleSuitability(Role role, Set<Role> roles, short maxRoles) throws RoleException{
        if(role == null || roles == null){
            throw new NullPointerException();
        }
        if(roles.contains(role)){
            throw new DuplicateRoleException();
        }
        if(roles.size() == maxRoles){
            throw new RoleLimitExceededException();
        }
        return true;
    }

    public static Role roleFromString(String roleName) throws RoleNotFoundException {
        for (Role role : EnumSet.allOf(Role.class)) {
            if (role.name().equalsIgnoreCase(roleName)) {
                return role;
            }
        }
        throw new RoleNotFoundException("Role not found: " + roleName);
    }
}
