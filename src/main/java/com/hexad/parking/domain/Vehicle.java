package com.hexad.parking.domain;

public class Vehicle
{
    private String licensePlate;

    private String color;

    public Vehicle(final String licensePlate, final String color)
    {
        this.licensePlate = licensePlate;
        this.color = color;
    }

    public String getLicensePlate()
    {
        return licensePlate;
    }

    public void setLicensePlate(final String licensePlate)
    {
        this.licensePlate = licensePlate;
    }

    public String getColor()
    {
        return color;
    }

    public void setColor(final String color)
    {
        this.color = color;
    }
}
