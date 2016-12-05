package com.gmail.paulolemus14.metronome;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import java.util.List;

/**
 * Created by Paulo on 12/5/2016.
 */

public class Retainer extends Fragment {

    // Objects to retain

    private Interpreter interpreter;
    private List<List<NoteType>> bigNoteTypeList;
    private List<NoteType> noteTypeList;
    private int numbOfMeasures;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // retain frag
        setRetainInstance(true);
    }

    public Interpreter getInterpreter() {
        return interpreter;
    }

    public void setInterpreter(Interpreter i) {
        interpreter = i;
    }

    public void setData(List<List<NoteType>> bigNoteTypeList, List<NoteType> noteTypeList, int numbOfMeasures) {
        this.bigNoteTypeList = bigNoteTypeList;
        this.noteTypeList = noteTypeList;
        this.numbOfMeasures = numbOfMeasures;
    }

    public List<List<NoteType>> getBigList() {
        return bigNoteTypeList;
    }

    public List<NoteType> getSmallList() {
        return noteTypeList;
    }

    public int getNumbOfMeasures() {
        return numbOfMeasures;
    }

}
