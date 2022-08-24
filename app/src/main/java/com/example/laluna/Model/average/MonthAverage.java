package com.example.laluna.Model.average;

import com.example.laluna.Model.calculations.Arithmetic;
import com.example.laluna.Model.average.times.TimeFactory;
import com.example.laluna.Model.average.times.TimeObject;
import com.example.laluna.Model.exceptions.IrrelevantDateException;
import com.example.laluna.Model.repository.ExpenseRepository;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

    class MonthAverage extends Average {

    public MonthAverage(Calendar start, Calendar end, ExpenseRepository expenseRepository) throws IrrelevantDateException {
        super(start, end, expenseRepository);
    }

    @Override
    List<TimeObject> makeTimesList() {
        List<TimeObject> list = new ArrayList<>();

        Calendar startCalculationDate = (Calendar) start.clone();
        startCalculationDate.add(Calendar.DAY_OF_MONTH, -1);
        Calendar endCalculationDate = (Calendar) end.clone();
        while(endCalculationDate.after(startCalculationDate)){

            TimeObject month = TimeFactory.getMonthObject((Calendar)startCalculationDate.clone(), calculateMonthValue(startCalculationDate));      // Use start and end date to calculate the value instead of 300
            list.add(month);

            startCalculationDate.add(Calendar.MONTH, 1);

        }

        return list;
    }

    private int calculateMonthValue(Calendar month){
        Date s = month.getTime();
        Date e = month.getTime();

        s.setHours(0);
        s.setMinutes(0);
        s.setSeconds(0);
        s.setDate(1);

        e.setHours(23);
        e.setMinutes(59);
        e.setSeconds(59);
        e.setDate(month.getActualMaximum(Calendar.DATE));

        return Arithmetic.calculateTotalMoneySpent(expenseRepository.getExpensesByStartAndEnd(s,e));

    }
}
