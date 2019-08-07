package com.mark.asciiartapp.mvp.model.impl;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import com.mark.asciiartapp.application.MainApplication;
import com.mark.asciiartapp.bean.PictureBean;
import com.mark.asciiartapp.constants.Constants;
import com.mark.asciiartapp.mvp.model.base.PictureModel;
import com.mark.basemodule.util.LogUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PictureModelImpl implements PictureModel {

    @Override
    public List<PictureBean> getALlPicturePath(Context context) {
        List<PictureBean> res = new ArrayList<>();
        Cursor cursor = context.getContentResolver()
                .query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
        while (cursor.moveToNext()) {
            String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            boolean addable = true;
            for (int i = 0;i < Constants.FILTER_PATH.length; i++){
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
    public List<PictureBean> getIndexPathPictureInfo(List<PictureBean> list, String path) {
        List<PictureBean> res = new ArrayList<>();
        for (int i = 0; i < list.size(); i++){
            if (list.get(i).getPath().contains(path)){
                res.add(new PictureBean(list.get(i).getPath(), false, list.get(i).getFileTime()));
            }
        }
        return res;
    }


    @Override
    public void removePictureList(List<PictureBean> list) {
        for (int i = 0; i < list.size(); i++){
            File file = new File(list.get(i).getPath());
            LogUtils.d("删除的数据为" + file.getPath());
            if (file.exists()){
                if (file.delete()){
                    LogUtils.d("删除成功");
                    MainApplication.getInstance().getContentResolver().delete(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, MediaStore.Images.Media.DATA + "=?", new String[]{list.get(i).getPath()});
                }else {
                    LogUtils.d("删除失败");
                }
            }
        }
    }

}
