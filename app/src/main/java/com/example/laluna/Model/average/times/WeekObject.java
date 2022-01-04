package com.example.laluna.Model.average.times;

import androidx.annotation.NonNull;

import java.util.Calendar;

class WeekObject extends TimeObject{

    public WeekObject(Calendar date, double value) {
        super(date, value);
    }

    @NonNull
    @Override
    public String toString() {

        return "v."+ String.valueOf(date.get(Calendar.WEEK_OF_YEAR));
    }
}
