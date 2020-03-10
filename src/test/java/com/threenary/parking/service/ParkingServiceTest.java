package com.threenary.parking.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import com.threenary.parking.domain.Slot;
import com.threenary.parking.domain.Vehicle;
import com.threenary.parking.service.ParkingService;
import com.threenary.parking.service.impl.ParkingServiceImpl;


public class ParkingServiceTest
{
    private final static int SIZE = 55;
    private ParkingService testSubject = new ParkingServiceImpl();

    @Before
    public void setUp() throws Exception
    {
        testSubject.createParking(SIZE);
    }

    @Test
    public void createParkingLotTest()
    {
        assertEquals(SIZE, testSubject.getAmountOfFreeSlots());
    }

    @Test
    public void createParkingServiceTest()
    {
        assertNotNull(testSubject);
        assertEquals(55, testSubject.getAmountOfFreeSlots());
    }

    @Test
    public void assignSlotTest()
    {
        //given
        final Vehicle vehicle = new Vehicle("ABC 123", "Red");

        //when
        Optional<Slot> result = testSubject.park(vehicle);

        //then
        assertNotNull(result);
        assertTrue(result.isPresent());
        assertEquals(1, result.get().getSlotId());
        assertEquals(54, testSubject.getAmountOfFreeSlots());
    }

    @Test
    public void assignSlotMoreThanOneVehicleTest()
    {
        //given
        final Vehicle vehicle1 = new Vehicle("ABC 123", "Red");
        testSubject.park(vehicle1);

        final Vehicle vehicle2 = new Vehicle("ABC 456", "Blue");

        //when
        Optional<Slot> result = testSubject.park(vehicle2);

        //then
        assertNotNull(result);
        assertTrue(result.isPresent());
        assertEquals(2, result.get().getSlotId());
        assertEquals(53, testSubject.getAmountOfFreeSlots());
    }

    @Test
    public void freeSlotTest()
    {
        //given
        final Vehicle vehicle1 = new Vehicle("ABC 123", "Red");
        final Optional<Slot> slot = testSubject.park(vehicle1);

        //when
        testSubject.emptySlot(slot.get().getSlotId());

        //then
        assertNull(testSubject.status().get(slot));
    }

    @Test
    public void getSlotsForColorTest()
    {
        //given
        final Vehicle vehicle1 = new Vehicle("ABC 123", "Red");
        final Optional<Slot> slot1 = testSubject.park(vehicle1);

        final Vehicle vehicle2 = new Vehicle("ABC 456", "Red");
        final Optional<Slot> slot2 = testSubject.park(vehicle1);

        //when
        List<Integer> result = testSubject.getSlotsForColor("Red");

        //then
        assertNotNull(result);
        assertTrue(!result.isEmpty());
        assertEquals(2, result.size());
        assertTrue(result.contains(1));
        assertTrue(result.contains(2));
    }

}