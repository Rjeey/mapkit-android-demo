package com.yandex.mapkitdemo.utils.transport;

import com.yandex.mapkitdemo.utils.transport.types.TransportTypes;

import java.util.List;

public class Trans {

    private String number;
    private int type;
    private List<StopsData> stops;

    public Trans() {
    }

    public Trans(String number, int type, List<StopsData> stops) {
        this.number = number;
        this.type = type;
        this.stops = stops;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<StopsData> getStops() {
        return stops;
    }

    public void setStops(List<StopsData> stops) {
        this.stops = stops;
    }
}
