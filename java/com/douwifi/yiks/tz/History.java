package com.douwifi.yiks.tz;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by YiKS on 2016/7/12.
 */
public class History implements Serializable {
    private Integer id;
    private ArrayList<ResultItemData> resultList;
    private long time;

    public History(){

    }
    public History(Integer id, ArrayList<ResultItemData> resultList, long time){
        this.id = id;
        this.resultList = resultList;
        this.time = time;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ArrayList<ResultItemData> getResultList() {
        return resultList;
    }

    public void setResultList(ArrayList<ResultItemData> resultList) {
        this.resultList = resultList;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
