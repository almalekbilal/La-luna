package com.example.laluna.Model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateConverter {

    /**
     * A method that takes a string and converts it to date object
     */
    public static Date stringToDateTime(String dateString) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            if (dateString != null) {
                date = sdf.parse(dateString);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * A method that takes a date object and converts it to a string value
     */
    public static String datetimeToString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

    public static Date stringToDate(String dateString) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            if (dateString != null) {
                date = sdf.parse(dateString);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * A method that takes a date object and converts it to a string value
     */
    public static String dateToString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }


    public static Date incrementMonth(Date date){
        Date d = new Date();
        d.setDate(1);
        d.setHours(0);
        d.setMinutes(0);
        d.setSeconds(0);
        d.setYear(date.getYear());

        int month = date.getMonth();
        if(month == 11){
            int year = date.getYear() +1;
            d.setMonth(0);
            d.setYear(year);
        }else{
            d.setMonth(month + 1);
        }
        return d;
    }


    /**
     * The method change the date to the previous month
     */
    public static Date decrementMonth(Date date){
        Date d = new Date();
        d.setDate(1);
        d.setHours(0);
        d.setMinutes(0);
        d.setSeconds(0);
        d.setYear(date.getYear());
        int month = date.getMonth();

        if(month == 0){
            int year = date.getYear() -1;
            d.setMonth(11);
            d.setYear(year);
        }else{
            d.setMonth(month - 1);
        }
        return d;
    }

    public static boolean isThisMonth(Date date){
        Date nowdate = new Date();

        if(nowdate.getYear() == date.getYear() && nowdate.getMonth() == date.getMonth()){
            return true;
        }else{
            return false;
        }
    }


}
