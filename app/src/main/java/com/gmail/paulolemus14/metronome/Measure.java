package com.gmail.paulolemus14.metronome;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import static com.gmail.paulolemus14.metronome.RhythmEditorActivity.DTAG;

/**
 * Created by Paulo on 11/26/2016.
 * <p>
 * Each measure will need to know the bounds of the current canvas and its index in the measureList.
 * Adding or removing measures, each measure will need to get its new index and/or new canvas size.
 * after it knows all of this, it can calculate its bounds and placement, and is ready to be drawn.
 */

public class Measure {

    private Resources res;                  // To draw notes

    private int listIndex;                  // The measure's index in the measureList
    private int canvasX;                    // canvas max X, used to calculate placement
    private int canvasY;                    // canvas max Y, used to calculate placement
    private float topPadding;               // Portrait = 0.2*canvasY, Land = 0.25*canvasY
    private float sidePadding;              // Portrait = 0.05*canvasX, Land = 0.05*canvasX
    private float bottomPadding;            // Portrait = 0.1*canvasY, Land = 0.2*canvasY
    private float usableHeight;             // canvasY - bottom - top
    private int orient;                     // Current Orientation

    private float barStartX;                // Measure starting X location on canvas
    private float barEndX;                  // Measure ending X location on canvas
    private float barY;                     // Measure Y value on canvas
    private float divDiffY = 9f;            // constant for now

    private float fractX;                   // Used for placing notes. This is 1/18 of measure length

    private int timeSigNum;                 // default, how many beats in a measure | noteval=note*TS_num*100
    private int timeSigDen;                 // default, note value that represents one beat
    private int maxCount = 400;             // default, maxCount = 100* TS_den
    private int currentVal = 0;             // current note we are on


    private List<Note> noteList = new ArrayList<>();


    public Measure(Resources res, int num, int den, int canvasX, int canvasY) {    // Still need index

        this.res = res;
        timeSigNum = num;
        timeSigDen = den;
        this.canvasX = canvasX;
        this.canvasY = canvasY;
        maxCount = 100 * timeSigDen;
        orient = res.getConfiguration().orientation;

        if (orient == Configuration.ORIENTATION_PORTRAIT) {
            topPadding = 0.25f * canvasY;
            sidePadding = 0.05f * canvasX;
            bottomPadding = 0.15f * canvasY;
        } else {
            topPadding = 0.3f * canvasY;
            sidePadding = 0.05f * canvasX;
            bottomPadding = 0.2f * canvasY;
        }
        usableHeight = canvasY - topPadding - bottomPadding;
    }

    public void addNote(Resources res, NoteType n, boolean isRest) {
        // logic for getting x from width and values
        float noteX = barStartX + fractX + fractX * (currentVal / 25);
        Log.d(DTAG, "NoteX=" + noteX + "\n\tbarStartX=" + barStartX + "\n\tfractX=" + fractX + "\n\tcurrval/25=" + (currentVal / 25));

        noteList.add(new Note(res, n, noteX, barY, isRest));
        currentVal += n.getValue();
        if (currentVal > maxCount) {
            currentVal = maxCount;
        }
    }


    public int getIndex() {
        return listIndex;
    }

    public void setIndex(int index) {
        listIndex = index;
    }

    public int getCurrentVal() {
        return currentVal;
    }

    public void setCurrentVal(int val) {
        currentVal = val;
    }

    public void addToCurrentVal(int val) {
        currentVal += val;
    }

    public void subFromCurrentVal(int val) {
        currentVal -= val;
        if (currentVal < 0) {
            currentVal = 0;
        }
    }

    public int getMaxCount() {
        return maxCount;
    }

    public boolean isFull() {
        return currentVal >= maxCount;
    }

    public List<Note> getNoteList() {
        return noteList;
    }

    public void calcPlacement() {
        if ((listIndex % 2) == 0) {// left side
            barStartX = sidePadding;
            barEndX = canvasX / 2.0f;
            barY = (topPadding + (usableHeight / 3) * (listIndex / 2));  // TODO: Make this scale to #of rows

        } else {// right side
            barStartX = canvasX / 2.0f;
            barEndX = canvasX - sidePadding;
            barY = (topPadding + (usableHeight / 3) * (listIndex / 2));
        }
        fractX = (barEndX - barStartX) / 18;
        Log.d(DTAG, "CALCPLACEMENT FRaCTX=" + fractX);
    }

    // call upon changing canvas/orientation
    public void updateBounds(Resources res, int canvasX, int canvasY) {
        orient = res.getConfiguration().orientation;
        this.canvasX = canvasX;
        this.canvasY = canvasY;

        if (orient == Configuration.ORIENTATION_PORTRAIT) {
            topPadding = 0.25f * canvasY;
            sidePadding = 0.05f * canvasX;
            bottomPadding = 0.15f * canvasY;
        } else {
            topPadding = 0.3f * canvasY;
            sidePadding = 0.05f * canvasX;
            bottomPadding = 0.2f * canvasY;
        }
        usableHeight = canvasY - topPadding - bottomPadding;
        fractX = (barEndX - barStartX) / 18;
        Log.d(DTAG, "UPDATEBOUNDS FRaCTX=" + fractX);
    }

    public float[] getMeasurePlacement() {
        return new float[]{barStartX, barEndX, barY, divDiffY, usableHeight};
    }
}
