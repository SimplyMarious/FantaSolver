package com.spme.fantasolver.entity;

import com.spme.fantasolver.utility.Utility;
import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.EnumSet;


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

    public static SimpleStringProperty getFormattedRoles(Set<Role> roles){
        if(roles == null || roles.isEmpty()){
            throw new IllegalArgumentException();
        }
        List<String> rolesNames = new ArrayList<>();
        for(Role role: roles){
            rolesNames.add(role.name());
        }
        return new SimpleStringProperty(Utility.getFormattedStrings(rolesNames));
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
