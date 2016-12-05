package com.gmail.paulolemus14.metronome;

import android.content.res.Resources;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import static com.gmail.paulolemus14.metronome.RhythmEditorActivity.DTAG;

/**
 * Created by Paulo on 12/5/2016.
 * <p>
 * This node will take in a arraylist of measures, parse through it and convert it to simple data.
 */

public class Interpreter {


    private List<Measure> measureList;      // This must be null to pass
    private List<Note> noteList;            // this must be null to pass.

    private List<List<NoteType>> bigNoteTypeList;   // These three used to contruct everything on change
    private List<NoteType> noteTypeList;
    private int numbOfMeasures;

    private List<Measure> builtList;


    public Interpreter() {
    }

    public List<Measure> getList() {
        return measureList;
    }

    public void setList(List<Measure> list) {
        measureList = list;
    }

    public void convertList() {

        Log.d(DTAG, "Starting conversion");
        bigNoteTypeList = new ArrayList<>();

        numbOfMeasures = measureList.size();

        // This for loop gets a list of all NoteTypes for all notes in each measure.
        for (int i = 0; i < numbOfMeasures; i++) {

            noteTypeList = new ArrayList<>();
            noteList = measureList.get(i).getNoteList();

            int numbOfNotes = noteList.size();

            for (int j = 0; j < numbOfNotes; j++) {
                // Makes a list of all noteTypes for notes of a measure, saves it
                noteTypeList.add(noteList.get(j).getNoteType());
            }
            bigNoteTypeList.add(noteTypeList);
        }

        Log.d(DTAG, "#Measures: " + numbOfMeasures + "\n\t#NOTETYPE COLS: " + bigNoteTypeList.size()
                + "\n\tNOTESINLAST: " + noteTypeList.size());
        // end
        measureList = null;
        noteList = null;
        builtList = null;
    }

    public void buildList(Resources res, int num, int den, int x, int y) {

        Log.d(DTAG, "Building list");
        builtList = new ArrayList<>();

        // adds all measures;
        for (int i = 0; i < numbOfMeasures; i++) {
            Measure m = new Measure(res, num, den, x, y);
            builtList.add(m);
        }

        // Initializes the measures
        for (Measure mes : builtList) {
            mes.updateBounds(res, x, y);
            mes.setIndex(builtList.indexOf(mes));
            mes.calcPlacement();
        }

        // Get data from bitList
        int bigListSize = bigNoteTypeList.size();

        // For each list in big, get the small list and corresponding meassure.
        for (int i = 0; i < bigListSize; i++) {
            List<NoteType> BNT = bigNoteTypeList.get(i);
            Measure measure = builtList.get(i);

            // For each noteType listed, add the corresponding note
            for (NoteType nt : BNT) {
                measure.addNote(res, nt, false);
            }
        }

    }

    public List<Measure> getBuiltList() {
        return builtList;
    }
}
