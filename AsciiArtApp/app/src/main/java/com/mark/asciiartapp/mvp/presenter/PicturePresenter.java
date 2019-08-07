package com.mark.asciiartapp.mvp.presenter;

import android.content.Context;
import android.util.Log;

import com.mark.asciiartapp.bean.PictureBean;
import com.mark.asciiartapp.bean.PictureSpinnerBean;
import com.mark.asciiartapp.constants.Constants;
import com.mark.asciiartapp.eventbus.PictureBeanListEvent;
import com.mark.asciiartapp.mvp.model.base.PictureModel;
import com.mark.asciiartapp.mvp.view.PictureView;
import com.mark.basemodule.network.AppThreadPool;
import com.mark.basemodule.util.LogUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class PicturePresenter extends BasePresenter<PictureModel, PictureView> {

    public void showAllPicture(Context context){
        List<PictureBean> path = null;
        if (model != null){
            path = model.getALlPicturePath(context);
        }
        if (path != null && getView() != null){
            getView().showPictureInRV(path);
        }
    }

    public void changePictureDataOrShareData(final Context context, final int mode, final List<PictureBean> inputList, final boolean isRootPath, final String path){
        switch (mode){
            case 1:
                LogUtils.d("接受的数据为" + inputList.size());
                //删除
                if (model != null){
                    AppThreadPool.getPool().execute(new Runnable() {
                        @Override
                        public void run() {
                            List<PictureBean> res = null;
                            model.removePictureList(inputList);
                            LogUtils.d("删除的数据为" + inputList.size());
                            if (isRootPath){
                                res = model.getALlPicturePath(context);
                            }else {
                                res = model.getIndexPathPictureInfo(model.getALlPicturePath(context), path);
                            }
                            if (res != null){
                                //发送消息RV更新
                                EventBus.getDefault().post(new PictureBeanListEvent(1, res));
                            }else {
                                LogUtils.d("数据为空");
                            }
                        }
                    });
                }
                break;
            case 2:
                //分享

                break;
            default:
                break;
        }
    }

    public void spinnerChangePictureRVData(final Context context, final PictureSpinnerBean bean){
        if (model != null){
            AppThreadPool.getPool().execute(new Runnable() {
                @Override
                public void run() {
                    List<PictureBean> res = null;
                    if (bean.isRootPath()){
                        res = model.getALlPicturePath(context);
                    }else {
                        res = model.getIndexPathPictureInfo(model.getALlPicturePath(context), bean.getPath());
                    }
                    if (res != null){
                        //发送消息更新
                        EventBus.getDefault().post(new PictureBeanListEvent(bean.getEventNms(), res));
                    }else {
                        LogUtils.d("数据为空");
                    }
                }
            });
        }
    }

    public void changePictureRVData(List<PictureBean> list){
        if (list != null && getView() != null){
            getView().changePictureInRV(list);
        }
    }

    @Override
    protected void onViewDestory() {

    }
}
