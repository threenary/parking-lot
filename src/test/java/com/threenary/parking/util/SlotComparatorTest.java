package com.threenary.parking.util;

import static org.junit.Assert.*;

import org.junit.Test;

import com.threenary.parking.common.SlotComparator;
import com.threenary.parking.domain.Slot;


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