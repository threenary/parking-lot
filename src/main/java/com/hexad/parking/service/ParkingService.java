package com.hexad.parking.service;

import java.util.Optional;

import com.hexad.parking.domain.Parking;
import com.hexad.parking.domain.Slot;
import com.hexad.parking.domain.Vehicle;


public interface ParkingService
{
    /**
     * Entrey point to create a parking lot
     * @param size
     * @return a brand new and empty {@link Parking}
     */
    Parking createParking(final int size);

    /**
     * Returns the empty slots in the Parking
     * @return the number of {@link Slot} without {@link Vehicle}
     */
    long getFreeSlots();

    /**
     * Assigns a vehicle to a free parking slot.
     * If no available it returns false.
     * @param vehicle
     * @return Optional<Slot> which is absent in case there is no slot available
     */
    Optional<Slot> park(final Vehicle vehicle);

    /**
     * Empties the parking slot and makes it available for future occupancy
     * @param slot
     * @return the empty slot if present
     */
    Optional<Slot> freeSlot(final Slot slot);

}
