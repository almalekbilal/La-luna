package com.example.laluna;

import com.example.laluna.Model.DateConverter;

import static org.junit.Assert.*;
import org.junit.Test;

import java.util.Date;

public class DateConverterTest {

    @Test
    public void stringToDateTest(){

        Date date = DateConverter.stringToDate("2022-01-05");
        Date expectedDate = new Date(2022,01,05);

        boolean actual = date.getYear()+1900==expectedDate.getYear() && date.getMonth()+1==expectedDate.getMonth() && date.getDate()==expectedDate.getDate();
        assertTrue(actual);

    }

    @Test
    public void dateToStringTest(){

        String date = DateConverter.dateToString(new Date(122,00,05));

        String expectedString = "2022-01-05";

        assertEquals(expectedString,date);

    }


    @Test
    public void stringToDateTimeTest(){
        Date date = DateConverter.stringToDate("2022-01-05 02:14:50");
        Date expectedDate = new Date(2022,01,05,02,14,50);

        boolean actual = date.getYear()+1900==expectedDate.getYear() && date.getMonth()+1==expectedDate.getMonth() &&
                date.getDate()==expectedDate.getDate() && date.getHours() == expectedDate.getHours() && date.getMinutes() == expectedDate.getMinutes()
                && date.getSeconds() == date.getSeconds();

        assertTrue(actual);

    }

    @Test
    public void datetimeToStringTest(){
        String dateToString = DateConverter.datetimeToString(new Date(121,11,30,5,4,1));
        assertEquals("2021-12-30 05:04:01",dateToString);
    }


    @Test
    public void incrementMonthTest(){
        Date date = new Date(2022,04,03);
        Date newDate = DateConverter.incrementMonth(date);

        assertEquals(5,newDate.getMonth());
    }

    @Test
    public void decrementMonthTest(){
        Date date = new Date(2022,04,03);
        Date newDate = DateConverter.decrementMonth(date);

        assertEquals(3,newDate.getMonth());
    }

    @Test
    public void isThisMonthTest(){

        Date date = new Date();

        boolean thisMonth = DateConverter.isThisMonth(date);

        assertTrue(thisMonth);

    }

}
