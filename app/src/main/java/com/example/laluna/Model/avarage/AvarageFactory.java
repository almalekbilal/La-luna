package com.example.laluna.Model.avarage;

import com.example.laluna.Model.repository.ExpenseRepository;

import java.util.Calendar;

public class AvarageFactory {

    public static Avarage getDayAvarage(Calendar start, Calendar end, ExpenseRepository expenseRepository){
        return new DayAvarage(start, end, expenseRepository);
    }

    public static Avarage getWeekAvarage(Calendar start, Calendar end, ExpenseRepository expenseRepository){
        return new WeekAvarage(start, end, expenseRepository);
    }

    public static Avarage getMonthAvarage(Calendar start, Calendar end, ExpenseRepository expenseRepository){
        return new MonthAvarage(start, end, expenseRepository);
    }
}
