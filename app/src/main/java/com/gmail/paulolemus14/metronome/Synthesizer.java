package com.gmail.paulolemus14.metronome;


import android.annotation.TargetApi;
import android.media.AudioAttributes;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.media.SoundPool;
import android.os.Build;

/**
 * Created by Paulo on 11/6/2016.
 */



// To create sound files that are ready to play in the soundpool
public class Synthesizer {

    private double BPM;
    private int sampleRate;         // 4kHz sampleRate = 4k measurements/second
    private double sound;
    private double delay;
    private SoundPool spPlayer;
    private AudioTrack atPlayer;
    private AudioAttributes aa;


    public Synthesizer(int sampleRate){

        this.sampleRate = sampleRate;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) makeNewSP();
        else makeOldSP();

        makeNewAT();
    }

    public double[] makeSineWave(int samples, int sampleRate, double frequency){
        double[] sample = new double[samples];
        for(int i = 0; i<samples;i++){
            sample[i] = Math.sin(2*Math.PI*i/(sampleRate/frequency));
        }
        return sample;
    }
    public byte[] makePCM(double[] samples){
        byte[] synthSound = new byte[2*samples.length];
        int index = 0;
        for(double sample : samples){
            short maxSample = (short) ((sample * Short.MAX_VALUE));

            synthSound[index++] = (byte) (maxSample & 0x00ff);
            synthSound[index++] = (byte) ((maxSample & 0xff00) >>> 8);
        }
        return synthSound;
    }


    @SuppressWarnings("deprecation")
    protected void makeOldSP(){
        spPlayer = new SoundPool(3, AudioManager.STREAM_MUSIC, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    protected void makeNewSP(){
        aa = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build();

        spPlayer = new SoundPool.Builder()
                .setAudioAttributes(aa)
                .setMaxStreams(3)
                .build();
    }
    @SuppressWarnings("deprecation")
    protected void makeNewAT(){
        atPlayer = new AudioTrack(AudioManager.STREAM_MUSIC,
                sampleRate,
                AudioFormat.CHANNEL_CONFIGURATION_MONO,
                AudioFormat.ENCODING_PCM_16BIT,
                sampleRate,
                AudioTrack.MODE_STREAM);
    }
    public void writeSound(double[] samples){
        byte[] synthSound = makePCM(samples);
        atPlayer.write(synthSound, 0, synthSound.length);
    }
    public void destroyAT(){
        atPlayer.stop();
        atPlayer.release();
    }
}
