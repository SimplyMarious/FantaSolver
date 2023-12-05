package com.spme.fantasolver.entity;

import com.spme.fantasolver.annotations.Generated;

import java.util.*;

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
        return Comparator.comparingInt(Slot::getRolesSize).thenComparingInt(s -> s.getFirstRole().ordinal()).
                compare(this, slot);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Slot slot = (Slot) o;
        return Objects.equals(id, slot.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Generated
    private Role getFirstRole() {
        return (Role)roles.toArray()[0];
    }

    public static Slot[] sortSlotsByRolesSize(Slot[] slots){
        List<Slot> slotsList = new ArrayList<>(Arrays.asList(slots));
        Collections.sort(slotsList);
        Slot[] sortedSlots = new Slot[11];
        return slotsList.toArray(sortedSlots);
    }
}
