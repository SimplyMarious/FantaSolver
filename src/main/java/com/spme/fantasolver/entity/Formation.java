package com.spme.fantasolver.entity;

import com.spme.fantasolver.annotations.Generated;

public class Formation {
    private String name;
    private Slot[] slots;

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
}
