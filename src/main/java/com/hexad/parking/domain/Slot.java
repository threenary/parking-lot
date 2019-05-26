package com.hexad.parking.domain;

public class Slot
{
    private int slotId;

    public Slot(final int slotId)
    {
        this.slotId = slotId;
    }

    public int getSlotId()
    {
        return slotId;
    }

    public void setSlotId(final int slotId)
    {
        this.slotId = slotId;
    }
}
