package com.mark.asciiartapp.mvp.model.base;


import android.content.Context;
import android.graphics.Bitmap;

import com.mark.asciiartapp.mvp.model.base.Model;

public interface MainModel extends Model {

    String getDataFromNet();

    void stopRequest();

    String getImageShowResources();

    /**
     * 输入的图片路径转化成Ascii风格
     * @param input
     * @return 返回的图片路径
     */
    Bitmap changePicToAscii(Context context, String input);
}
