package com.spme.fantasolver.entity;

import com.spme.fantasolver.annotations.Generated;

public class Formation {
    private String name;
    private Slot[] slots;

    public Formation(String name) {
        this.name = name;
        this.slots = new Slot[11];
        for (short i = 0; i < 11; i++){
            slots[i] = new Slot(i);
        }
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
    public Slot[] getSlots() {
        return slots;
    }

    public void setSlot(Slot slot){
        this.slots[slot.getId()] = slot;
    }

    public void addRole(int idSlot, Role role) throws RoleException {
        slots[idSlot].addRole(role);
    }
}
