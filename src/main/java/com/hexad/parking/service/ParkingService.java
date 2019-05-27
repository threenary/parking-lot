package com.hexad.parking.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.hexad.parking.common.exceptions.ParkingLotException;
import com.hexad.parking.domain.Parking;
import com.hexad.parking.domain.Slot;
import com.hexad.parking.domain.Vehicle;


public interface ParkingService
{
    /**
     * Returns the empty slots in the Parking
     * @return the number of {@link Slot} without {@link Vehicle}
     */
    long getAmountOfFreeSlots();

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
    Slot emptySlot(final int slot);

    /**
     * Returns a current picture of all the parking status
     * @return a Map containing Slot-Vehicle association
     */
    Map<Slot, Vehicle> status();

    /**
     * Returns a list of license plates for a given color of vehicle
     * @param color
     * @return a {@link List} of {@link String}
     */
    List<String> getLicensePlatesContainingColor(final String color);

    /**
     * Returns the slot for a given registration number
     * @param licensePlate
     * @return a {@link Slot}
     */
    Slot getSlotForLicensePlate(final String licensePlate);

    /**
     * Returns all the slot parks where vehicles of the given color are parked
     * @param color
     * @return a {@link List} of {@link Slot}
     */
    List<Integer> getSlotsForColor(final String color);

    /**
     * Creates a parking lot with the amount of slots recieved as parameter
     * @param size
     */
    void createParking(final int size) throws ParkingLotException;
}
