package com.gmail.paulolemus14.metronome;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by Paulo on 11/14/2016.
 * <p>
 * This class is used to hold the note bitmap and location data, and will be instantiated
 * within each measure class anonymously.
 */
public class Note {

    private Resources res;

    private NoteType noteType;
    private Bitmap noteBitmap;

    private float x;
    private float y;
    private int value;
    private boolean isRest;

    public Note(Resources res, NoteType noteType, float x, float y, boolean isRest) {

        this.res = res;
        this.noteType = noteType;
        this.x = x + noteType.getXOffset();
        this.y = y + noteType.getYOffset();

        this.isRest = isRest;
        this.value = noteType.getValue();
        this.isRest = true;  // TODO: DELETE
        if (isRest) {
            noteBitmap = BitmapFactory.decodeResource(res, noteType.getResLinkID());
        } else {
            noteBitmap = BitmapFactory.decodeResource(res, noteType.getResNoteID());
        }
    }


    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public int getValue() {
        return value;
    }

    public NoteType getNoteType() {
        return noteType;
    }

    public boolean isRest() {
        return isRest;
    }

    public void setRest(boolean b) {
        isRest = b;
    }

    public Bitmap getBitmap() {
        return noteBitmap;
    }

    public void setLink(boolean b) {
        isRest = b;
    }

    public void setNoteBitmap() {
        if (isRest) {
            noteBitmap = BitmapFactory.decodeResource(res, noteType.getResLinkID());
        } else {
            noteBitmap = BitmapFactory.decodeResource(res, noteType.getResNoteID());
        }
    }

}
