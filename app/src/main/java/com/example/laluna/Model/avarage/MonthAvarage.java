package com.example.laluna.Model.avarage;

import com.example.laluna.Model.avarage.times.TimeFactory;
import com.example.laluna.Model.avarage.times.TimeObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

class MonthAvarage extends Avarage{
    public MonthAvarage(Calendar start, Calendar end) {
        super(start, end);
    }

    @Override
    List<TimeObject> makeTimesList() {
        List<TimeObject> list = new ArrayList<>();
        end.add(Calendar.MONTH, -1);
        while(end.after(start)){
            Calendar startCalculationDate = (Calendar) end.clone();
            Calendar endCalculationDate = (Calendar) end.clone();

            startCalculationDate.add(Calendar.DAY_OF_MONTH, -end.get(Calendar.DAY_OF_MONTH));
            endCalculationDate.add(Calendar.MONTH, 1 );
            endCalculationDate.add(Calendar.DAY_OF_MONTH, -endCalculationDate.get(Calendar.DAY_OF_MONTH));


            TimeObject week = TimeFactory.getWeekObject(end, 300);      // Use start and end date to calculate the value instead of 300
            list.add(week);
            end.add(Calendar.MONTH, -1);
        }
        return list;
    }
}
