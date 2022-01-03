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

 class DayAverage extends Average {

    public DayAverage(Calendar start, Calendar end, ExpenseRepository expenseRepository) {
        super(start, end, expenseRepository);
    }

    @Override
    List<TimeObject> makeTimesList() {
        List<TimeObject> list = new ArrayList<>();

        while(end.after(start)){
            end.add(Calendar.DAY_OF_MONTH, -1);
            TimeObject day = TimeFactory.getDayObject(end, calculateDayValue(end.getTime()));    // calculate the value and pup it instead of 200

            list.add(day);
        }
        return list;
    }

    private int calculateDayValue(Date date){
        Date start = (Date)date.clone();
        start.setHours(0);
        start.setMinutes(0);
        start.setSeconds(0);

        Date end = (Date)date.clone();
        end.setHours(23);
        end.setMinutes(59);
        end.setSeconds(59);

        return Arithmetic.calculateTotalMoneySpent(expenseRepository.getExpensesByStartAndEnd(start,end));

    }
}
