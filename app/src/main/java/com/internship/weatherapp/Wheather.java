package com.internship.weatherapp;

/**
 * Created by apersin on 21-Mar-17.
 */

public class Wheather {
    private String dayName;
    private String dayState;
    private String maxTemperature;
    private String minTemperature;

    public Wheather(String dayName, String dayState, String maxTemperature, String minTemperature) {
        this.dayName = dayName;
        this.dayState = dayState;
        this.maxTemperature = maxTemperature;
        this.minTemperature = minTemperature;
    }

    public String getDayName() {
        return dayName;
    }

    public void setDayName(String dayName) {
        this.dayName = dayName;
    }

    public String getDayState() {
        return dayState;
    }

    public void setDayState(String dayState) {
        this.dayState = dayState;
    }

    public String getMaxTemperature() {
        return maxTemperature;
    }

    public void setMaxTemperature(String maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    public String getMinTemperature() {
        return minTemperature;
    }

    public void setMinTemperature(String minTemperature) {
        this.minTemperature = minTemperature;
    }
}
