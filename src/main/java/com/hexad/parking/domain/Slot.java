package com.hexad.parking.domain;

import java.util.Objects;


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

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (!(o instanceof Slot))
        {
            return false;
        }
        final Slot slot = (Slot) o;
        return getSlotId() == slot.getSlotId();
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(getSlotId());
    }
}
