package com.caffe.utill;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * Created by brill on 2016/7/21.
 */
public class LruUtil {
    private static int mTotalSize = (int) Runtime.getRuntime().totalMemory();
    private  static LruCache<String, Bitmap> mLruCache = new LruCache<String, Bitmap>(mTotalSize / 3);

    public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            System.out.println("add lru");
            mLruCache.put(key, bitmap);
        }
    }

    public Bitmap getBitmapFromMemCache(String key) {
        return mLruCache.get(key);
    }

}
