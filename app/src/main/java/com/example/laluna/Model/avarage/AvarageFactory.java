package com.example.laluna.Model.avarage;

import java.util.Calendar;

public class AvarageFactory {

    public static Avarage getDayAvarage(Calendar start, Calendar end){
        return new DayAvarage(start, end);
    }

    public static Avarage getWeekAvarage(Calendar start, Calendar end){
        return new WeekAvarage(start, end);
    }

    public static Avarage getMonthAvarage(Calendar start, Calendar end){
        return new MonthAvarage(start, end);
    }
}
