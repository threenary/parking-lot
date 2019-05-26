package com.hexad.parking.repository;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Optional;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.hexad.parking.common.ParkingLotException;
import com.hexad.parking.domain.Slot;
import com.hexad.parking.domain.Vehicle;
import com.hexad.parking.repository.impl.ParkingRepositoryImpl;


public class ParkingRepositoryTest
{
    private static final int SIZE = 55;
    private static final String LICENSE_PLATE = "ABC 123";
    private static final String COLOR = "Red";
    private static final Vehicle VEHICLE = new Vehicle(LICENSE_PLATE, COLOR);

    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    private ParkingRepository testSubject;

    @Test
    public void getStatusTest() throws ParkingLotException
    {
        //given + when
        testSubject = new ParkingRepositoryImpl(SIZE);

        //then
        assertEquals(55, testSubject.getStatus().size());
    }

    @Test
    public void getNextSlotTest() throws ParkingLotException
    {
        //given
        testSubject = new ParkingRepositoryImpl(SIZE);

        //when
        final Optional<Slot> next = testSubject.getFreeSlots();

        //then
        assertNotNull(next);
        assertTrue(next.isPresent());
        assertEquals(1, next.get().getSlotId());
    }

    @Test
    public void getNextSlotEmptyTest() throws ParkingLotException
    {
        //given
        testSubject = new ParkingRepositoryImpl(1);
        testSubject.park(VEHICLE);

        //when
        final Optional<Slot> next = testSubject.getFreeSlots();

        //then
        assertNotNull(next);
        assertFalse(next.isPresent());
    }

    @Test
    public void parkAvailableTest() throws ParkingLotException
    {
        //given
        testSubject = new ParkingRepositoryImpl(1);

        //when
        Optional<Slot> parkingSlot = testSubject.park(VEHICLE);

        //then
        assertNotNull(parkingSlot);
        assertTrue(parkingSlot.isPresent());
        assertEquals(1, parkingSlot.get().getSlotId());
        assertEquals(VEHICLE, testSubject.getStatus().get(parkingSlot.get()));
    }

    @Test
    public void createEmptySlotsRepositoryThrowsExceptionTest() throws ParkingLotException
    {
        //given
        expectedException.expect(ParkingLotException.class);
        testSubject = new ParkingRepositoryImpl(0);

        //otherwise
        fail();
    }

}