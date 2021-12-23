package com.example.laluna.Model.avarage;

import com.example.laluna.Model.avarage.times.TimeFactory;
import com.example.laluna.Model.avarage.times.TimeObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

class DayAvarage extends Avarage{

    public DayAvarage(Calendar start, Calendar end) {
        super(start, end);
    }

    @Override
    List<TimeObject> makeTimesList() {
        List<TimeObject> list = new ArrayList<>();
        while(end.after(start)){
            end.add(Calendar.DAY_OF_MONTH, -1);
            TimeObject day = TimeFactory.getDayObject(end, 200);    // calculate the value and pup it instead of 200
            list.add(day);
        }
        return list;
    }
}
