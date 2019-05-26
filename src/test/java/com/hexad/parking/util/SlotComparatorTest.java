package com.hexad.parking.util;

import static org.junit.Assert.*;

import org.junit.Test;

import com.hexad.parking.domain.Slot;
import com.hexad.parking.common.SlotComparator;


public class SlotComparatorTest
{

    private final SlotComparator testSubject = new SlotComparator();

    @Test
    public void compareTest()
    {
        // given
        final Slot slot1 = new Slot(1);
        final Slot slot2 = new Slot(2);

        //when
        final int result = testSubject.compare(slot1, slot2);

        //then
        assertTrue(result<0);
    }
}