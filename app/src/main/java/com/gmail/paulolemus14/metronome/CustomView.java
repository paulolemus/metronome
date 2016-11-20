package com.gmail.paulolemus14.metronome;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


/**
 * Created by Paulo on 11/14/2016.
 */
public class CustomView extends View {

    Bitmap whole;
    Bitmap background;

    float x=0, y=0;

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);

        background = BitmapFactory.decodeResource(getResources(), R.drawable.paper_bg);
        whole = BitmapFactory.decodeResource(getResources(), R.drawable.whole);


    }


    public void setX(float x){this.x = x;}
    public void setY(float y){this.y = y;}

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(background, canvas.getWidth()-background.getWidth()/2, canvas.getHeight()-background.getHeight()/2, null);
        canvas.drawBitmap(whole, x-whole.getWidth()/2, y-whole.getHeight()/2, null);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        x = event.getX();
        y = event.getY();
        invalidate();

        return false;
    }
}
