package com.example.laluna.Model.average.times;

import androidx.annotation.NonNull;

import java.util.Calendar;

class MonthObject extends TimeObject{
    public MonthObject(Calendar date, double value) {
        super(date, value);
    }

    @NonNull
    @Override
    public String toString() {
        return String.valueOf(date.get(Calendar.MONTH) + 1);
    }
}
