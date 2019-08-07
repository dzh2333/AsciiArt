package com.mark.basemodule.util;

import android.util.Log;

public class LogUtils {
    private static final String TAG = LogUtils.class.getSimpleName();

    public static void i(String content){
        Log.d(TAG, content);
    }

    public static void e(String content){
        Log.e(TAG, content );
    }

    public static void d(String content){
        Log.d(TAG, content);
    }

    public static void w(String content){
        Log.w(TAG, content );
    }
}
