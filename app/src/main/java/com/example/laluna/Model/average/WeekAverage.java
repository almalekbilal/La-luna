package com.example.laluna.Model.average;

import android.util.Log;

import com.example.laluna.Model.Arithmetic;
import com.example.laluna.Model.DateConverter;
import com.example.laluna.Model.average.times.TimeFactory;
import com.example.laluna.Model.average.times.TimeObject;
import com.example.laluna.Model.repository.ExpenseRepository;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

 class WeekAverage extends Average {

    public WeekAverage(Calendar start, Calendar end, ExpenseRepository expenseRepository) {
        super(start, end, expenseRepository);
    }

    @Override
    public List<TimeObject> makeTimesList() {
        List<TimeObject> list = new ArrayList<>();

        end.add(Calendar.WEEK_OF_YEAR, -1);
        while(end.after(start)){
            Calendar startCalculationDate = (Calendar) end.clone();
            Calendar endCalculationDate = (Calendar) end.clone();

            startCalculationDate.add(Calendar.DAY_OF_MONTH, -end.get(Calendar.DAY_OF_WEEK));
            endCalculationDate.add(Calendar.DAY_OF_MONTH, (7 - end.get(Calendar.DAY_OF_WEEK)) );


            TimeObject week = TimeFactory.getWeekObject((Calendar) end.clone(), calculateWeekValue(startCalculationDate,endCalculationDate));      // Use start and end date to calculate the value instead of 300
            list.add(week);

            end.add(Calendar.WEEK_OF_YEAR, -1);
        }
        return list;
    }

    private int calculateWeekValue(Calendar start, Calendar end){
        Date s = start.getTime();
        Date e = end.getTime();

        s.setHours(0);
        s.setMinutes(0);
        s.setSeconds(0);

        e.setHours(0);
        e.setMinutes(0);
        e.setSeconds(0);

        return Arithmetic.calculateTotalMoneySpent(expenseRepository.getExpensesByStartAndEnd(s,e));

    }
}
