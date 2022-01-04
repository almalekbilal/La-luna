package com.example.laluna;

import static org.junit.Assert.*;

import com.example.laluna.Model.Category;
import com.example.laluna.Model.CategoryWithExpenses;
import com.example.laluna.Model.Expense;
import com.example.laluna.Model.circleDiagrams.CategoryCircleDiagram;
import com.example.laluna.Model.circleDiagrams.CircleDiagram;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

public class CategoryCircleDiagramTest {

    private CircleDiagram categoryCircleDiagram;


    @Test
    public void getCenterTextTest(){
        Category category = new Category(5, 1000, "Test",
                5, "#FFFFFF", new Date(), null);

        categoryCircleDiagram = new CategoryCircleDiagram(new CategoryWithExpenses(category,
                new ArrayList<Expense>(), 1000));

        String expected = "Test" + " \n" + 0 + " Kr";

        assertEquals(expected, categoryCircleDiagram.getCenterText());
    }
}
