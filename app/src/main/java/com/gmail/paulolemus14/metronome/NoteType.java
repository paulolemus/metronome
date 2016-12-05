package com.gmail.paulolemus14.metronome;

/**
 * Created by Paulo on 11/26/2016.
 * <p>
 * This class is used through out the Note class and the CustomView class, primarily in
 * switch statements.
 * field 1: note value modifier. This is how much of a 4/4 bar it takes up.
 * field 2: The ID for the note if it is a note, and not a rest.
 * field 3: the ID for the note if it is a rest.
 */

public enum NoteType {

    WHOLE(1, R.drawable.music_wholenote, R.drawable.music_wholenote, -31, -24),
    HALF(0.5f, R.drawable.music_halfnote, R.drawable.whole_note, -26, -37),
    QUARTER(0.25f, R.drawable.music_quarternote, R.drawable.whole_note, -28, -53),
    EIGHTH(0.125f, R.drawable.music_eighthnote, R.drawable.whole_note, -27, -51),
    SIXTEENTH(0.0625f, R.drawable.music_sixteennote, R.drawable.whole_note, -29, -52);

    private float modifier;
    private int resNoteID;
    private int resRestID;
    private float xOffset;
    private float yOffset;

    NoteType(float modifier, int resNoteID, int resRestID, float xOffset, float yOffset) {
        this.modifier = modifier;
        this.resNoteID = resNoteID;
        this.resRestID = resRestID;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    public float getModifier() {
        return modifier;
    }

    public int getValue() {
        return Math.round(modifier * 400);
    }

    public int getResNoteID() {
        return resNoteID;
    }

    public int getResRestID() {
        return resRestID;
    }

    public float getXOffset() {
        return xOffset;
    }

    public float getYOffset() {
        return yOffset;
    }
}
