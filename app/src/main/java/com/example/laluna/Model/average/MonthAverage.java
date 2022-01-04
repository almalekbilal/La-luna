package com.example.laluna.Model.average;

import android.util.Log;

import com.example.laluna.Model.Arithmetic;
import com.example.laluna.Model.average.times.TimeFactory;
import com.example.laluna.Model.average.times.TimeObject;
import com.example.laluna.Model.repository.ExpenseRepository;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

    class MonthAverage extends Average {

    public MonthAverage(Calendar start, Calendar end, ExpenseRepository expenseRepository) {
        super(start, end, expenseRepository);
    }

    @Override
    List<TimeObject> makeTimesList() {
        List<TimeObject> list = new ArrayList<>();
        end.add(Calendar.DAY_OF_MONTH, 1);
        end.add(Calendar.MONTH, -1);
        while(end.after(start)){
            Calendar startCalculationDate = (Calendar) end.clone();
            Calendar endCalculationDate = (Calendar) end.clone();

            startCalculationDate.add(Calendar.DAY_OF_MONTH, -end.get(Calendar.DAY_OF_MONTH));
            endCalculationDate.add(Calendar.MONTH, 1 );
            endCalculationDate.add(Calendar.DAY_OF_MONTH, -endCalculationDate.get(Calendar.DAY_OF_MONTH));


            TimeObject month = TimeFactory.getMonthObject((Calendar)end.clone(), calculateMonthValue(startCalculationDate, endCalculationDate));      // Use start and end date to calculate the value instead of 300
            list.add(month);
            end.add(Calendar.MONTH, -1);
        }
        return list;
    }

    private int calculateMonthValue(Calendar start, Calendar end){
        Date s = start.getTime();
        Date e = end.getTime();

        s.setHours(0);
        s.setMinutes(0);
        s.setSeconds(0);

        e.setHours(23);
        e.setMinutes(59);
        e.setSeconds(59);

        return Arithmetic.calculateTotalMoneySpent(expenseRepository.getExpensesByStartAndEnd(s,e));

    }
}
