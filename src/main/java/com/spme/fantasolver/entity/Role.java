package com.spme.fantasolver.entity;

import com.spme.fantasolver.utility.Utility;
import javafx.beans.property.SimpleStringProperty;

import java.util.*;


public enum Role {
    POR,
    DS,
    DC,
    DD,
    E,
    M,
    C,
    W,
    T,
    A,
    PC;

    public static boolean checkNewRoleSuitability(Role role, Set<Role> roles, int maxRoles) throws RoleException{
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

    public static Role[] sortRoles(Role[] roles) {
        Arrays.sort(roles, Comparator.comparingInt(Enum::ordinal));
        return roles;
    }
}
