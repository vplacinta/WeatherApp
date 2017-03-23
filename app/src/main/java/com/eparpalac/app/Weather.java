package com.eparpalac.app;

class Weather {

    private String day, state, tempDay, tempNight;

    public Weather() {
    }

    Weather(String day, String state, String tempDay, String tempNight) {
        this.day = day;
        this.state = state;
        this.tempDay = tempDay;
        this.tempNight = tempNight;
    }

    String getDay() {
        return day;
    }

    String getState() {
        return state;
    }

    String getTempDay() {
        return tempDay;
    }

    String getTempNight() {
        return tempNight;
    }

}
