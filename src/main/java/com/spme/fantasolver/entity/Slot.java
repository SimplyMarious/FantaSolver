package com.spme.fantasolver.entity;

import com.spme.fantasolver.annotations.Generated;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Slot implements Comparable<Slot>{
    private short id;
    private Set<Role> roles = new HashSet<>();
    private static final short MAX_ROLES_PER_SLOT = 3;

    @Generated
    public Slot(short id){
        this.id = id;
    }

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

    @Generated
    public int getRolesSize() {
        return roles.size();
    }

    public void addRole(Role role) throws RoleException {
        if(Role.checkNewRoleSuitability(role, roles, MAX_ROLES_PER_SLOT)){
            roles.add(role);
        }
    }

    @Override
    public int compareTo(Slot slot) {
//        return Comparator.comparingInt(Slot::getRolesSize).
//                thenComparing(Slot::getId).compare(this, slot);

        return Comparator.comparingInt(Slot::getRolesSize).thenComparingInt(s -> s.getFirstRole().ordinal()).
                compare(this, slot);
    }

    @Generated
    private Role getFirstRole() {
        return (Role)roles.toArray()[0];
    }

    public static Slot[] sortSlotsByRolesSize(Slot[] slots){
        //List<Slot> slotsList = new ArrayList<>(Arrays.stream(slots).toList());
        List<Slot> slotsList = new ArrayList<>(Arrays.asList(slots));
        Collections.sort(slotsList);
        Slot[] sortedSlots = new Slot[11];
        return slotsList.toArray(sortedSlots);
    }
}
