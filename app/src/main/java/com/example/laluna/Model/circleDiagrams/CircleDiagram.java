package com.example.laluna.Model.circleDiagrams;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;

public class CircleDiagram {

    private int spent,total;
    private float spentRate, totalRate;
    private List<Integer> colors;
    private int warning;

    public CircleDiagram(int spent, int total, int color){

        if(spent > total){
            warning = Color.RED;
            spentRate = ((float)(spent-total)/(float)total) * (float)100;
        }else{
            warning = Color.WHITE;
            spentRate = ((float)(spent)/(float)total) * (float)100;
        }
        totalRate = (float)100 - spentRate;

        this.total = total;
        this.spent = spent;

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
        return spentRate;
    }

    public float getTotalRate(){
        return totalRate;
    }

    public String getCenterText(){
        return spent + " Kr";
    }

    public int getWarningColor(){
        return warning;
    }
}
