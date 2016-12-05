package com.gmail.paulolemus14.metronome;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static com.gmail.paulolemus14.metronome.RhythmEditorActivity.DTAG;


/**
 * Created by Paulo on 11/14/2016.
 * <p/>
 * Primary class for editor logic and measure handling.
 */
public class CustomView extends View {

    final float BAR_LINE_WIDTH = 1f;              // Line width of measure, keep until add zoom functionality
    final float DIV_LINE_WIDTH = 2.0f;            // Line width of bar-dividing line
    final int TS_NUM = 4;                         // Time signature numerator, keep until add ability to change
    final int TS_DEN = 4;                         // Time signature denominator
    final int MAX_BARS = 8;                       // Max number of bars allowed in screen TODO: Add capacity
    private Bitmap BG = BitmapFactory.decodeResource(getResources(),
            R.drawable.paper_bg);                 // The background
    private List<Measure> measureList = new ArrayList<>();  // The list to contain all measures for the canvas
    private int viewWidth;                        // default size of canvas in portrait
    private int viewHeight;                       // default values
    private float x = 0;                          // Used to get canvas pressed x
    private float y = 0;                          // Used to get canvas pressed y

    private Bitmap whole, quarter;                // Bitmaps for two notes values, delete later
    private Bitmap currentNote;                   // Temporary

    private Paint barPaint;                       // Temporary
    private Paint divPaint;
    private Paint titlePaint;
    private Paint subPaint;
    private String currTitle = null;
    private String subTitle = null;

    private NoteType noteType;                    // Set by button presses
    private int currMeasure = 0;                  // Measure index for adding/deleting TODO: Set up
    private int barEditIndex = 0;                 // index of measure adding notes to
    private int barVal = 0;                       // compare this to 400
    private int barMaxCount = 400;                // used for comparing
    private int remainder = 0;                    // remainder of note overflow
    private boolean isRest = false;


    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);

        Log.d(DTAG, "Entered constructor for editor");
        quarter = BitmapFactory.decodeResource(getResources(), R.drawable.quarter_note_x);
        whole = BitmapFactory.decodeResource(getResources(), R.drawable.whole_note);
        currentNote = whole;
        init();
    }


    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void addMeasure() {
        if (measureList.size() < MAX_BARS) {
            measureList.add(new Measure(getResources(), TS_NUM, TS_DEN, viewWidth, viewHeight));
            for (Measure mes : measureList) {
                mes.updateBounds(getResources(), viewWidth, viewHeight);
                mes.setIndex(measureList.indexOf(mes));
                mes.calcPlacement();
            }
            currMeasure++;
            invalidate();
            Log.d(DTAG, "Added measure. Measures: " + measureList.size());
        } else {
            Log.d(DTAG, "Measure Limit already reached. Measures: " + measureList.size());
        }
    }

    public void deleteMeasure() {
        try {
            measureList.remove(currMeasure - 1);
            for (Measure mes : measureList) {
                mes.setIndex(measureList.indexOf(mes));
                mes.calcPlacement();
            }
            currMeasure--;
            invalidate();
            Log.d(DTAG, "deleted measure. Measures: " + measureList.size());
        } catch (Exception e) {
            Log.d(DTAG, "No measures to delete");
        }
        if (currMeasure < 0) currMeasure = 0;
        barEditIndex = 0;
    }

    public void addNote() {
        try {
            Measure m = measureList.get(barEditIndex);
            remainder = 0;
            barVal = m.getCurrentVal();
            barMaxCount = m.getMaxCount();

            if (m.isFull()) {
                // If bar is full, move to the next bar.
                Log.d(DTAG, "Bar was full, going recursive");
                barEditIndex++;
                addNote();
            } else if ((barVal + noteType.getValue() > barMaxCount) && (barVal < barMaxCount)) {
                // if bar will be full, add note, then add remainder to next bar.
                remainder = Math.round(noteType.getValue() - barMaxCount + barVal);
                measureList.get(barEditIndex + 1).addToCurrentVal(remainder);
                Log.d(DTAG, "Bar overflowed, remainder: " + remainder);
                m.addNote(getResources(), noteType, isRest);
                m.setCurrentVal(barMaxCount);
                barEditIndex++;
            } else {
                Log.d(DTAG, "added normally. Barval: " + m.getCurrentVal());
                m.addNote(getResources(), noteType, isRest);
                Log.d(DTAG, "barNewVal: " + m.getCurrentVal());
            }
        } catch (Exception e) {
            Log.d(DTAG, "Entered add loop exception");
            barEditIndex = 0;
            Toast t = Toast.makeText(getContext(), "No space available!", Toast.LENGTH_SHORT);
            t.setGravity(Gravity.CENTER, 0, 0);
            t.show();
        }
    }

    public void deleteNote() {

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(BG, canvas.getWidth() - BG.getWidth() / 2, canvas.getHeight() - BG.getHeight() / 2, null);
        canvas.drawBitmap(currentNote, x - currentNote.getWidth() / 2, y - currentNote.getHeight() / 2, null);
        canvas.drawText(currTitle, viewWidth / 2, viewHeight / 10, titlePaint);

        Log.d(DTAG, "drawing measures, entering loop");
        for (Measure mes : measureList) {
            // 0=startX, 1=endX, 2=Y, 3=Ydiff
            float[] dim = mes.getMeasurePlacement();
            canvas.drawLine(dim[0], dim[2], dim[1], dim[2], barPaint);              // Main measure line
            canvas.drawLine(dim[0], dim[2] + dim[3], dim[0], dim[2] - dim[3], divPaint);// bar front divider
            canvas.drawLine(dim[1], dim[2] + dim[3], dim[1], dim[2] - dim[3], divPaint);// bar end divider

            for (Note n : mes.getNoteList()) {
                canvas.drawBitmap(n.getBitmap(), n.getX(), n.getY(), null);
            }
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        Log.d(DTAG, "Entered onSizeChanged");
        viewWidth = w;
        viewHeight = h;
        try {
            if (measureList.isEmpty()) {
                Log.d(DTAG, "Added bar to empty list");
                addMeasure();
            }
        } catch (Exception e) {
            Log.d(DTAG, "Tried to add initial measure-failed");
        }

        for (Measure mes : measureList) {
            mes.updateBounds(getResources(), w, h);
            mes.setIndex(measureList.indexOf(mes));
            mes.calcPlacement();
        }
        super.onSizeChanged(w, h, oldw, oldh);
        invalidate();
    }


    @Override       // This just grabs x and y location of canvas click
    public boolean onTouchEvent(MotionEvent event) {

        Log.d(DTAG, "CustomView: Touched");
        x = event.getX();
        y = event.getY();
        Log.d(DTAG, "x, y: " + x + " " + y);

        for (int i = 0; i < measureList.size(); i++) {
            Log.d(DTAG, "bar#" + i + "\n\tindex: " + measureList.get(i).getIndex());
            float[] temp = measureList.get(i).getMeasurePlacement();
            Log.d(DTAG, "\tStartX:" + temp[0] + "\n\tEndX:" + temp[1] + "\n\tY:" + temp[2] + "\n\tUsableHeight:" + temp[4] + "\n\tval: " + measureList.get(i).getCurrentVal());
        }
        invalidate();

        return false;
    }

    public void setNoteType(NoteType n) {
        noteType = n;
        Log.d(DTAG, "CurrNoteVal: " + n.getValue());
        setCurrentNote();
    }

    private void setCurrentNote() {
        switch (noteType) {
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

    private void init() {
        barPaint = new Paint();
        barPaint.setColor(Color.BLACK);
        barPaint.setStrokeWidth(BAR_LINE_WIDTH);
        divPaint = new Paint();
        divPaint.setColor(Color.BLACK);
        divPaint.setStrokeWidth(DIV_LINE_WIDTH);

        titlePaint = new Paint();
        titlePaint.setColor(Color.BLACK);
        titlePaint.setTypeface(Typeface.SERIF);
        titlePaint.setTextSize(28);
        titlePaint.setTextAlign(Paint.Align.CENTER);
        titlePaint.setAntiAlias(true);

        subPaint = new Paint();
        subPaint.setColor(Color.BLACK);
        subPaint.setTypeface(Typeface.SERIF);
        subPaint.setTextSize(22);
        subPaint.setTextAlign(Paint.Align.CENTER);
        subPaint.setAntiAlias(true);

        Log.d(DTAG, "Set up paint");
        if (currTitle == null) {
            currTitle = new String("Untitled 1");
        }
    }
}
