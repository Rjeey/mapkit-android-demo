package com.yandex.mapkitdemo.utils.model;

import com.yandex.mapkit.geometry.Point;

import java.util.List;

public class StopTrans {

    private String name;
    private Point location;
    private List<MyTime> timeArrival;


    public StopTrans() {
    }

    public StopTrans(String name, Point location, List<MyTime> timeArrival) {
        this.name = name;
        this.location = location;
        this.timeArrival = timeArrival;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public List<MyTime> getTimeArrival() {
        return timeArrival;
    }

    public void setTimeArrival(List<MyTime> timeArrival) {
        this.timeArrival = timeArrival;
    }
}
