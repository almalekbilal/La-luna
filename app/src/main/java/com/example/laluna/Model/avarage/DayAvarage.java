package com.example.laluna.Model.avarage;

import com.example.laluna.Model.Arithmetic;
import com.example.laluna.Model.avarage.times.TimeFactory;
import com.example.laluna.Model.avarage.times.TimeObject;
import com.example.laluna.Model.repository.ExpenseRepository;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

 class DayAvarage extends Avarage{

    public DayAvarage(Calendar start, Calendar end, ExpenseRepository expenseRepository) {
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
        start.setHours(23);
        start.setMinutes(59);
        start.setSeconds(59);

        return Arithmetic.calculateTotalMoneySpent(expenseRepository.getExpensesByStartAndEnd(start,end));

    }
}
