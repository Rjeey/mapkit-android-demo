package com.yandex.mapkitdemo.utils.model;


import com.yandex.mapkit.geometry.Point;

import java.util.ArrayList;
import java.util.List;

public class TransportPoint {

    private List<Point> points;
    private List<StopTrans> stopTrans;


    public void fillPoints(){
        if(stopTrans!=null && stopTrans.size()>0){
            stopTrans.forEach(i-> points.add(i.getLocation()));
        }
    }

    public TransportPoint() {
        points = new ArrayList<>();
        stopTrans = new ArrayList<>();
    }

    public TransportPoint(List<StopTrans> stopTrans) {
        points = new ArrayList<>();
        this.stopTrans = stopTrans;
    }

    public TransportPoint(List<Point> points, List<StopTrans> stopTrans) {
        this.points = points;
        this.stopTrans = stopTrans;
    }

    public List<Point> getPoints() {
        return points;
    }

    public void setPoints(List<Point> points) {
        this.points = points;
    }

    public List<StopTrans> getStopTrans() {
        return stopTrans;
    }

    public void setStopTrans(List<StopTrans> stopTrans) {
        this.stopTrans = stopTrans;
    }
}
