package com.mark.basemodule.util;

import android.content.Context;
import android.widget.Toast;

import com.mark.basemodule.application.BaseApplication;

public class ToastUtils {

    public static Toast mToast;

    public static void showToast(String content){
        showToast(content, Toast.LENGTH_SHORT);
    }

    public static void showToast(String content, int duration){
        if(mToast == null){
            mToast = Toast.makeText(getContext(), "", duration);
        }
        mToast.setText(content);
        mToast.show();
    }

    public static Context getContext(){
        return BaseApplication.getInstance();
    }
}
