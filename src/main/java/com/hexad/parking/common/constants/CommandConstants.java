package com.hexad.parking.common.constants;

public enum CommandConstants
{

    CREATE_PARKING_LOT("create_parking_lot"),
    PARK("park"),
    LEAVE("leave"),
    STATUS("status"),
    REG_NUMBER_FOR_CARS_WITH_COLOR("registration_numbers_for_cars_with_colour"),
    SLOTS_NUMBER_FOR_CARS_WITH_COLOR("slot_numbers_for_cars_with_colour"),
    SLOTS_NUMBER_FOR_REG_NUMBER("slot_number_for_registration_number");

    private String command;

    CommandConstants(final String command){
        this.command = command;
    }

    public static CommandConstants findByValue(String abbr){
        for(CommandConstants comm : values()){
            if( comm.getCommand().equals(abbr)){
                return comm;
            }
        }
        return null;
    }

    public String getCommand()
    {
        return command;
    }
}
