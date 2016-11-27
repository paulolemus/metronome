package com.gmail.paulolemus14.metronome;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by Paulo on 11/14/2016.
 *
 * This class is used to hold the note bitmap and location data, and will be instantiated
 * within each measure class anonymously.
 */
public class Note {

    private Context context;
    private Resources res;
    private Bitmap wholeNote;
    private Bitmap halfNote;
    private Bitmap quarterNote;

    private double x, y;                // Location on canvas
    private double scale;
    private int val;                    // 1 = whole, 2 = half, 3 = quarter, 4 = eighth, 5 = 16th
    private boolean isRest;

    public Note(Context c, double x, double y, int type, Resources rec){

        this.context = c;
        this.x = x;
        this.y = y;
        val = type;
        this.res = rec;
    }

    public void init(){
        wholeNote = BitmapFactory.decodeResource(res, R.drawable.whole_note);
        halfNote = BitmapFactory.decodeResource(res, R.drawable.whole_note);
        quarterNote = BitmapFactory.decodeResource(res, R.drawable.whole_note);
    }
    public Bitmap render(){

        switch(val){
            case 1: return wholeNote;       // Whole
            case 2: return halfNote;        // Half
            case 3: return quarterNote;     // Quarter
            case 4: return quarterNote;     // Eighth
            case 5: return quarterNote;     // 16th
            default: return quarterNote;
        }
    }


}
