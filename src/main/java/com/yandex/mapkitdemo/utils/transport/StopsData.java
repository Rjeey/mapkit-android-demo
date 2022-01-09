package com.yandex.mapkitdemo.utils.transport;

import java.util.Random;

public class StopsData {

    private String name;


    public StopsData() {
    }

    public StopsData(String name) {
        this.name = name;
    }

    public String getTimeArrived(){
        return (new Random().nextInt(10)+1)+"мин";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
