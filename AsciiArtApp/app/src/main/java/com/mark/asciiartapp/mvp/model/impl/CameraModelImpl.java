package com.mark.asciiartapp.mvp.model.impl;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.mark.asciiartapp.application.MainApplication;
import com.mark.asciiartapp.bean.PictureBean;
import com.mark.asciiartapp.constants.Constants;
import com.mark.asciiartapp.mvp.model.base.CameraModel;
import com.mark.basemodule.util.LogUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.mark.asciiartapp.mvp.model.impl.MainModelImpl.scale;
import static com.mark.asciiartapp.mvp.model.impl.MainModelImpl.textAsBitmap;

public class CameraModelImpl implements CameraModel {
    @Override
    public List<PictureBean> getAllPicture() {
        List<PictureBean> res = new ArrayList<>();
        Cursor cursor = MainApplication.getInstance().getContentResolver()
                .query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
        while (cursor.moveToNext()) {
            String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            boolean addable = true;
            for (int i = 0; i < Constants.FILTER_PATH.length; i++){
                if (path.contains(Constants.FILTER_PATH[i])){
                    addable = false;
                }
            }
            File file = new File(path);
            if (file.exists() && addable){
                Long fileTime = file.lastModified();
                res.add(new PictureBean(path, false, fileTime));
                LogUtils.d("图片路径为" + path + "时间为" + fileTime);
            }
        }

        for(int i = 0; i < res.size();i++){
            long max = 0;
            int maxIndex = 0;
            for(int j = i; j < res.size();j++){
                if (max <= res.get(j).getFileTime()){
                    max = res.get(j).getFileTime();
                    maxIndex = j;
                }
            }

            //如果 PictureBean tmp = res.get(i)会出现res全部为第一次max的值,需要实例化一个对象而不是指向一个List索引的值;
            PictureBean tmp = new PictureBean(res.get(i).getPath(), res.get(i).isFocused(), res.get(i).getFileTime());

            res.get(i).setPath(res.get(maxIndex).getPath());
            res.get(i).setFileTime(res.get(maxIndex).getFileTime());
            res.get(i).setFocused(res.get(maxIndex).isFocused());

            res.get(maxIndex).setFileTime(tmp.getFileTime());
            res.get(maxIndex).setFocused(tmp.isFocused());
            res.get(maxIndex).setPath(tmp.getPath());
        }
        return res;
    }

    @Override
    public String getLastCameraViewPath(List<PictureBean> list) {
        for (int i = 0; i < list.size(); i++){
            if (list.get(i).getPath().contains(Constants.DCIM_CAMERA_PATH)){
                return list.get(i).getPath();
            }
        }
        return null;
    }

    @Override
    public String getLastAsciiPath(List<PictureBean> list) {
        for (int i = 0; i < list.size(); i++){
            if (list.get(i).getPath().contains(Constants.ASCII_PATH)){
                return list.get(i).getPath();
            }
        }
        return null;
    }

    @Override
    public Bitmap changePictureToAscii(Context context, String path) {
        final String base = "#8XOHLTI)i=+;:,.";// 字符串由复杂到简单
//        final String base = "#,.0123456789:;@ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";// 字符串由复杂到简单
        StringBuilder text = new StringBuilder();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        //获取屏幕像素
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        Bitmap image = BitmapFactory.decodeFile(path);  //读取图片
        //获取图片像素信息
        int width0 = image.getWidth();
        int height0 = image.getHeight();

        int width1, height1;
        int scale = 7;
        if (width0 <= width / scale) {
            width1 = width0;
            height1 = height0;
        } else {
            width1 = width / scale;
            height1 = width1 * height0 / width0;
        }
        image = scale(path, width1, height1);  //读取图片
        //输出到指定文件中V
        for (int y = 0; y < image.getHeight(); y += 2) {
            for (int x = 0; x < image.getWidth(); x++) {
                final int pixel = image.getPixel(x, y);
                final int r = (pixel & 0xff0000) >> 16, g = (pixel & 0xff00) >> 8, b = pixel & 0xff;
                final float gray = 0.299f * r + 0.578f * g + 0.114f * b;
                final int index = Math.round(gray * (base.length() + 1) / 255);
                String s = index >= base.length() ? " " : String.valueOf(base.charAt(index));
                text.append(s);
            }
            text.append("\n");
        }
        return textAsBitmap(text, context);
    }
}
