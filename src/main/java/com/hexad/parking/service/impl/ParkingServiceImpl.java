package com.hexad.parking.service.impl;

import java.util.Objects;
import java.util.Optional;

import com.hexad.parking.common.ParkingLotException;
import com.hexad.parking.domain.Parking;
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
        repository = new ParkingRepositoryImpl(size);
    }

    @Override
    public Parking createParking(final int size)
    {
        return null;
    }

    @Override
    public long getFreeSlots()
    {
        return repository.getStatus().values().stream().filter(Objects::isNull).count();
    }

    @Override
    public Optional<Slot> park(final Vehicle vehicle)
    {
        return repository.park(vehicle);
    }

    @Override
    public Optional<Slot> freeSlot(final Slot slot)
    {
        return Optional.empty();
    }
}
