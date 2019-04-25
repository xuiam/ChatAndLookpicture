package com.example.a515_01.homework1.fragment;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.VolleyError;
import com.example.a515_01.homework1.R;
import com.example.a515_01.homework1.util.VolleyUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_3_2 extends Fragment {
    private ImageView iv_f32;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_3_2, container, false);
        initView(view);
        return view;
    }

    public ImageView getImg() {
        return iv_f32;
    }

    private void initView(View view) {
        iv_f32 = view.findViewById(R.id.iv_f32);
        iv_f32.setImageResource(R.mipmap.loader);
        VolleyUtils.loaderRandomImage(new VolleyUtils.ImageCallBack() {
            @Override
            public void success(Bitmap bitmap) {
                iv_f32.setImageBitmap(bitmap);
            }

            @Override
            public void failure(VolleyError volleyError) {
                iv_f32.setImageResource(R.mipmap.failure);
            }
        });
    }
}

