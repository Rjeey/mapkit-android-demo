package com.yandex.mapkitdemo.utils.model;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ViewTransportData {

    private int number;
    private String path;
    private int imgId;
    private List<TransportPoint> route;
    private List<String> stopsList;

    public void fillStopsList(){
        if(stopsList!= null && route != null){
            route.forEach(i-> i.getStopTrans().forEach(j->stopsList.add(j.getName())));
        }
    }

    public ViewTransportData() {
    }

    public ViewTransportData(int number, @Nullable List<TransportPoint> route, int imgId, String path) {
        stopsList = new ArrayList<>();
        this.number = number;
        this.route = route;
        this.imgId = imgId;
        this.path = path;
    }

    public ViewTransportData(int number, List<TransportPoint> route, List<String> stopsList) {
        this.number = number;
        this.route = route;
        this.stopsList = stopsList;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public List<TransportPoint> getRoute() {
        return route;
    }

    public void setRoute(List<TransportPoint> route) {
        this.route = route;
    }

    public List<String> getStopsList() {
        return stopsList;
    }

    public void setStopsList(List<String> stopsList) {
        this.stopsList = stopsList;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
