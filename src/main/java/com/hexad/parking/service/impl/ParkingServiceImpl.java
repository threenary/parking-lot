package com.hexad.parking.service.impl;

import static com.hexad.parking.common.constants.MessagesConstants.PARKING_CREATED;
import static com.hexad.parking.common.constants.MessagesConstants.PARKING_EMPTY_NOT_ALLOWED;
import static com.hexad.parking.common.constants.MessagesConstants.PARKING_FULL;
import static com.hexad.parking.common.constants.MessagesConstants.SLOT_ALLOCATED;
import static com.hexad.parking.common.constants.MessagesConstants.SLOT_FREE;
import static com.hexad.parking.common.constants.MessagesConstants.VEHICLE_NOT_FOUND;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import com.hexad.parking.common.exceptions.ParkingLotException;
import com.hexad.parking.domain.Slot;
import com.hexad.parking.domain.Vehicle;
import com.hexad.parking.repository.ParkingRepository;
import com.hexad.parking.repository.impl.ParkingRepositoryImpl;
import com.hexad.parking.service.ParkingService;


public class ParkingServiceImpl implements ParkingService
{
    private final ParkingRepository repository;

    public ParkingServiceImpl(final int size) throws ParkingLotException
    {
        if (size == 0)
        {
            throw new ParkingLotException(PARKING_EMPTY_NOT_ALLOWED);
        }
        repository = new ParkingRepositoryImpl(size);
        System.out.println(String.format(PARKING_CREATED, size));
    }

    @Override
    public long getFreeSlots()
    {
        return repository.getStatus().values().stream().filter(Objects::isNull).count();
    }

    @Override
    public Optional<Slot> park(final Vehicle vehicle)
    {
        Optional<Slot> slot = repository.assignSlot(vehicle);
        if (!slot.isPresent())
        {
            System.out.println(PARKING_FULL);
        }
        else
        {
            System.out.println(String.format(SLOT_ALLOCATED, slot.get().getSlotId()));
        }
        return slot;
    }

    @Override
    public Slot emptySlot(final Slot slot)
    {
        final Slot result = repository.unassignSlot(slot);
        if (null != result)
        {
            System.out.println(String.format(SLOT_FREE, slot.getSlotId()));
        }
        return result;
    }

    @Override
    public Map<Slot, Vehicle> status()
    {
        System.out.println("Slot No.\tRegistration No.\tColor");
        Map<Slot, Vehicle> status = repository.getStatus();
        printStatus(status);
        return status;
    }

    @Override
    public List<String> getLicensePlatesContainingColor(final String color)
    {
        List<String> result = repository.getLicensePlatesWithColor(color);
        result.forEach(System.out::println);
        return result;

    }

    @Override
    public Slot getSlotForLicensePlate(final String licensePlate)
    {
        final Slot result = repository.getSlotForRegistrationNumber(licensePlate);
        if (null != result)
        {
            System.out.println(result.getSlotId());
        }
        else
        {
            System.out.println(VEHICLE_NOT_FOUND);
        }
        return result;
    }

    @Override
    public List<Integer> getSlotsForColor(final String color)
    {
        List<Integer> result = repository.getSlotsForColor(color).stream().map(Slot::getSlotId).collect(Collectors.toList());
        result.forEach(System.out::println);
        return result;
    }

    private void printStatus(final Map<Slot, Vehicle> status)
    {
        if (status.size() == 0)
        {
            System.out.println("Sorry, parking lot is empty.");
        }
        else
        {
            for (Slot slot : status.keySet())
            {
                final Vehicle vehicle = status.get(slot);
                if (null != vehicle)
                {
                    System.out.println(slot.getSlotId() + "\t\t" + vehicle.getLicensePlate() + "\t\t" + vehicle.getColor());
                }
            }
        }
    }
}
