package com.example.a515_01.homework1.util;

import android.app.Application;
import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class App extends Application {
    public static RequestQueue queue;
    public static Context context;

    public static RequestQueue getHttpQueue() {
        return queue;
    }

    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        queue = Volley.newRequestQueue(getApplicationContext());//使用全局上下文
        context = getApplicationContext();
    }
}
