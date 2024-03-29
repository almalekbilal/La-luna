package com.example.laluna.Model.average.times;

import androidx.annotation.NonNull;

import java.util.Calendar;

class DayObject extends TimeObject{

    public DayObject(Calendar date, double value) {
        super(date, value);
    }

    @NonNull
    @Override
    public String toString() {
        String text = date.get(Calendar.DAY_OF_MONTH) + "/" + (date.get(Calendar.MONTH)+1);
        return text;
    }
}
