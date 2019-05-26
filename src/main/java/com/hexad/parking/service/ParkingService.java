package com.hexad.parking.service;

import java.util.Map;
import java.util.Optional;

import com.hexad.parking.domain.Parking;
import com.hexad.parking.domain.Slot;
import com.hexad.parking.domain.Vehicle;


public interface ParkingService
{
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
    Slot emptySlot(final Slot slot);

    /**
     * Returns a current picture of all the parking status
     * @return a Map containing Slot-Vehicle association
     */
    Map<Slot, Vehicle> status();
}
