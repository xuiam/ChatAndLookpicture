package com.example.a515_01.homework1.util;

import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class VolleyUtils {
    private static final String TAG = "VolleyUtils";

    public static void post(String url, JSONObject params, final CallBack callBack) {
        Log.d(TAG, "jsonObjectRequest: " + url);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    callBack.success(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(App.getContext(), "网络连接失败", Toast.LENGTH_LONG).show();
            }
        });
        App.getHttpQueue().add(request);
    }

    public static void get(String url, final CallBack callBack) {
        StringRequest request = new StringRequest(url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {
                            callBack.success(new JSONObject(s));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(App.getContext(), "网络连接失败", Toast.LENGTH_LONG).show();
                    }
                });
        App.getHttpQueue().add(request);
    }

    public static void image(String url, final ImageCallBack callBack) {
        ImageRequest request = new ImageRequest(url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap bitmap) {
                callBack.success(bitmap);
            }
        }, 0, 0, ImageView.ScaleType.CENTER, Bitmap.Config.RGB_565, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                callBack.failure(volleyError);
            }
        });
        App.getHttpQueue().add(request);
    }

    /**
     * 加载随机图片
     *
     * @param callBack
     */
    public static void loaderRandomImage(final ImageCallBack callBack) {
        VolleyUtils.get("http://image.baidu.com/channel/listjson?pn=" + (int) (Math.random() * 1000) + "&rn=1&tag1=壁纸&tag2=全部",
                new VolleyUtils.CallBack() {
                    @Override
                    public void success(JSONObject response) throws JSONException {
                        String imgUrl = response.getJSONArray("data").getJSONObject(0).getString("image_url");
                        VolleyUtils.image(imgUrl, new VolleyUtils.ImageCallBack() {
                            @Override
                            public void success(Bitmap bitmap) {
                                callBack.success(bitmap);
                            }

                            @Override
                            public void failure(VolleyError volleyError) {
                                callBack.failure(volleyError);
                            }
                        });
                    }
                });
    }

    public interface ImageCallBack {
        void success(Bitmap bitmap);

        void failure(VolleyError volleyError);
    }

    public interface CallBack {
        void success(JSONObject response) throws JSONException;

    }
}
