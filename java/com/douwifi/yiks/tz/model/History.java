package com.douwifi.yiks.tz.model;

import java.io.Serializable;

/**
 * Created by YiKS on 2016/7/12.
 */
public class History implements Serializable {
    private static final long serialVersionUID = 552566296988880827L;
    private Integer id;
    private Result result;
    private String title;

    public History(){

    }
    public History(Integer id, Result result, String title){
        this.id = id;
        this.result = result;
        this.title = title;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String time) {
        this.title = title;
    }
}
