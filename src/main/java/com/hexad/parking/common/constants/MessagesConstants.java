package com.hexad.parking.common.error;

public interface ErrorConstants
{
    String PARKING_EMPTY_NOT_ALLOWED = "Can't create an empty Parking Lot service";

    String PARKING_FULL = "Sorry, parking lot is full";

    String PARKING_CREATED = "Created a parking lot with %s slots";

    String SLOT_ALLOCATED = "Allocated slot number: %s";

    String VEHICLE_NOT_FOUND = "Not found";
}
