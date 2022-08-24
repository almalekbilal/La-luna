package com.example.laluna.Model.average;

import com.example.laluna.Model.exceptions.IrrelevantDateException;
import com.example.laluna.Model.repository.ExpenseRepository;

import java.util.Calendar;

public class AverageFactory {

    public static Average getDayAvarage(Calendar start, Calendar end, ExpenseRepository expenseRepository) throws IrrelevantDateException {
        return new DayAverage(start, end, expenseRepository);
    }

    public static Average getWeekAvarage(Calendar start, Calendar end, ExpenseRepository expenseRepository) throws IrrelevantDateException {
        return new WeekAverage(start, end, expenseRepository);
    }

    public static Average getMonthAvarage(Calendar start, Calendar end, ExpenseRepository expenseRepository) throws IrrelevantDateException {
        return new MonthAverage(start, end, expenseRepository);
    }
}
