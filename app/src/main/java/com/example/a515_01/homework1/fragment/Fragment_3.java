package com.example.a515_01.homework1.fragment;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.VolleyError;
import com.example.a515_01.homework1.R;
import com.example.a515_01.homework1.adapter.Adapter_f3;
import com.example.a515_01.homework1.util.VolleyUtils;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_3 extends Fragment implements ViewPager.OnPageChangeListener {

    private ViewPager vp_f3;
    private ArrayList<Fragment> mList;
    private Adapter_f3 mAdapter;
    private boolean flag = true;
    private Fragment_3_1 f31;
    private Fragment_3_2 f32;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_3, container, false);
        initView(view);
        mList = new ArrayList<>();
        f31 = new Fragment_3_1();
        f32 = new Fragment_3_2();
        mList.add(f31);
        mList.add(f32);
        mAdapter = new Adapter_f3(getActivity().getSupportFragmentManager(), mList);
        vp_f3.setAdapter(mAdapter);
        vp_f3.addOnPageChangeListener(this);
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

    private void initView(View view) {
        vp_f3 = (ViewPager) view.findViewById(R.id.vp_f3);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (state == 0 && vp_f3.getCurrentItem() == 1) {
            f31.getImg().setImageDrawable(f32.getImg().getDrawable());
            vp_f3.setCurrentItem(0, false);
            if (!flag) {//加载中
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        VolleyUtils.loaderRandomImage(new VolleyUtils.ImageCallBack() {
                            @Override
                            public void success(Bitmap bitmap) {
                                f31.getImg().setImageBitmap(bitmap);
                            }
                            @Override
                            public void failure(VolleyError volleyError) {
                                f31.getImg().setImageResource(R.mipmap.failure);
                            }
                        });
                    }
                }).start();
            }
            flag = false;
            f32.getImg().setImageResource(R.mipmap.loader);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    VolleyUtils.loaderRandomImage(
                            new VolleyUtils.ImageCallBack() {
                                @Override
                                public void success(Bitmap bitmap) {
                                    f32.getImg().setImageBitmap(bitmap);
                                    flag = true;
                                }
                                @Override
                                public void failure(VolleyError volleyError) {
                                    f32.getImg().setImageResource(R.mipmap.failure);
                                    flag = true;
                                }
                            });
                }
            }).start();
        }
    }
}
