package com.douwifi.yiks.tz;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by YiKS on 2016/7/8.
 */
public class ResultItemData implements Serializable{
    private String stamac;
    private ArrayList<DetaTime> detaTimes;
    private ArrayList<Long> times;



    public ResultItemData(){

    }
    public ResultItemData(String stamac, ArrayList<DetaTime> detaTime, ArrayList<Long> times){
        this.stamac = stamac;
        this.detaTimes = detaTime;
        this.times = times;
    }


    public String getStamac() {
        return stamac;
    }

    public void setStamac(String stamac) {
        this.stamac = stamac;
    }

    public ArrayList<DetaTime> getDetaTimes() {
        return detaTimes;
    }

    public void setDetaTimes(ArrayList<DetaTime> detaTime) {
        this.detaTimes = detaTime;
    }

    public ArrayList<Long> getTimes() {
        return times;
    }

    public void setTimes(ArrayList<Long> times) {
        this.times = times;
    }



}
