package com.example.a515_01.homework1.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a515_01.homework1.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_home extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        return view;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (hidden) {
            onStop();
        } else {
            onResume();
        }
    }
}
