package com.gmail.paulolemus14.metronome;

import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.util.List;


public class RhythmEditorActivity extends AppCompatActivity {   //implements TabLayout.OnTabSelectedListener

    public static final String DTAG = "MetronomeApp";
    private final String FM_TAG = "data";

    private List<Measure> measureList;

    private CustomView editorView;          // CustomView for drawing on canvas
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private Retainer retainer;          // Used for saving data
    private Interpreter interpreter;    // Used for converting data


    // Good orientation website:
    // code.hootsuite.com/orientation-changes-on-android/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);                                      // Set fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Log.d(DTAG, "finished setting flags");
        setContentView(R.layout.activity_rhythm_editor);
        Log.d(DTAG, "Finished settingContentView");
        editorView = (CustomView) findViewById(R.id.editor_view);                            // View for the noteEditor
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);                               // The tab layout for fragments
        viewPager = (ViewPager) findViewById(R.id.pager);                                   // Allows switching
        Log.d(DTAG, "Finished finding views");
        setupFragmentTabs();
        Log.d(DTAG, "Finished setting up fragments");

        FragmentManager fm = getSupportFragmentManager();
        retainer = (Retainer) fm.findFragmentByTag(FM_TAG);

        if (retainer == null) {
            Log.d(DTAG, "retainer = NULL");
            retainer = new Retainer();
            fm.beginTransaction().add(retainer, FM_TAG).commit();
        } else {
            editorView.setState(retainer.getInterpreter());
            Log.d(DTAG, "SETSTATE");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(DTAG, "Entered OnDestroy");
        editorView.saveState();
        retainer.setInterpreter(editorView.getState());
        Log.d(DTAG, "Saved Data to retainer");
    }

    private void setupFragmentTabs() {

        Log.d(DTAG, "Activity: setting up frags");
        tabLayout.addTab(tabLayout.newTab().setText("Notes"));
        tabLayout.addTab(tabLayout.newTab().setText("Options"));

        Pager adapter = new Pager(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        try {
            tabLayout.getTabAt(0).setIcon(R.drawable.ic_create_black_24dp);
            tabLayout.getTabAt(1).setIcon(R.drawable.ic_settings_black_24dp);
        } catch (Exception e) {
            Log.d(DTAG, "Failed tab icon set");
        }
    }


    public void wholeBtn(View view) {
        Log.d(DTAG, "Clicked wholeBtn");
        editorView.setNoteType(NoteType.WHOLE);
        editorView.addNote();
    }

    public void halfBtn(View view) {
        Log.d(DTAG, "Clicked halfBtn");
        editorView.setNoteType(NoteType.HALF);
        editorView.addNote();
    }

    public void quarterBtn(View view) {
        Log.d(DTAG, "Clicked quarterBtn");
        editorView.setNoteType(NoteType.QUARTER);
        editorView.addNote();
    }

    public void eighthBtn(View view) {
        Log.d(DTAG, "Clicked eigthBtn");
        editorView.setNoteType(NoteType.EIGHTH);
        editorView.addNote();
    }

    public void sixteenBtn(View view) {
        Log.d(DTAG, "Clicked sixteenthBtn");
        editorView.setNoteType(NoteType.SIXTEENTH);
        editorView.addNote();
    }

    public void delNoteBtn(View view) {
        Log.d(DTAG, "Clicked delNoteBtn");
        editorView.deleteNote();
    }

    public void addMeasureBtn(View view) { // works
        Log.d(DTAG, "Clicked addMeasureBtn");
        editorView.addMeasure();
    }

    public void delMeasureBtn(View view) { // works
        Log.d(DTAG, "Clicked delMeasureBtn");
        editorView.deleteMeasure();
    }
}
