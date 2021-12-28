package com.example.laluna.Model.circleDiagrams;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;

public class CircleDiagram {

    private int spent,total;
    private List<Integer> colors;

    public CircleDiagram(int spent, int total, int color){

        this.spent = spent;
        this.total = total;

        colors = new ArrayList<>();
        colors.add(color);
        colors.add(Color.rgb(203, 204, 196));
    }

    public int getSpent() {
        return spent;
    }

    public int getTotal() {
        return total;
    }

    public List<Integer> getColors() {
        return colors;
    }

    public float getSpentRate(){
        return (spent/total) * 100;
    }

    public float getTotalRate(){
        return 100 - getSpentRate();
    }


}
