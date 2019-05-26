package com.hexad.parking.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import com.hexad.parking.domain.Slot;
import com.hexad.parking.domain.Vehicle;
import com.hexad.parking.service.impl.ParkingServiceImpl;


public class ParkingServiceTest
{
    private final static int SIZE = 55;
    private ParkingService testSubject;

    @Before
    public void setUp() throws Exception
    {
        this.testSubject = new ParkingServiceImpl(SIZE);
    }

    @Test
    public void createParkingServiceTest()
    {
        assertNotNull(testSubject);
        assertEquals(55, testSubject.getFreeSlots());
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
    }

    @Test
    public void freeSlotTest()
    {
    }
}