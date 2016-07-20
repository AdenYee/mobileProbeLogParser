package com.douwifi.yiks.tz;

/**
 * Created by YiKS on 2016/7/13.
 */
public class Config {

    public static String[] logPaths = {"/storage/sdcard1/clifile", "/storage/sdcard0/clifile"};


    public final static String DatabaseName = "Timer";
    public final static String TableName_Timer = "timer";
    public final static String TableName_History = "history";
    public final static String Create_TableName_Timer = "create table if not exists " + TableName_Timer +"("
            + "_id integer primary key,"  + "startTime long," + "endTime long" +")";
    public final  static String Create_TableName_History = "create table if not exists " + TableName_History +"("
            + "_id integer primary key AUTOINCREMENT,"  + "result BLOB," + "time long" +")";
}
