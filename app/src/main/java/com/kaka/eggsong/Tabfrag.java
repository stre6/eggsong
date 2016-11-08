package com.kaka.eggsong;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by stre6 on 2016-10-19.
 */

public class Tabfrag extends Fragment{
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        int resId=R.layout.tabfrag;
        return inflater.inflate(resId,null);
    }
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
    }
}
