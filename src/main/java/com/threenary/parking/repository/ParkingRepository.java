package com.threenary.parking.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.threenary.parking.domain.Slot;
import com.threenary.parking.domain.Vehicle;


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

    /**
     * Returns all the license plates for vehicles with the given color
     * @param color
     * @return a List of license plate in String format, or empty
     */
    List<String> getLicensePlatesWithColor(final String color);

    /**
     * Returns the slot for a given registration number
     * @param licensePlate
     * @return a {@link Slot}
     */
    Slot getSlotForRegistrationNumber(final String licensePlate);

    /**
     * Returns all the slot parks where vehicles of the given color are parked
     * @param color
     * @return a {@link List} of {@link Slot}
     */
    List<Slot> getSlotsForColor(final String color);

}
