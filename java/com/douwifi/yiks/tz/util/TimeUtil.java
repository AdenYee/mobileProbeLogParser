package com.douwifi.yiks.tz.util;

/**
 * Created by YiKS on 2016/7/11.
 */
public class TimeUtil {

    /**
     * 时间戳转日期
     * @param timestamp 时间戳，以毫秒为单位
     * @param formats   日期格式，如："yyyy-MM-dd HH:mm:ss", "HH:mm:ss"
     * @return
     */
    public static String TimeStamp2Date(Long timestamp, String formats){
        String date = new java.text.SimpleDateFormat(formats).format(new java.util.Date(timestamp));
        return date;
    }
}
