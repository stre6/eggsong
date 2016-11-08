package com.kaka.eggsong;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by stre6 on 2016-10-20.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {
    private int tabCount;

    public PagerAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.tabCount = numOfTabs;
    }

    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                Tabone tab1 = new Tabone();
                return tab1;
            case 1:
                Tabtwo tab2 = new Tabtwo();
                return tab2;
            case 2:
                Tabthree tab3 = new Tabthree();
                return tab3;
            case 3:
                Tabfour tab4 = new Tabfour();
                return tab4;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
