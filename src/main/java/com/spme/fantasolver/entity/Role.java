package com.spme.fantasolver.entity;

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
}
