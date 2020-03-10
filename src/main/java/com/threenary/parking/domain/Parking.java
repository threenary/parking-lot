package com.threenary.parking.domain;

import java.util.TreeSet;


public class Parking
{
    private TreeSet<Slot> slotsAvailable;

    public Parking(final TreeSet<Slot> slotsAvailable)
    {
        this.slotsAvailable = slotsAvailable;
    }

    public TreeSet<Slot> getSlotsAvailable()
    {
        return slotsAvailable;
    }

    public void setSlotsAvailable(final TreeSet<Slot> slotsAvailable)
    {
        this.slotsAvailable = slotsAvailable;
    }
}
