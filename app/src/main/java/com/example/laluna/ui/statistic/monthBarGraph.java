package com.example.laluna.ui.statistic;

public class monthBarGraph {
    String month;
    int expenses;

    public monthBarGraph(String month, int expenses) {
        this.month = month;
        this.expenses = expenses;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getExpenses() {
        return expenses;
    }

    public void setExpenses(int expenses) {
        this.expenses = expenses;
    }



}
