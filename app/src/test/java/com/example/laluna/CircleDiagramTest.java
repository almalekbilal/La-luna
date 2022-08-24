package com.example.laluna;

import static org.junit.Assert.*;

import android.graphics.Color;

import com.example.laluna.Model.circleDiagrams.CircleDiagram;

import org.junit.Test;

public class CircleDiagramTest {

    private CircleDiagram circleDiagram;



    @Test
    public void warningColorTest(){
        circleDiagram = new CircleDiagram(150, 100, Color.BLACK);

        boolean isRed = circleDiagram.getWarningColor() == Color.RED;

        assertTrue(isRed);
    }

    @Test
    public void noWarningColorTest(){
        circleDiagram = new CircleDiagram(100, 200, Color.BLACK);

        boolean isWhite = circleDiagram.getWarningColor() == Color.WHITE;

        assertTrue(isWhite);
    }
}
