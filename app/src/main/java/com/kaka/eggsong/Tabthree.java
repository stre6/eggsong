package com.kaka.eggsong;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by stre6 on 2016-10-20.
 */

public class Tabthree extends Fragment {
    public Tabthree() {

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab, null);
        return inflater.inflate(R.layout.tab3, container, false);
    }
}