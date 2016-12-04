package com.gmail.paulolemus14.metronome;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Paulo on 11/19/2016.
 */
public class Pager extends FragmentPagerAdapter {

    int tabCount;


    public Pager(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                NotesTab notesTab = new NotesTab();
                return notesTab;
            case 1:
                OptionsTab optionsTab = new OptionsTab();
                return optionsTab;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }


}
