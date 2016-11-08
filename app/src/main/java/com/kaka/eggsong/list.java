package com.kaka.eggsong;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by stre6 on 2016-10-18.
 */
public class list extends Fragment {
    public list(){

    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        return inflater.inflate(R.layout.listview_item,container,false);
    }
}
