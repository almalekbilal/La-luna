package com.example.laluna.Model.avarage;

import com.example.laluna.Model.avarage.times.TimeFactory;
import com.example.laluna.Model.avarage.times.TimeObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

class WeekAvarage extends Avarage {

    public WeekAvarage(Calendar start, Calendar end) {
        super(start, end);
    }

    @Override
    List<TimeObject> makeTimesList() {
        List<TimeObject> list = new ArrayList<>();
        end.add(Calendar.DAY_OF_MONTH, -7);
        while(end.after(start)){
            Calendar startCalculationDate = (Calendar) end.clone();
            Calendar endCalculationDate = (Calendar) end.clone();

            startCalculationDate.add(Calendar.DAY_OF_MONTH, -end.get(Calendar.DAY_OF_WEEK));
            endCalculationDate.add(Calendar.DAY_OF_MONTH, (7 - end.get(Calendar.DAY_OF_WEEK)) );


            TimeObject week = TimeFactory.getWeekObject(end, 300);      // Use start and end date to calculate the value instead of 300
            list.add(week);
            end.add(Calendar.DAY_OF_MONTH, -7);
        }
        return list;
    }
}
