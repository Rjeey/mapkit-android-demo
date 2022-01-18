package com.yandex.mapkitdemo.utils.model;

public class Scheduler {

    private int type;
    private String name;
    private String location;
    private String time;

    public Scheduler() {
    }

    public Scheduler(int type, String name, String location, String time) {
        this.type = type;
        this.name = name;
        this.location = location;
        this.time = time;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
