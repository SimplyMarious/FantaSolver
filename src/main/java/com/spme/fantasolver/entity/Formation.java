package com.spme.fantasolver.entity;

public class Formation {
    private String name;
    private Slot[] slots;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Slot[] getSlots() {
        return slots;
    }

    public void setSlot(Slot slot){
        this.slots[slot.getId()] = slot;
    }
}
