package com.douwifi.yiks.tz.model;

import java.io.Serializable;

/**
 * Created by YiKS on 2016/7/8.
 * 时间段model
 */
public class DetaTime implements Serializable {

    private static final long serialVersionUID = -3248108563167056147L;
    private int id = 0;
    private long startTime = 0;
    private long endTime = 0;
    private int selected = 0;

    public DetaTime(){

    }

    public DetaTime(int id, long startTime, long endTime, int selected){
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.selected = selected;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }


    public int getSelected() {
        return selected;
    }

    public void setSelected(int selected) {
        this.selected = selected;
    }


}
