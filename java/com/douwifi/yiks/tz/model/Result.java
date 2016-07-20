package com.douwifi.yiks.tz.model;

import com.douwifi.yiks.tz.model.DetaTime;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by YiKS on 2016/7/8.
 */
public class Result implements Serializable{
    private static final long serialVersionUID = 3898765860903157834L;

    private ArrayList<DetaTime> detaTimes;
    private ArrayList<String> stamacs;

    public Result(){

    }

    public Result(ArrayList<DetaTime> detaTime, ArrayList<String> stamacs){
        this.detaTimes = detaTime;
        this.stamacs = stamacs;
    }

    public ArrayList<DetaTime> getDetaTimes() {
        return detaTimes;
    }

    public void setDetaTimes(ArrayList<DetaTime> detaTimes) {
        this.detaTimes = detaTimes;
    }

    public ArrayList<String> getStamacs() {
        return stamacs;
    }

    public void setStamacs(ArrayList<String> stamacs) {
        this.stamacs = stamacs;
    }

}
