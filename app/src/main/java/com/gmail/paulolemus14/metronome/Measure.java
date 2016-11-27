package com.gmail.paulolemus14.metronome;

import java.util.List;

/**
 * Created by Paulo on 11/26/2016.
 */

public class Measure {

    private int timeSigNum, timeSigDen;
    private int maxCount;
    private List<Note> noteContainer;


    public Measure(int num, int den) {
        timeSigNum = num;
        timeSigDen = den;
    }
}
