package com.example.a515_01.homework1.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class Adapter_f3 extends FragmentPagerAdapter {
    private static final String TAG = "Adapter_f3";
    private ArrayList<Fragment> mList;

    public Adapter_f3(FragmentManager fm, ArrayList<Fragment> mList) {
        super(fm);
        this.mList = mList;
    }


    @Override
    public Fragment getItem(int position) {
        return mList.get(position);
    }

    @Override
    public int getCount() {
        return mList.size();
    }
}
