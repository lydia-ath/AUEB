package com.test.toyotomi_new_era;

final class State
{
    private int temperature;
    private String airDirection, airType, timer, airIntensity, sleepMode;

    State(int temp, String dir, String type, String intensity, String time, String sleep)
    {
        this.temperature = temp;
        this.airIntensity = intensity;
        this.airDirection = dir;
        this.airType = type;
        this.timer = time;
        this.sleepMode = sleep;
    }

    public int getTemperature() {
        return this.temperature;
    }

    public String getAirDirection() {
        return this.airDirection;
    }

    public void setAirDirection(String airDirection) {
        this.airDirection = airDirection;
    }

    public String getAirType() {
        return this.airType;
    }

    public void setAirType(String airType) {
        this.airType = airType;
    }

    public String getTimer() {
        return this.timer;
    }

    public void setTimer(String timer) {
        this.timer = timer;
    }

    public String isSleepMode() {
        return this.sleepMode;
    }

    public void setSleepMode(String sleepMode) {
        this.sleepMode = sleepMode;
    }

    public String getAirIntensity() {
        return this.airIntensity;
    }

    public void setAirIntensity(String airIntensity) {
        this.airIntensity = airIntensity;
    }
}
