package com.gmail.paulolemus14.metronome;

import android.media.AudioTrack;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MetronomeActivity extends AppCompatActivity {

    public static final String TAG = "MetronomeApp";
    public static String PACKAGE_NAME;

    volatile int flag = 0;
    volatile int flag1 = 0;
    volatile int bp, bt;

    Metronome metronome = new Metronome();
    Converter converter;
    AudioTrack audioTrack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metronome);

        PACKAGE_NAME = getApplicationContext().getPackageName();

        EditText bpm = (EditText) findViewById(R.id.bpmdisplay);
        bpm.setText("120");

        EditText beat = (EditText) findViewById(R.id.beatdisplay);
        beat.setText("4");
        Log.d(TAG, "About to initialize Converter");
        converter = new Converter(getResources());
        Log.d(TAG, "Finished initializing converter");
        playbtnlistener();
        notebtnlistener();
    }

    private void playbtnlistener() {
        final Button play_btn = (Button) findViewById(R.id.btn_play);

        play_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(flag == 0 && flag1 == 0){
                    metronome = new Metronome();
                    Toast.makeText(MetronomeActivity.this, "Playing", Toast.LENGTH_LONG).show();

                    EditText beat = (EditText) findViewById(R.id.beatdisplay);
                    bt = Integer.parseInt(beat.getText().toString());
                    EditText bpm = (EditText) findViewById(R.id.bpmdisplay);
                    bp = Integer.parseInt(bpm.getText().toString());

                    metronome.setBeat(bt);
                    metronome.setbpm(bp);
                    new Thread(new Runnable() {
                        public void run() {
                            metronome.play();
                        }
                    }).start();
                    flag ++;
                }
                else if(flag == 0 && flag1 == 1){
                    converter.stop();
                    flag1 = 0;
                }
                else if(flag == 1 && flag1 == 0){
                    metronome.stop();
                    flag = 0;
                }
                else{
                    metronome.stop();
                    converter.stop();
                    flag = 0;
                    flag1 = 0;
                }
            }
        });
    }

    private void notebtnlistener() {
        final Button note_btn = (Button) findViewById(R.id.btn_note);

        note_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Clicked button for Notes");
                //note.play();
                if(flag == 0 && flag1 == 0){
                    metronome = new Metronome();
                    converter = new Converter(getResources());
                    EditText beat = (EditText) findViewById(R.id.beatdisplay);
                    bt = Integer.parseInt(beat.getText().toString());
                    EditText bpm = (EditText) findViewById(R.id.bpmdisplay);
                    bp = Integer.parseInt(bpm.getText().toString());

                    metronome.setBeat(bt);
                    metronome.setbpm(bp);
                    new Thread(new Runnable() {
                        public void run() {
                            converter.play();
                            //metronome.play();
                        }
                    }).start();
                    new Thread(new Runnable() {
                        public void run() {
                            metronome.play();
                        }
                    }).start();
                    flag1 ++;
                }
                else if(flag == 1 && flag1 == 0){
                    metronome.stop();
                    flag = 0;
                }
                else if(flag == 0 && flag1 == 1){
                    converter.stop();
                    flag1 = 0;
                }
                else{
                    metronome.stop();
                    converter.stop();
                    flag = 0;
                    flag1 = 0;
                }
            }
        });
    }
}