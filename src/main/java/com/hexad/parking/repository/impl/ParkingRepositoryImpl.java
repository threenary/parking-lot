package com.hexad.parking.repository.impl;

import static com.hexad.parking.common.constants.MessagesConstants.PARKING_EMPTY_NOT_ALLOWED;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.stream.Collectors;

import com.hexad.parking.common.SlotComparator;
import com.hexad.parking.common.exceptions.ParkingLotException;
import com.hexad.parking.domain.Slot;
import com.hexad.parking.domain.Vehicle;
import com.hexad.parking.repository.ParkingRepository;


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
            throw new ParkingLotException(PARKING_EMPTY_NOT_ALLOWED);
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
    public Slot unassignSlot(final Slot slot)
    {
        if (parkingSlots.get(slot) != null)
        {
            parkingSlots.put(slot, null);
            freeSlots.add(slot);
        }
        return slot;
    }

    @Override
    public List<String> getLicensePlatesWithColor(final String color)
    {
        return parkingSlots.entrySet().stream()
            .filter(entry -> entry.getValue() != null && entry.getValue().getColor().equals(color))
            .map(entry -> entry.getValue().getLicensePlate())
            .collect(Collectors.toList());
    }

    @Override
    public Slot getSlotForRegistrationNumber(final String licensePlate)
    {
        Optional<Map.Entry<Slot, Vehicle>> result = parkingSlots.entrySet().stream()
            .filter(entry -> entry.getValue() != null && entry.getValue().getLicensePlate().equals(licensePlate)).findFirst();

        return result.map(Map.Entry::getKey).orElse(null);
    }

    @Override
    public List<Slot> getSlotsForColor(final String color)
    {
        return parkingSlots.entrySet().stream()
            .filter(entry -> entry.getValue() != null && entry.getValue().getColor().equals(color))
            .map(Map.Entry::getKey)
            .collect(Collectors.toList());
    }

}
