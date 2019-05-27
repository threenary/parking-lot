package com.hexad.parking;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import com.hexad.parking.common.exceptions.ParkingLotException;
import com.hexad.parking.service.impl.ParkingServiceImpl;
import com.hexad.parking.ui.CommandInterpreter;


public class Application
{
    public static void main(String[] args) throws ParkingLotException, IOException
    {
        CommandInterpreter interpreter = new CommandInterpreter();
        interpreter.setService(new ParkingServiceImpl());

        BufferedReader bufferReader = null;
        String input = null;

        System.out.println("\n");
        System.out.println("===================================================================");
        System.out.println("===========     The Most Amazing Parking System     ===============");
        System.out.println("===================================================================");
        switch (args.length)
        {
            case 0:
            {
                guide();
                System.out.println("Write 'exit' + Enter to exit the system");
                System.out.println("Input:");
                while (true)
                {
                    Scanner scanner = new Scanner(System.in);
                    String command = scanner.nextLine().trim();
                    if (command.equalsIgnoreCase("exit"))
                    {
                        break;
                    }
                    else
                    {
                        interpreter.read(command);
                        System.out.println("\nInput:");
                    }
                }
                break;
            }
            case 1:
            {
                File inputFile = new File(args[0]);

                try (BufferedReader bufferedReader = bufferReader = new BufferedReader(new FileReader(inputFile)))
                {
                    int lineNo = 1;
                    while ((input = bufferReader.readLine()) != null)
                    {
                        interpreter.read(input.trim());
                        lineNo++;
                    }
                }
            }
            break;
            default:
                throw new IllegalStateException("Unexpected value: " + args.length);
        }

    }

    private static void guide()
    {
        StringBuffer buffer = new StringBuffer();
        buffer = buffer.append("------------ Instructions -----------------------").append("\n");
        buffer = buffer.append("---> create_parking_lot <size>").append("\n");
        buffer = buffer.append("---> park <car_number> <car_clour>").append("\n");
        buffer = buffer.append("---> leave <slot_number>").append("\n");
        buffer = buffer.append("---> status").append("\n");
        buffer = buffer.append("---> registration_numbers_for_cars_with_colour <car_color>").append("\n");
        buffer = buffer.append("---> slot_number_for_registration_number <car_number>").append("\n");
        System.out.println(buffer.toString());
    }
}
