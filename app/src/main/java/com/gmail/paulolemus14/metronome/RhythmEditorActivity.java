package com.gmail.paulolemus14.metronome;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

public class RhythmEditorActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {

    private CustomView editorView;          // CustomView for drawing on canvas
    private TabLayout tabLayout;
    private ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);                                      // Set fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_rhythm_editor);

        editorView = (CustomView) findViewById(R.id.editor_view);                            // View for the noteEditor
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);                               // The tab layout for fragments
        viewPager = (ViewPager) findViewById(R.id.pager);                                   // Allows switching

        setupFragmentTabs();

    }


    private void setupFragmentTabs() {

        tabLayout.addTab(tabLayout.newTab().setText("Notes"));
        tabLayout.addTab(tabLayout.newTab().setText("Options"));

        Pager adapter = new Pager(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.half);
        tabLayout.getTabAt(1).setIcon(R.drawable.quarter);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
