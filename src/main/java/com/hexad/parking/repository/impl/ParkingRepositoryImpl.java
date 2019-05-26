package com.hexad.parking.repository.impl;

import static com.hexad.parking.common.error.ErrorConstants.NOT_EMPTY_PARKING_ALLOWED;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.PriorityBlockingQueue;

import com.hexad.parking.common.ParkingLotException;
import com.hexad.parking.domain.Slot;
import com.hexad.parking.domain.Vehicle;
import com.hexad.parking.repository.ParkingRepository;
import com.hexad.parking.util.SlotComparator;


public class ParkingRepositoryImpl implements ParkingRepository
{
    private Map<Slot, Vehicle> parkingSlots;

    private PriorityBlockingQueue<Slot> freeSlots;

    public ParkingRepositoryImpl(final int size) throws ParkingLotException
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

    private PriorityBlockingQueue<Slot> initializeSlotsPriority() throws ParkingLotException
    {
        if (parkingSlots.isEmpty())
        {
            throw new ParkingLotException(NOT_EMPTY_PARKING_ALLOWED);
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
    public Optional<Slot> getFreeSlots()
    {
        return Optional.ofNullable(freeSlots.poll());
    }

    @Override
    public Optional<Slot> park(final Vehicle vehicle)
    {
        Optional<Slot> freeSlot = getFreeSlots();
        if (freeSlot.isPresent())
        {
            parkingSlots.put(freeSlot.get(), vehicle);
        }
        return freeSlot;
    }

}
