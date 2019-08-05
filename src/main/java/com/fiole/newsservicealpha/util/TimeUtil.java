package com.fiole.newsservicealpha.util;

import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Slf4j
public class TimeUtil {
    public static int getTimeFromNow2NextZore(){
        long passedTime = (new Date().getTime() - getTodayZeroOClock().getTime()) / 1000;
        return (int)(3600 * 24 - passedTime);
    }

    public static Date getTodayZeroOClock(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR, -12);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    public static Date string2Date(String stringDate){
        String regex = "[^0-9]";
        String[] splitTime = stringDate.split(regex);
        if (splitTime.length < 5){
            return new Date();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        StringBuilder sb = new StringBuilder();
        sb.append(splitTime[0]);
        sb.append("-");
        sb.append(splitTime[1]);
        sb.append("-");
        sb.append(splitTime[2]);
        sb.append(' ');
        sb.append(splitTime[3]);
        sb.append(':');
        sb.append(splitTime[4]);
        sb.append(':');
        sb.append("00");
        Date date;
        try {
            date = sdf.parse(sb.toString());
        } catch (ParseException e) {
            log.error("Wrong string time format");
            date = new Date();
        }
        return date;
    }
}
