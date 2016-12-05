package com.gmail.paulolemus14.metronome;


import android.util.Log;

import static com.gmail.paulolemus14.metronome.MetronomeActivity.TAG;


public class Metronome {

    private final int tick = 700; // samples of tick
    private double bpm;
    private int beat = 4;
    private int noteValue;
    private int silence;
    private double beatSound = 250;
    private double sound = 500;     //440
    private boolean play = true;

    private AudioGenerator audioGenerator = new AudioGenerator(8000);

    public Metronome() {
        audioGenerator.createPlayer();
    }

    public void calcSilence() {
        silence = (int) (((60 / bpm) * 8000) - tick);
    }

    public void play() {
        calcSilence();
        play = true;
        double[] tick =
                audioGenerator.getSineWave(this.tick, 8000, beatSound);
        double[] tock =
                audioGenerator.getSineWave(this.tick, 8000, sound);
        double silence = 0;
        double[] sound = new double[8000];
        int t = 0, s = 0, b = 0;
        do {
            for (int i = 0; i < sound.length && play; i++) {
                if (t < this.tick) {

                    if (b == 0)
                        sound[i] = tock[t];
                    else
                        sound[i] = tick[t];
                    t++;
                } else {
                    sound[i] = silence;
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
            Log.d(TAG, "snare length" + sound.length);
            audioGenerator.writeSound(sound);
        } while (play);
    }

    public void stop() {
        play = false;
        audioGenerator.destroyAudioTrack();
    }

    public void reset() {
        play = false;
    }


    public void setbpm(int bpm) {
        this.bpm = bpm;
    }

    public void setBeat(int beat) {
        this.beat = beat;
    }

    public int getbpm() {
        int b = (int) this.bpm;
        return b;
    }
}