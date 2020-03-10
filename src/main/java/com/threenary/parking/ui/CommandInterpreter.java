package com.threenary.parking.ui;

import static com.threenary.parking.common.constants.MessagesConstants.INVALID_COMMAND;

import com.threenary.parking.common.constants.CommandConstants;
import com.threenary.parking.common.exceptions.ParkingLotException;
import com.threenary.parking.domain.Vehicle;
import com.threenary.parking.service.ParkingService;


public class CommandInterpreter
{
    private static final int COMMAND_INDEX = 0;
    private static final int PARAMETER_INDEX = 1;

    private static final int COLOR_INDEX = 2;

    private ParkingService service;

    public void setService(final ParkingService service)
    {
        this.service = service;
    }

    public void read(final String input) throws ParkingLotException
    {
        final String[] cvp = input.split(" ");

        final CommandConstants command = CommandConstants.findByValue(cvp[COMMAND_INDEX]);

        if (null != command)
        {
            switch (command)
            {
                case CREATE_PARKING_LOT:
                    service.createParking(Integer.valueOf(cvp[PARAMETER_INDEX]));
                    break;
                case PARK:
                    park(cvp[PARAMETER_INDEX], cvp[COLOR_INDEX]);
                    break;
                case LEAVE:
                    leave(cvp[PARAMETER_INDEX]);
                    break;
                case STATUS:
                    service.status();
                    break;
                case SLOTS_NUMBER_FOR_REG_NUMBER:
                    service.getSlotForLicensePlate(cvp[PARAMETER_INDEX]);
                    break;
                case REG_NUMBER_FOR_CARS_WITH_COLOR:
                    service.getLicensePlatesContainingColor(cvp[PARAMETER_INDEX]);
                    break;
                case SLOTS_NUMBER_FOR_CARS_WITH_COLOR:
                    service.getSlotsForColor(cvp[PARAMETER_INDEX]);
                    break;
            }
        }
        else
        {
            System.out.println(INVALID_COMMAND);
        }
    }

    private void leave(final String slotId)
    {
        service.emptySlot(Integer.valueOf(slotId));
    }

    private void park(final String licensePlate, final String color)
    {
        final Vehicle vehicle = new Vehicle(licensePlate, color);
        service.park(vehicle);
    }

}
