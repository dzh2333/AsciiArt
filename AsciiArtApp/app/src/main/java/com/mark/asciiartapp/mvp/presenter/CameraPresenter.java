package com.mark.asciiartapp.mvp.presenter;

import android.content.Context;
import android.graphics.Bitmap;

import com.mark.asciiartapp.eventbus.CameraEventBean;
import com.mark.asciiartapp.mvp.model.base.CameraModel;
import com.mark.asciiartapp.mvp.view.CameraView;
import com.mark.basemodule.eventbus.ChangeAsciiFinishedEvent;
import com.mark.basemodule.network.AppThreadPool;
import com.mark.basemodule.util.BitmapUtils;
import com.mark.basemodule.util.LogUtils;

import org.greenrobot.eventbus.EventBus;

public class CameraPresenter extends BasePresenter<CameraModel, CameraView>{
    @Override
    protected void onViewDestory() {

    }

    public void initCameraView(){

    }


    public void initSmallView(){
        String lastCameraViewPath = null;
        String lastAsciiViewPath = null;
        if (model != null){
            lastAsciiViewPath = model.getLastAsciiPath(model.getAllPicture());
            lastCameraViewPath = model.getLastCameraViewPath(model.getAllPicture());
            if (lastAsciiViewPath != null){
                getView().initLastAsciiView(lastAsciiViewPath);
            }
            if (lastCameraViewPath != null){
                getView().initLastCameraView(lastCameraViewPath);
            }
        }
    }

    public void changePictureToAscii(final Context context, final String path){
        if (model != null && getView() != null){
            AppThreadPool.getPool().execute(new Runnable() {
                @Override
                public void run() {
                    Bitmap bitmap = model.changePictureToAscii(context, path);
                    String res = BitmapUtils.saveImageToGallery(context, bitmap);
                    LogUtils.d("图片保存的路径为" + res);
                    EventBus.getDefault().post(new CameraEventBean(2, res));
                }
            });
        }
    }

    public void changeSmallView(int mode, String path){
        LogUtils.d("接收到了数据2" + path);
        if (getView() != null){
            switch (mode){
                case 1:
                    LogUtils.d("接收到了数据22" + path);
                    getView().changeLastCameraView(path);
                    break;
                case 2:
                    getView().changeAsciiView(path);
                    break;
                case 3:
                    break;
                default:
                    break;
            }
        }else {
            LogUtils.d("接收到了数据2为空");
        }
    }
}
