package com.example.laluna;

import com.example.laluna.Model.average.times.TimeFactory;
import com.example.laluna.Model.average.times.TimeObject;

import static org.junit.Assert.*;
import org.junit.Test;
import java.util.Calendar;
import java.util.Date;

public class TimeObjectTest {

    private TimeObject object;


    @Test
    public void dayObjectToStringTest(){

        object = TimeFactory.getDayObject(Calendar.getInstance(), 50);

        String actual = object.toString();
        Date date = new Date();
        int month = date.getMonth()+1;
        int day = date.getDate();
        String expected = day+"/"+month;

        assertEquals(expected, actual);
    }

    @Test
    public void weekObjectToStringTest(){

        object = TimeFactory.getWeekObject(Calendar.getInstance(), 50);


        String actual = object.toString();
        int week = Calendar.getInstance().get(Calendar.WEEK_OF_YEAR); // Why does this return 2 since we are in week 1?????
        String expected = week + "";

        assertEquals(expected, actual);
    }

    @Test
    public void monthObjectToStringTest(){

        object = TimeFactory.getMonthObject(Calendar.getInstance(), 50);

        String actual = object.toString();
        Date date = new Date();
        int month = date.getMonth()+1;
        String expected = month + "";

        assertEquals(expected, actual);
    }


}
