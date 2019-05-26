package com.hexad.parking.repository;

import java.util.Map;
import java.util.Optional;

import com.hexad.parking.domain.Slot;
import com.hexad.parking.domain.Vehicle;


public interface ParkingRepository
{
    /**
     * Refresh the current status of the repository
     * @return a Map with Slot - Vehicle/null relationship
     */
    Map<Slot, Vehicle> getStatus();

    /**
     * Returns the next slot available
     * @return a Slot or absent if none
     */
    Optional<Slot> getFreeSlot();

    /**
     * Assigns a vehicle to a free slot
     * @param vehicle
     * @return the occupied slot or absent if none
     */
    Optional<Slot> assignSlot(final Vehicle vehicle);

    /**
     * Frees an occupied slot
     * @param slot
     */
    Slot unassignSlot(final Slot slot);
}
