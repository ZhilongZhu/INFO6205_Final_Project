package edu.neu.coe.info6205.util;

public class Day {
    private final int startDay;
    private int daysInterval;
    private int currentDay;

    /**
     * by default, we count the days of simulation from 0
     *
     */
    public Day() {
        this.startDay = 0;
    }

    public void updateCurrentDay(){
        this.currentDay = this.startDay + this.daysInterval;
    }

    public void updateDaysInterval(){
        this.daysInterval = this.currentDay = this.startDay;
    }
    public int getStartDay() {
        return startDay;
    }

    public int getDaysInterval() {
        return daysInterval;
    }

    public void setDaysInterval(int daysInterval) {
        this.daysInterval = daysInterval;
        updateCurrentDay();
    }

    public int getCurrentDay() {
        return currentDay;
    }

    public void setCurrentDay(int currentDay) {
        this.currentDay = currentDay;
        updateDaysInterval();
    }

    public void nextDay(){
        this.daysInterval += 1;
        this.updateCurrentDay();
    }


}
