package com.example.laluna.Model.average.times;

import java.util.Calendar;

public class TimeFactory {

    public static TimeObject getDayObject(Calendar calendar, double value){
        return new DayObject(calendar, value);
    }

    public static TimeObject getWeekObject(Calendar calendar, double value){
        return new WeekObject(calendar, value);
    }
    public static TimeObject getMonthObject(Calendar calendar, double value){
        return new MonthObject(calendar, value);
    }
}
