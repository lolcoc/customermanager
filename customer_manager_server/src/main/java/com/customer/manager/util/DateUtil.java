package com.customer.manager.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    public static Date stringToDate(String str){
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String getMillisString(){
        DateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String millisString = format.format(Calendar.getInstance().getTime());
        return millisString;
    }

}
