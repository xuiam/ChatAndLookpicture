package com.example.a515_01.homework1.util;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

//继承ImageCache，使用LruCache实现缓存
public class BitmapCache implements ImageLoader.ImageCache {
    private LruCache<String, Bitmap> mCache;

    public BitmapCache() {
        int maxSize = 10 * 1024 * 1024;
        mCache = new LruCache<String, Bitmap>(maxSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getRowBytes() * bitmap.getHeight();
            }
        };
    }

    @Override
    public Bitmap getBitmap(String url) {
        return mCache.get(url);
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        mCache.put(url, bitmap);
    }
}

//    private void getImageByImageLoader() {
//        ImageView iv = (ImageView) findViewById(R.id.iv);
//        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
//        String url = "https://www.baidu.com/img/bdlogo.png";
//        ImageLoader loader = new ImageLoader(queue, new BitmapCache());
//// 第一个参数指定用于显示图片的ImageView控件
//// 第二个参数指定加载图片的过程中显示的图片
//// 第三个参数指定加载图片失败的情况下显示的图片
//        ImageLoader.ImageListener listener = ImageLoader.getImageListener(iv, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
//// 调用ImageLoader的get()方法来加载图片
//// 第一个参数就是图片的URL地址
//// 第二个参数则是刚刚获取到的ImageListener对象
//// 如果想对图片的大小进行限制，也可以使用get()方法的重载，指定图片允许的最大宽度和高度，即通过第三第四个参数指定
//        loader.get(url, listener);
//    }
