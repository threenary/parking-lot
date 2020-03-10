package com.threenary.parking.common;

import java.util.Comparator;

import com.threenary.parking.domain.Slot;


public class SlotComparator implements Comparator<Slot>
{
    @Override
    public int compare(final Slot slot1, final Slot slot2)
    {
        return slot1.getSlotId() - slot2.getSlotId();
    }
}
