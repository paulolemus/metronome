package com.gmail.paulolemus14.metronome;

import java.util.List;

/**
 * Created by Paulo on 12/5/2016.
 * <p>
 * Does not work
 */

public class LineHandler {

    private List<Note> noteList;
    private Note note1;
    private Note note2;
    private Note tempNote;

    private boolean foundFirst = false;

    private float x1;
    private float x2;
    private float y;
    private float xOffset;


    public LineHandler(List<Note> noteList) {
        this.noteList = noteList;
    }

    public void parseList() {
        for (Note n : noteList) {
            if (foundFirst || n.getNoteType() == NoteType.EIGHTH || n.getNoteType() == NoteType.SIXTEENTH) {
                x1 = n.getX();
                y = n.getY();
                foundFirst = true;
//            } else if(){
//
//            }
            }

        }
    }
}
