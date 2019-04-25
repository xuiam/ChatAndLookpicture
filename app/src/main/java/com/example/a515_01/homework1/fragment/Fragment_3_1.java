package com.example.a515_01.homework1.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.a515_01.homework1.R;

public class Fragment_3_1 extends Fragment {
    private ImageView iv_f31;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_3_1, container, false);
        initView(view);
        return view;
    }

    public ImageView getImg() {
        return iv_f31;
    }

    private void initView(View view) {
        iv_f31 = view.findViewById(R.id.iv_f31);
        iv_f31.setImageResource(R.mipmap.index_f3);
    }

}
