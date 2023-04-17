package com.yandex.mapkitdemo.utils.model;

import androidx.annotation.NonNull;

public class MyTime {

    private int hours;
    private int minutes;


    public MyTime(int hours, int minutes) {
        this.hours = hours;
        this.minutes = minutes;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    @NonNull
    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        if (hours < 10) {
            res.append("0").append(hours);
        } else
            res.append(hours);
        if (minutes < 10) {
            res.append(":0").append(minutes);
        }else
            res.append(":").append(minutes);

        return res.toString();
    }
}
