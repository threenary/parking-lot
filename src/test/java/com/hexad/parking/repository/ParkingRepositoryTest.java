package com.hexad.parking.repository;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.hexad.parking.common.exceptions.ParkingLotException;
import com.hexad.parking.domain.Slot;
import com.hexad.parking.domain.Vehicle;
import com.hexad.parking.repository.impl.ParkingRepositoryImpl;


public class ParkingRepositoryTest
{
    private static final int SIZE = 55;

    private static final String LICENSE_PLATE_ONE = "ABC 123";
    private static final String COLOR_ONE = "Red";
    private static final Vehicle VEHICLE_ONE = new Vehicle(LICENSE_PLATE_ONE, COLOR_ONE);

    private static final String LICENSE_PLATE_TWO = "XYZ 789";
    private static final String COLOR_TWO = "Black";
    private static final Vehicle VEHICLE_TWO = new Vehicle(LICENSE_PLATE_TWO, COLOR_TWO);

    private static final String LICENSE_PLATE_THREE = "HGD 567";
    private static final Vehicle VEHICLE_THREE = new Vehicle(LICENSE_PLATE_THREE, COLOR_TWO);

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
        final Optional<Slot> next = testSubject.getFreeSlot();

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
        testSubject.assignSlot(VEHICLE_ONE);

        //when
        final Optional<Slot> next = testSubject.getFreeSlot();

        //then
        assertNotNull(next);
        assertFalse(next.isPresent());
    }

    @Test
    public void assignAvailableSlotForVehicleTest() throws ParkingLotException
    {
        //given
        testSubject = new ParkingRepositoryImpl(1);

        //when
        Optional<Slot> parkingSlot = testSubject.assignSlot(VEHICLE_ONE);

        //then
        assertNotNull(parkingSlot);
        assertTrue(parkingSlot.isPresent());
        assertEquals(1, parkingSlot.get().getSlotId());
        assertEquals(VEHICLE_ONE, testSubject.getStatus().get(parkingSlot.get()));
    }

    @Test
    public void assignAvailableSlotForMoreVehiclesTest() throws ParkingLotException
    {
        //given
        testSubject = new ParkingRepositoryImpl(SIZE);
        testSubject.assignSlot(VEHICLE_ONE);

        //when
        Optional<Slot> results = testSubject.assignSlot(VEHICLE_TWO);

        //then
        assertNotNull(results);
        assertTrue(results.isPresent());
        assertEquals(2, results.get().getSlotId());
        assertEquals(VEHICLE_TWO, testSubject.getStatus().get(results.get()));
    }

    @Test
    public void unassignSlotTest() throws ParkingLotException
    {
        //given
        testSubject = new ParkingRepositoryImpl(SIZE);
        final Optional<Slot> assignedSlot = testSubject.assignSlot(VEHICLE_ONE);

        //when
        testSubject.unassignSlot(assignedSlot.get());

        //then
        assertNull(testSubject.getStatus().get(assignedSlot));
    }

    @Test
    public void unassignSecondSlotTest() throws ParkingLotException
    {
        //given
        testSubject = new ParkingRepositoryImpl(SIZE);
        testSubject.assignSlot(VEHICLE_ONE);
        final Optional<Slot> secondAssignedSlot = testSubject.assignSlot(VEHICLE_TWO);

        //when
        testSubject.unassignSlot(secondAssignedSlot.get());

        //then
        assertNull(testSubject.getStatus().get(secondAssignedSlot));
        assertEquals(secondAssignedSlot.get().getSlotId(), testSubject.getFreeSlot().get().getSlotId());
    }

    @Test
    public void getLicensePlatesWithColorTest() throws ParkingLotException
    {
        //given
        testSubject = new ParkingRepositoryImpl(SIZE);
        testSubject.assignSlot(VEHICLE_ONE);
        testSubject.assignSlot(VEHICLE_TWO);
        testSubject.assignSlot(VEHICLE_THREE);

        //when
        List<String> result = testSubject.getLicensePlatesWithColor("Black");

        //then
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(2, result.size());
        assertTrue(result.contains(LICENSE_PLATE_TWO));
        assertTrue(result.contains(LICENSE_PLATE_THREE));
        assertFalse(result.contains(LICENSE_PLATE_ONE));
    }

    @Test
    public void getSlotForLicensePlateTest() throws ParkingLotException
    {
        //given
        testSubject = new ParkingRepositoryImpl(SIZE);
        final Optional<Slot> slotOne = testSubject.assignSlot(VEHICLE_ONE);
        testSubject.assignSlot(VEHICLE_TWO);
        testSubject.assignSlot(VEHICLE_THREE);

        //when
        Slot result = testSubject.getSlotForRegistrationNumber(LICENSE_PLATE_ONE);

        //then
        assertNotNull(result);
        assertEquals(slotOne.get(), result);
    }

    @Test
    public void getSlotsForColorTest() throws ParkingLotException
    {
        //given
        testSubject = new ParkingRepositoryImpl(SIZE);
        final Optional<Slot> slotOne = testSubject.assignSlot(VEHICLE_ONE);
        final Optional<Slot> slotTwo = testSubject.assignSlot(VEHICLE_TWO);
        final Optional<Slot> slotThree = testSubject.assignSlot(VEHICLE_THREE);

        //when
        List<Slot> result = testSubject.getSlotsForColor(COLOR_TWO);

        //then
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertTrue(result.containsAll(Arrays.asList(slotTwo.get(), slotThree.get())));
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