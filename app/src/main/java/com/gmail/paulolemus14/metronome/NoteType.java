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

    WHOLE(1, R.drawable.music_wholenote, R.drawable.music_wholenote, -31, -24, -31, 24),
    HALF(0.5f, R.drawable.music_halfnote, R.drawable.music_halfnote, -26, -37, -26, 37),
    QUARTER(0.25f, R.drawable.music_quarternote, R.drawable.music_quarternote, -28, -53, -28, -53),
    EIGHTH(0.125f, R.drawable.music_eighthnote, R.drawable.music_quarternote, -27, -51, -28, -53),
    SIXTEENTH(0.0625f, R.drawable.music_sixteennote, R.drawable.music_quarternote, -29, -52, -28, -53);


//    WHOLE_REST(1, R.drawable.music_halfrest, R.drawable.music_halfrest, 0, 0),
//    HALF_REST(0.5f, R.drawable.music_halfrest, R.drawable.music_halfrest, 0, 0),
//    QUARTER_REST(0.25f, R.drawable.music_quarterrest, R.drawable.music_quarterrest, 0, 0),
//    EIGHTH_REST(0.125f, R.drawable.music_eighthrest, R.drawable.music_eighthrest, 0, 0),
//    SIXTEENTH_REST(0.0625f, R.drawable.music_sixteenrest, R.drawable.music_sixteenrest, 0, 0);

    private float modifier;
    private int resNoteID;
    private int resLinkID;
    private float xOffset;
    private float yOffset;
    private float altX;
    private float altY;

    NoteType(float modifier, int resNoteID, int resLinkID, float xOffset, float yOffset, float altX, float altY) {
        this.modifier = modifier;
        this.resNoteID = resNoteID;
        this.resLinkID = resLinkID;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.altX = altX;
        this.altY = altY;
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

    public int getResLinkID() {
        return resLinkID;
    }

    public float getXOffset() {
        return xOffset;
    }

    public float getYOffset() {
        return yOffset;
    }
}
