package com.gmail.paulolemus14.metronome;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import static com.gmail.paulolemus14.metronome.RhythmEditorActivity.DTAG;


/**
 * Created by Paulo on 11/14/2016.
 *
 * Primary class for editor logic and measure handling.
 */
public class CustomView extends View {


    Bitmap background;
    Bitmap whole, quarter;
    float x=0, y=0;
    NoteType currNoteType;
    Bitmap currentNote;


    List<Measure> measureList = new ArrayList<>();


    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);

        background = BitmapFactory.decodeResource(getResources(), R.drawable.paper_bg);
        quarter = BitmapFactory.decodeResource(getResources(), R.drawable.quarter_note_x);
        whole = BitmapFactory.decodeResource(getResources(), R.drawable.whole_note);
        currentNote = whole;
    }


    public void setX(float x){this.x = x;}
    public void setY(float y){this.y = y;}

    public void setNoteType(NoteType n) {
        currNoteType = n;
        setCurrentNote();
    }

    private void setCurrentNote() {
        switch (currNoteType) {
            case WHOLE:
                currentNote = whole;
                break;
            case QUARTER:
                currentNote = quarter;
                break;
            default:
                break;
        }
        invalidate();
    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(background, canvas.getWidth()-background.getWidth()/2, canvas.getHeight()-background.getHeight()/2, null);
        canvas.drawBitmap(currentNote, x - currentNote.getWidth() / 2, y - currentNote.getHeight() / 2, null);

    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        Log.d(DTAG, "CustomView: Touched");
        x = event.getX();
        y = event.getY();
        invalidate();

        return false;
    }
}
