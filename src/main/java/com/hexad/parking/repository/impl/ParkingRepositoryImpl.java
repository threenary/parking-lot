package com.hexad.parking.repository.impl;

import static com.hexad.parking.common.constants.MessagesConstants.PARKING_EMPTY_NOT_ALLOWED;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.PriorityBlockingQueue;

import com.hexad.parking.common.SlotComparator;
import com.hexad.parking.common.exceptions.ParkingLotException;
import com.hexad.parking.common.exceptions.RepositoryException;
import com.hexad.parking.domain.Slot;
import com.hexad.parking.domain.Vehicle;
import com.hexad.parking.repository.ParkingRepository;


public class ParkingRepositoryImpl implements ParkingRepository
{
    private Map<Slot, Vehicle> parkingSlots;

    private PriorityBlockingQueue<Slot> freeSlots;

    public ParkingRepositoryImpl(final int size) throws RepositoryException
    {
        this.parkingSlots = initializeSlots(size);
        this.freeSlots = initializeSlotsPriority();
    }

    private Map<Slot, Vehicle> initializeSlots(final int size)
    {
        Map<Slot, Vehicle> slots = new HashMap<>();
        for (int i = 1; i <= size; i++)
        {
            slots.put(new Slot(i), null);
        }
        return slots;
    }

    private PriorityBlockingQueue<Slot> initializeSlotsPriority() throws RepositoryException
    {
        if (parkingSlots.isEmpty())
        {
            throw new RepositoryException(PARKING_EMPTY_NOT_ALLOWED);
        }
        final PriorityBlockingQueue<Slot> queue = new PriorityBlockingQueue<>(parkingSlots.size(), new SlotComparator());
        queue.addAll(parkingSlots.keySet());
        return queue;

    }

    @Override
    public Map<Slot, Vehicle> getStatus()
    {
        return parkingSlots;
    }

    @Override
    public Optional<Slot> getFreeSlot()
    {
        return Optional.ofNullable(freeSlots.poll());
    }

    @Override
    public Optional<Slot> assignSlot(final Vehicle vehicle)
    {
        Optional<Slot> freeSlot = getFreeSlot();
        if (freeSlot.isPresent())
        {
            parkingSlots.put(freeSlot.get(), vehicle);
        }
        return freeSlot;
    }

    @Override
    public void unassignSlot(final Slot slot)
    {
        parkingSlots.put(slot, null);
        freeSlots.add(slot);
    }

}
