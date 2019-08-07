package com.mark.asciiartapp.mvp.model.base;

import android.content.Context;
import android.graphics.Bitmap;

import com.mark.asciiartapp.bean.PictureBean;

import java.util.List;

public interface CameraModel extends Model{

    List<PictureBean> getAllPicture();

    String getLastCameraViewPath(List<PictureBean> list);

    String getLastAsciiPath(List<PictureBean> list);

    Bitmap changePictureToAscii(Context context, String path);
}
