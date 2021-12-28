package com.example.laluna.Model.avarage;

import com.example.laluna.Model.Arithmetic;
import com.example.laluna.Model.avarage.times.TimeFactory;
import com.example.laluna.Model.avarage.times.TimeObject;
import com.example.laluna.Model.repository.ExpenseRepository;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

 class WeekAvarage extends Avarage {

    public WeekAvarage(Calendar start, Calendar end, ExpenseRepository expenseRepository) {
        super(start, end, expenseRepository);
    }

    @Override
    public List<TimeObject> makeTimesList() {
        List<TimeObject> list = new ArrayList<>();
        end.add(Calendar.DAY_OF_MONTH, -7);
        while(end.after(start)){
            Calendar startCalculationDate = (Calendar) end.clone();
            Calendar endCalculationDate = (Calendar) end.clone();

            startCalculationDate.add(Calendar.DAY_OF_MONTH, -end.get(Calendar.DAY_OF_WEEK));
            endCalculationDate.add(Calendar.DAY_OF_MONTH, (7 - end.get(Calendar.DAY_OF_WEEK)) );


            TimeObject week = TimeFactory.getWeekObject(end, calculateWeekValue(startCalculationDate,endCalculationDate));      // Use start and end date to calculate the value instead of 300
            list.add(week);
            end.add(Calendar.DAY_OF_MONTH, -7);
        }
        return list;
    }

    private int calculateWeekValue(Calendar start, Calendar end){
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
