package com.mark.asciiartapp.mvp.model.base;

import android.content.Context;
import android.graphics.Bitmap;

import com.mark.asciiartapp.bean.PictureBean;

import java.util.List;

public interface PictureModel extends Model {

    /**
     * 获取所有图片资源的路径
     * @return
     */
    List<PictureBean> getALlPicturePath(Context context);

    /**
     * 获得指定文件夹下的图片资源
     * @param list 所有图片的资源路径
     * @param path 目标文件夹
     * @return
     */
    List<PictureBean> getIndexPathPictureInfo(List<PictureBean> list, String path);

    /**
     * 删除列表内的内容
     */
    void removePictureList(List<PictureBean> list);


}
