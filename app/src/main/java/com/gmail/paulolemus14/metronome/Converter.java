package com.gmail.paulolemus14.metronome;

import android.content.res.Resources;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import static com.gmail.paulolemus14.metronome.MetronomeActivity.TAG;


public class Converter {

    AudioTrack audio = new AudioTrack(AudioManager.STREAM_MUSIC,
            8000, AudioFormat.CHANNEL_OUT_STEREO,
            AudioFormat.ENCODING_PCM_16BIT, 8000,
            AudioTrack.MODE_STREAM);
    private Resources res;
    private boolean flag = true;
    private int run = 1;
    private int silence;
    private int sampleRate = 32000;
    AudioTrack test = new AudioTrack(AudioManager.STREAM_MUSIC,
            sampleRate, AudioFormat.CHANNEL_OUT_MONO,
            AudioFormat.ENCODING_PCM_8BIT, sampleRate,
            AudioTrack.MODE_STREAM);
    private int beat = 1;
    private double bpm = 120;
    private double measure = (beat / (60 * bpm));
    private double snared;
    private byte[] snare;
    private int vale;
    private ArrayList<ArrayList> bar;

    public Converter(Resources res) {
        this.res = res;
    }

    public void play() {
        Log.d(TAG, "Enter play mthod");
        flag = true;
        InputStream is = res.openRawResource(R.raw.beat);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        BufferedInputStream in = new BufferedInputStream(is);
        DataInputStream dis = new DataInputStream(in);
        byte[] convertedBeat = new byte[32000];
        int i = 0;

        try {
            Log.d(TAG, "Entered tryloop");
            while (dis.available() > 0) {
                convertedBeat[i] = dis.readByte();
                i++;
            }
            dis.close();
        } catch (IOException e) {
            Log.d(TAG, "Failed to read most or entire input");
        }
        snare = convertedBeat;
        Log.d(TAG, "Ended loop at i=" + i);
        //Log.d(TAG, "snare length" + snare.length);
        calcsound(bar);

        //Log.d(TAG, "sample rate-" + audio.getSampleRate());
        //Log.d(TAG, "buffer size-" + audio.getMinBufferSize(8000, AudioFormat.CHANNEL_OUT_STEREO,AudioFormat.ENCODING_PCM_16BIT));
    }


    public void calcSilence() {
        silence = (int) (((60 / bpm) * 8000) - snare.length);
    }

    void convert() {
        Log.d(TAG, "Enter play");
        //calcsound();
        Log.d(TAG, "leave calc");
    }

    void calcsound(ArrayList<ArrayList> bar) {

        Log.d(TAG, "Enter calc");
        byte[] silence = new byte[4000];
        byte[] sound = new byte[32000];
        audio.play();
        int t = 0, s = 0, b = 0;
        do {
            for (int j = 0; j < bar.size(); j++) {
                for (int i = 0; i < sound.length && flag; i++) {
                    if (bar.get(j).get(b) == "1" && t < 12000) {
                        sound[i] = snare[t];
                        this.silence = 20000;
                        t++;
                        b = 4;
                    } else if (bar.get(j).get(b) == "0.5" && t < 12000) {
                        sound[i] = snare[t];
                        this.silence = 4000;
                        t++;
                        b += 2;
                    } else if (bar.get(j).get(b) == "0.25" && t < 7000) {
                        sound[i] = snare[t];
                        this.silence = 1000;
                        t++;
                        b++;
                    } else if (bar.get(j).get(b) == "0.125" && t < 3000) {
                        sound[i] = snare[t];
                        this.silence = 1000;
                        t++;
                    } else {
                        sound[i] = silence[0];
                        s++;
                        if (s >= this.silence) {
                            t = 0;
                            s = 0;
                            b++;
                            if (b > (this.beat - 1))
                                b = 0;
                        }
                    }
                }
                audio.write(sound, 0, sound.length);
            }
        } while (flag);
        /*do {
            for(int i=0;i<measure;i++) {
                if(bar[i] == 1) {
                    audio.write(snare, 0, 12608);
                    audio.write(silence, 0, 19392);
                }
                else if(bar[i] == 2){
                    audio.write(snare, 0, 12608);
                    audio.write(silence, 0, silence.length);
                }
                else if(bar[i] == 4){
                    audio.write(snare, 0, 7000);
                    audio.write(silence, 0, 1000);
                }
                else if(bar[i] == 8){
                    audio.write(snare, 0, 3000);
                    audio.write(silence, 0, 1000);
                }
                //audio.write(snare, 0, 7000);
                //audio.write(silence, 0, silence1.length);
                //audio.write(snare, 0, 12608);
                //audio.write(silence, 0, silence.length);
            }
        }while(flag);*/
    }

    void stop() {
        flag = false;
        audio.pause();
        audio.release();
    }

    //Setters
    public void setbpm(int bt) {
        this.bpm = bt;
    }
}
