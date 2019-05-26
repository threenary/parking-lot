package com.hexad.parking.service.impl;

import static com.hexad.parking.common.constants.MessagesConstants.PARKING_EMPTY_NOT_ALLOWED;
import static com.hexad.parking.common.constants.MessagesConstants.PARKING_FULL;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

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
        return slot;
    }

    @Override
    public Optional<Slot> emptySlot(final Slot slot)
    {
        return Optional.empty();
    }

    @Override
    public Map<Slot, Vehicle> status()
    {
        return repository.getStatus();
    }
}
