package com.mark.asciiartapp.mvp.presenter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.mark.asciiartapp.mvp.model.base.MainModel;
import com.mark.asciiartapp.mvp.view.MainView;
import com.mark.basemodule.eventbus.ChangeAsciiFinishedEvent;
import com.mark.basemodule.network.AppThreadPool;
import com.mark.basemodule.network.LocalFileThreadPool;
import com.mark.basemodule.util.BitmapUtils;
import com.mark.basemodule.util.LogUtils;

import org.greenrobot.eventbus.EventBus;

public class MainPresenter extends BasePresenter<MainModel, MainView>{

    public void getData(){
        String content = null;
        if (model != null){
            content = model.getDataFromNet();
        }
        if (getView() != null){
            getView().setData(content);
        }
    }

    public void setImageData(){
        String path = null;
        if (model != null){
            path = model.getImageShowResources();
        }
        if (getView() != null){
            getView().setImageViewResources(path);
        }
    }

    public void changeBitmapToAscii(final Context context, final String path){
        if (model != null && getView() != null){
            AppThreadPool.getPool().execute(new Runnable() {
                @Override
                public void run() {
                    Bitmap bitmap = model.changePicToAscii(context,path);
                    String res = BitmapUtils.saveImageToGallery(context, bitmap);
                    LogUtils.d("图片保存的路径为" + res);
                    EventBus.getDefault().post(new ChangeAsciiFinishedEvent(true, res));
                }
            });
        }
    }

    @Override
    protected void onViewDestory() {
        if (model != null){
            model.stopRequest();
        }
    }
}
